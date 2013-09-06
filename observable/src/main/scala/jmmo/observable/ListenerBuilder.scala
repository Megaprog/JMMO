package jmmo.observable

import scala.collection.mutable
import scala.reflect.ClassTag
import java.lang.IllegalArgumentException

/**
 * User: Tomas
 * Date: 06.09.13
 * Time: 16:07
 */
class ListenerBuilder {
  protected val classHandlerMap = new mutable.LinkedHashMap[Class[_ <: ObservableEvent], ObservableListener.Handler]
  protected val classFilterMap = new mutable.LinkedHashMap[Class[_ <: Observable], ObservableListener.Filter]
  protected var level = ObservableListener.MaxLevel

  def addHandler[T <: ObservableEvent](handler: (T, ObservableListener.Chain) => Unit)(implicit tag: ClassTag[T]): this.type = {
    val clazz = tag.runtimeClass.asInstanceOf[Class[T]]
    if (classHandlerMap.contains(clazz)) {
      throw new IllegalArgumentException(clazz.getName + " already handled by " + classHandlerMap.get(clazz))
    }

    classHandlerMap.put(clazz, handler.asInstanceOf[ObservableListener.Handler])
    this
  }

  def addHandlerWC[T <: ObservableEvent](handler: (T) => Unit)(implicit tag: ClassTag[T]): this.type = {
    addHandler[T]((event, chain) => handler(event))
  }

  def addFilter[T <: Observable](filter: (T, ObservableListener.Chain) => Boolean)(implicit tag: ClassTag[T]): this.type = {
    val clazz = tag.runtimeClass.asInstanceOf[Class[T]]
    if (classFilterMap.contains(clazz)) {
      throw new IllegalArgumentException(clazz.getName + " already filtered by " + classFilterMap.get(clazz))
    }

    classFilterMap.put(clazz, filter.asInstanceOf[ObservableListener.Filter])
    this
  }

  def addFilterWC[T <: Observable](filter: (T) => Boolean)(implicit tag: ClassTag[T]): this.type = {
    addFilter[T]((observable, chain) => filter(observable))
  }

  def setLevel(level: ObservableListener.Level) {
    this.level = level
  }

  def build(): ObservableListener = {
    if (classHandlerMap.isEmpty) {
      throw new IllegalArgumentException("Must be at least one handler to build listener")
    }

    val classesHandlers = classHandlerMap.toIterable
    val classesFilters = classFilterMap.toIterable

    ObservableListener(
      if (classesHandlers.size == 1)
        new SingleHandler(classesHandlers.iterator.next())
      else
        new MultiHandler(classesHandlers),

      if (classesFilters.isEmpty)
        ObservableListener.PassAll
      else if (classesFilters.size == 1)
        new SingleFilter(classesFilters.iterator.next())
      else
        new MultiFilter(classesFilters),

      level
    )
  }

  protected class SingleHandler(classHandler: (Class[_ <: ObservableEvent], ObservableListener.Handler)) extends ObservableListener.Handler {

    def apply(event: ObservableEvent, chain: ObservableListener.Chain) {
      if (classHandler._1.isInstance(event)) {
        classHandler._2(event, chain)
      }
    }
  }

  protected class MultiHandler(classesHandlers: Traversable[(Class[_ <: ObservableEvent], ObservableListener.Handler)]) extends ObservableListener.Handler {

    def apply(event: ObservableEvent, chain: ObservableListener.Chain) {
      classesHandlers foreach {
        case (clazz, handler) =>
          if (clazz.isInstance(event)) {
            handler(event, chain)
            return
          }
      }
    }
  }

  protected class SingleFilter(classFilter: (Class[_ <: Observable], ObservableListener.Filter)) extends ObservableListener.Filter {

    def apply(observable: Observable, chain: ObservableListener.Chain): Boolean = {
      if (classFilter._1.isInstance(observable)) {
        classFilter._2(observable, chain)
      }
      else {
        false
      }
    }
  }

  protected class MultiFilter(classesFilters: Traversable[(Class[_ <: Observable], ObservableListener.Filter)]) extends ObservableListener.Filter {

    def apply(observable: Observable, chain: ObservableListener.Chain): Boolean = {
      classesFilters foreach {
        case (clazz, filter) =>
          if (clazz.isInstance(observable)) {
            return filter(observable, chain)
          }
      }
      false
    }
  }
}

object ListenerBuilder {

  def apply() = new ListenerBuilder()
}
