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

  type Classes = collection.Set[Class[_ <: ObservableEvent]]


  val PassAll: Filter= (_, _) => true

  val ParentLevel: Level = 0

  val MaxLevel: Level = Int.MaxValue

  val AllClasses: Classes = Set.empty


  def apply(handler: Handler): ObservableListener = handler

  def apply(handler: Handler, filter: Filter): ObservableListener = new ObservableListenerImpl(handler, filter = filter)

  def apply(handler: Handler, level: Level): ObservableListener = new ObservableListenerImpl(handler, level = level)

  def apply(handler: Handler, classes: Classes): ObservableListener = new ObservableListenerImpl(handler, classes = classes)

  def apply(handler: Handler, filter: Filter, level: Level): ObservableListener = new ObservableListenerImpl(handler, filter, level)

  def apply(handler: Handler, filter: Filter, classes: Classes): ObservableListener = new ObservableListenerImpl(handler, filter, classes = classes)

  def apply(handler: Handler, level: Level, classes: Classes): ObservableListener = new ObservableListenerImpl(handler, level = level, classes = classes)

  def apply(handler: Handler, filter: Filter, level: Level, classes: Classes): ObservableListener = new ObservableListenerImpl(handler, filter, level, classes)


  def unapply(listener: ObservableListener) = if (listener eq null) None else Some(listener.handler, listener.filter, listener.level, listener.classes)


  implicit class ObservableListenerImpl(val handler: Handler) extends ObservableListener {
    private var _filter = PassAll
    private var _level = MaxLevel
    private var _classes = AllClasses

    def this(handler: Handler, filter: Filter = PassAll, level: Level = MaxLevel, classes: Classes = AllClasses) {
      this(handler)
      _filter = filter
      _level = level
      _classes = classes
    }

    def filter = _filter
    def level = _level
    def classes = _classes

    override def toString = "ObservableListener{" +
      "handler=" + handler +
      ", filter=" + filter +
      ", level=" + level +
      ", classes=" + classes +
    "}"
  }
}
