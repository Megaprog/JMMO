package jmmo.observables

import java.util.EventListener

/**
 * User: Tomas
 * Date: 04.08.13
 * Time: 17:00
 */
trait ObservableListener extends EventListener {

  /**
   * @return event handler
   */
  def handler: ObservableListener.Handler

  /**
   * @return filter of observables
   */
  def filter: ObservableListener.Filter

  /**
   * @return Level of child Observers to which will be added the listener
   */
  def level: ObservableListener.Level

  /**
   * @return classes of events what listener will handle. If empty all events will be handle
   */
  def classes: ObservableListener.Classes
}

/**
 * For creation instances of [[jmmo.observables.ObservableListener]]
 */
object ObservableListener {

  type Handler = (ObservableEvent, Seq[Observable]) => Unit

  type Filter = (Observable, Seq[Observable]) => Boolean

  type Level = Int

  type Classes = collection.Set[EventClass]

  type EventClass = Class[_ <: ObservableEvent]


  val PassAll: Filter= (_, _) => true

  val ParentLevel: Level = 0

  val MaxLevel: Level = Int.MaxValue

  val AllClasses: Classes = Set.empty


  def apply(handler: Handler): ObservableListener = ObservableListenerImpl(handler)

  def apply(handler: Handler, filter: Filter): ObservableListener = ObservableListenerImpl(handler, filter = filter)

  def apply(handler: Handler, classes: Classes): ObservableListener = ObservableListenerImpl(handler, classes = classes)

  def apply(handler: Handler, level: Level): ObservableListener = ObservableListenerImpl(handler, level = level)

  def apply(handler: Handler, filter: Filter, classes: Classes): ObservableListener = ObservableListenerImpl(handler, filter, classes)

  def apply(handler: Handler, filter: Filter, level: Level): ObservableListener = ObservableListenerImpl(handler, filter, level = level)

  def apply(handler: Handler, classes: Classes, level: Level): ObservableListener = ObservableListenerImpl(handler, classes = classes, level = level)

  def apply(handler: Handler, filter: Filter, classes: Classes, level: Level): ObservableListener = ObservableListenerImpl(handler, filter, classes, level)


  def unapply(listener: ObservableListener) = if (listener eq null) None else Some(listener.handler, listener.filter, listener.level, listener.classes)


  private[ObservableListener] case class ObservableListenerImpl(handler: Handler, filter: Filter = PassAll, classes: Classes = AllClasses, level: Level = MaxLevel) extends ObservableListener

  implicit class ImplicitObservableListener(val handler: Handler) extends ObservableListener {

    val filter = PassAll
    val classes = AllClasses
    val level = MaxLevel

    override def toString = s"ImplicitObservableListener(handler=$handler, filter=$filter, classes=$classes, level=$level)"
  }
}
