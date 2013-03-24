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
 * [[jmmo.util.MapSetImmImm]] fully immutable class.
 * @tparam A type of map key
 * @author Tomas Shestakov
 */
trait MapCount[A] {

  def apply(elem: A): Int

  /**
   * Add the specified element to the `MapCount` if element not contains in `MapCount` or increment count of the element
   * @param elem the key to which to bind the new value
   * @return a reference to this `MapCount` if it is mutable otherwise reference to new `MapCount`
   */
  def addCount(elem: A, action: => Unit = {}): MapCount[A]

  /**
   * Remove the specified element from the `MapCount` if current count of the element is 1 or decrement count of the element
   * @param elem the key to which to remove value
   * @return a reference to this MapSet if it is mutable otherwise reference to new MapSet
   */
  def removeCount(elem: A, action: => Unit = {}): MapCount[A]

  /**
   * Remove all counts of specified element
   * @param elem element to remove all counts from
   * @return a reference to this `MapCount` if it is mutable otherwise reference to new `MapCount`
   */
  def removeAllCounts(elem: A): MapCount[A]

  /**
   * Remove all counts and all element from `MapCount`
   * @return a reference to this `MapCount` if it is mutable otherwise reference to new `MapCount`
   */
  def clear(): MapCount[A]

  /**
   * @return a map based on which the `MapCount`
   */
  def underlyingMap: scala.collection.Map[A, Int]
}

/**
 * Common methods for all MapSet inheritors.
 * @tparam A type of map key
 */
abstract class MapCountCommon[A] extends MapCount[A] with Proxy {

  def apply(elem: A) = this.underlyingMap.get(elem) match {
                        case Some(count) => count
                        case None => 0
                      }

  override def toString = this.getClass.getSimpleName + "(" + this.underlyingMap + ")"

  def self = this.underlyingMap
}

class MapCountMut[A](val underlyingMap: mutable.Map[A, Int]) extends MapCountCommon[A] {

  def this() = this(mutable.Map.empty)

  def addCount(elem: A, action: => Unit = {}): this.type = {
    underlyingMap(elem) = 1 + (underlyingMap.get(elem) match {
                                 case Some(count) => count
                                 case None => { action; 0 }
                               })
    this
  }

  def removeCount(elem: A, action: => Unit = {}): this.type = {
    underlyingMap.get(elem) match {
      case Some(count) =>
        if (count > 1) {
          underlyingMap(elem) = count - 1
        }
        else {
          action
          underlyingMap -= elem
        }
      case None =>
    }
    this
  }

  def removeAllCounts(elem: A): this.type = {
    underlyingMap -= elem
    this
  }

  def clear(): this.type = {
    underlyingMap.clear()
    this
  }

  /**
   * Add new key -> value binding to mutable MapSet.
   * @param elem the key/value binding
   * @return a reference to this MapSet
   */
  def += (elem: A): this.type = this.addCount(elem)

  /**
   * Remove new key -> value binding from mutable MapSet.
   * @param elem the key/value binding
   * @return a reference to this MapSet
   */
  def -= (elem: A): this.type = this.removeCount(elem)
}

class MapCountImm[A](val underlyingMap: immutable.Map[A, Int]) extends MapCountCommon[A] {

  def this() = this(immutable.Map.empty)

  def addCount(elem: A, action: => Unit = {}): MapCountImm[A] = {
    new MapCountImm[A](underlyingMap + (elem -> (1 + (underlyingMap.get(elem) match {
                                                        case Some(count) => count
                                                        case None => { action; 0 }
                                                      }))))
  }

  def removeCount(elem: A, action: => Unit = {}): MapCountImm[A] = {
    underlyingMap.get(elem) match {
      case Some(count) =>
        if (count > 1) {
          new MapCountImm[A](underlyingMap + (elem -> (count - 1)))
        }
        else {
          action
          new MapCountImm[A](underlyingMap - elem)
        }
      case None => this
    }
  }

  def removeAllCounts(elem: A): MapCountImm[A] = {
    new MapCountImm[A](underlyingMap - elem)
  }

  def clear(): MapCountImm[A] = {
    new MapCountImm[A](underlyingMap.empty)
  }

  /**
   * Add new key -> value binding to mutable MapSet.
   * @param elem the key/value binding
   * @return a reference to this MapSet
   */
  def + (elem: A): MapCountImm[A] = this.addCount(elem)

  /**
   * Remove new key -> value binding from mutable MapSet.
   * @param elem the key/value binding
   * @return a reference to this MapSet
   */
  def - (elem: A): MapCountImm[A] = this.removeCount(elem)
}

/**
 * Helper object to create [[jmmo.util.MapCount]] instances.
 */
object MapCount {

  /**
   * @tparam A type of element to count
   * @return default mutable `MapCount` with [[scala.collection.mutable.HashMap]]
   */
  def mut[A] = new MapCountMut[A]

  /**
   * @tparam A type of element to count
   * @return default immutable `MapCount` with [[scala.collection.mutable.HashMap]]
   */
  def imm[A] = new MapCountImm[A]
}