package jmmo.observable

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
   * @return Level of child Observers to which will be added the listenerWrapper
   */
  def level: ObservableListener.Level
}

/**
 * For creation instances of [[jmmo.observable.ObservableListener]]
 */
object ObservableListener {

  type Chain = Seq[Observable]

  type Handler = (ObservableEvent, Chain) => Unit

  type Filter = (Observable, Chain) => Boolean

  type Level = Int


  val PassAll: Filter= (_, _) => true

  val NotPassLevel: Level = -1 //

  val ParentLevel: Level = 0

  val MaxLevel: Level = Int.MaxValue


  def apply(handler: Handler, filter: Filter = PassAll, level: Level = MaxLevel): ObservableListener = ObservableListenerImpl(handler, filter, level)

  def unapply(listener: ObservableListener) = if (listener eq null) None else Some(listener.handler, listener.filter, listener.level)


  implicit class ImplicitObservableListener(val handler: Handler) extends ObservableListener {

    def filter = PassAll
    def level = MaxLevel

    override def toString = s"ObservableListener1(handler=$handler, filter=$filter, level=$level)"
  }

  private[ObservableListener] case class ObservableListenerImpl(handler: Handler, filter: Filter, level: Level) extends ObservableListener
}
