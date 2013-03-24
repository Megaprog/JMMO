/*
 * Copyright (c) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.util

import scala.collection.mutable
import scala.collection.immutable

/**
 * Trait for manipulate Map with Sets as value.
 * Has four derived classes with different combinations
 * of mutable or immutable Map and Sets:<br>
 * [[jmmo.util.MapSetMutMut]] mutable map and update;<br>
 * [[jmmo.util.MapSetMutImm]] mutable map but immutable sets;<br>
 * [[jmmo.util.MapSetImmMut]] immutable map but mutable sets;<br>
 * [[jmmo.util.MapSetImmImm]] fully immutable class.
 * @tparam A type of map key
 * @tparam B type elements in Set
 * @author Tomas Shestakov
 */
trait MapSet[A, B] {

  /**
   * Returns Set corresponded to the key or empty Set if key not found
   * @param key the key to which to bind the Set
   */
  def apply(key: A): scala.collection.Set[B]

  /**
   * Add the specified `value` to a specified `key`
   * @param key the key to which to bind the new value
   * @param value the value to bind to the key
   * @return a reference to this MapSet if it is mutable otherwise reference to new MapSet
   */
  def addBinding(key: A, value: B): MapSet[A, B]

  /**
   * Remove the specified `value` from a specified `key`
   * @param key the key to which to remove value
   * @param value the value to remove from the key
   * @return A reference to this MapSet if it is mutable otherwise reference to new MapSet
   */
  def removeBinding(key: A, value: B): MapSet[A, B]

  /**
   * Remove all bindings from a specified `key`
   * @param key the key to which to remove all values
   * @return a reference to this MapSet if it is mutable otherwise reference to new MapSet
   */
  def removeAllBindings(key: A): MapSet[A, B]

  /**
   * Remove all keys and all bindings from `MapSet`
   * @return a reference to this MapSet if it is mutable otherwise reference to new MapSet
   */
  def clear(): MapSet[A, B]

  /**
   * @return a map based on which the MapSet
   */
  def underlyingMap: scala.collection.Map[A, scala.collection.Set[B]]
}

/**
 * Common methods for all MapSet inheritors.
 * @tparam A type of map key
 * @tparam B type elements in Set
 */
abstract class MapSetCommon[A, B] extends MapSet[A, B] with Proxy {

  def apply(key: A) = this.underlyingMap.get(key) match {
                        case Some(set) => set
                        case None => Set.empty[B]
                      }

  override def toString = this.getClass.getSimpleName + "(" + this.underlyingMap + ")"

  def self = this.underlyingMap
}

/**
 * Common methods for mutable MapSet inheritors.
 * @tparam A type of map key
 * @tparam B type elements in Set
 */
abstract class MapSetMutable[A, B] extends MapSetCommon[A, B] {

  def addBinding(key: A, value: B): this.type

  def removeBinding(key: A, value: B): this.type

  def removeAllBindings(key: A): this.type

  def clear(): this.type

  /**
   * Add new key -> value binding to mutable MapSet.
   * @param kv the key/value binding
   * @return a reference to this MapSet
   */
  def += (kv: (A, B)): this.type = this.addBinding(kv._1, kv._2)

  /**
   * Remove new key -> value binding from mutable MapSet.
   * @param kv the key/value binding
   * @return a reference to this MapSet
   */
  def -= (kv: (A, B)): this.type = this.removeBinding(kv._1, kv._2)
}

class MapSetMutMut[A, B](protected val innerMap: mutable.Map[A, mutable.Set[B]]) extends MapSetMutable[A, B] {

  def this() = this(mutable.Map.empty)

  def underlyingMap = innerMap

  def addBinding(key: A, value: B): this.type  = {
    innerMap.get(key) match {
      case Some(set) =>
        set += value
      case None =>
        val set = makeSet
        set += value
        innerMap(key) = set
    }
    this
  }

  def removeBinding(key: A, value: B): this.type = {
    innerMap.get(key) match {
      case Some(set) =>
        set -= value
        if (set.isEmpty) innerMap -= key
      case None =>
    }
    this
  }

  def removeAllBindings(key: A): this.type = {
    innerMap -= key
    this
  }

  def clear(): this.type = {
    innerMap.clear()
    this
  }

  protected def makeSet = mutable.Set.empty[B]
}

class MapSetMutImm[A, B](protected val innerMap: mutable.Map[A, immutable.Set[B]]) extends MapSetMutable[A, B] {

  def this() = this(mutable.Map.empty)

  def underlyingMap = innerMap

  def addBinding(key: A, value: B): this.type = {
    innerMap(key) = (innerMap.get(key) match {
      case Some(set) =>
        set
      case None =>
        makeSet
    }) + value
    this
  }

  def removeBinding(key: A, value: B): this.type = {
    innerMap.get(key) match {
      case Some(set) =>
        val n_set = set - value
        if (n_set.isEmpty) innerMap -= key else innerMap(key) = n_set
      case None =>
    }
    this
  }

  def removeAllBindings(key: A): this.type = {
    innerMap -= key
    this
  }

  def clear(): this.type = {
    innerMap.clear()
    this
  }

  protected def makeSet = immutable.Set.empty[B]
}

class MapSetImmMut[A, B](protected var innerMap: immutable.Map[A, mutable.Set[B]]) extends MapSetMutable[A, B] {

  def this() = this(immutable.Map.empty)

  def underlyingMap = innerMap

  def addBinding(key: A, value: B): this.type = {
    innerMap.get(key) match {
      case Some(set) =>
        set += value
       case None =>
        val set = makeSet
        set += value
        innerMap += (key -> set)
    }
    this
  }

  def removeBinding(key: A, value: B): this.type = {
    innerMap.get(key) match {
      case Some(set) =>
        set -= value
        if (set.isEmpty) innerMap -= key
      case None =>
    }
    this
  }

  def removeAllBindings(key: A): this.type = {
    innerMap -= key
    this
  }

  def clear(): this.type = {
    innerMap = innerMap.empty
    this
  }

  protected def makeSet = mutable.Set.empty[B]
}

class MapSetImmImm[A, B](protected val innerMap: immutable.Map[A, immutable.Set[B]]) extends MapSetCommon[A, B] {

  def this() = this(immutable.Map.empty)

  def underlyingMap = innerMap

  def addBinding(key: A, value: B): MapSetImmImm[A, B] = {
    new MapSetImmImm(innerMap + (key -> ((innerMap.get(key) match {
                                  case Some(set) =>
                                    set
                                  case None =>
                                    makeSet
                                }) + value)))
  }

  def removeBinding(key: A, value: B): MapSetImmImm[A, B] = {
    innerMap.get(key) match {
      case Some(set) =>
        val n_set = set - value
        if (n_set.isEmpty) new MapSetImmImm(innerMap - key) else new MapSetImmImm(innerMap + (key -> n_set))
      case None => this
    }
  }

  def removeAllBindings(key: A): MapSetImmImm[A, B] = {
    new MapSetImmImm(innerMap - key)
  }

  def clear(): MapSetImmImm[A, B] = {
    new MapSetImmImm(innerMap.empty)
  }

  protected def makeSet = immutable.Set.empty[B]

  /**
   * Create new immutable MapSet addition to the old a key -> value binding.
   * @param kv the key/value binding
   * @return a reference to new immutable MapSet
   */
  def + (kv: (A, B)) = this.addBinding(kv._1, kv._2)

  /**
   * Create new immutable MapSet removing from the old a key -> value binding.
   * @param kv the key/value binding
   * @return a reference to new immutable MapSet
   */
  def - (kv: (A, B)) = this.removeBinding(kv._1, kv._2)
}

/**
 * Helper object to create [[jmmo.util.MapSet]] instances.
 */
object MapSet {
  /**
   * @tparam A type of map key
   * @tparam B type elements in Set
   * @return default MapSet with [[scala.collection.mutable.HashMap]] and
   * [[scala.collection.mutable.HashSet]]
   */
  def mutMut[A, B] = new MapSetMutMut[A, B]
  /**
   * @tparam A type of map key
   * @tparam B type elements in Set
   * @return default MapSet with [[scala.collection.mutable.HashMap]] and
   * [[scala.collection.immutable.HashSet]]
   */
  def mutImm[A, B] = new MapSetMutImm[A, B]
  /**
   * @tparam A type of map key
   * @tparam B type elements in Set
   * @return default MapSet with [[scala.collection.immutable.HashMap]] and
   * [[scala.collection.mutable.HashSet]]
   */
  def immMut[A, B] = new MapSetImmMut[A, B]
  /**
   * @tparam A type of map key
   * @tparam B type elements in Set
   * @return default fully immutable MapSet with [[scala.collection.immutable.HashMap]] and
   * [[scala.collection.immutable.HashSet]]
   */
  def immImm[A, B] = new MapSetImmImm[A, B]

  def unapply[A, B](mapSet: MapSet[A, B]) = if (mapSet eq null) None else Some(mapSet.underlyingMap)
}
