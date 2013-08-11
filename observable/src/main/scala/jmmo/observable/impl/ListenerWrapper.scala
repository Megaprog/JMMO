package jmmo.observable.impl

import jmmo.observable.Observable
import jmmo.observable.ObservableListener

/**
 * User: Tomas
 * Date: 09.08.13
 * Time: 12:17
 */
trait ListenerWrapper extends ObservableListener with Equals {

  def wrapped: ObservableListener

  def chain: ObservableListener.Chain
}

object ListenerWrapper {

  def apply(listener: ObservableListener, observable: Observable): ListenerWrapper =
    apply(wrapped(listener), listener.level - 1, (chain(listener) :+ observable): _*)

  protected[observable] def apply(wrapped: ObservableListener, level: Int, chain: Observable*): ListenerWrapper = ListenerWrapperImpl(wrapped, level, chain)


  def unapplySeq(wrapper: ListenerWrapper): Option[(ObservableListener, Int, Seq[Observable])] =
    if (wrapper eq null) None else Some(wrapper.wrapped, wrapper.level, wrapper.chain)


  trait WrapperEquals extends Equals { this: ListenerWrapper =>

    def canEqual(other: Any) = other.isInstanceOf[ListenerWrapper]

    override def equals(other: Any) = other match {
      case that @ ListenerWrapper(w, l, c @ _*) => (that canEqual this) && w == wrapped && l == level && c == chain
      case _ => false
    }
  }

  private[ListenerWrapper] case class ListenerWrapperImpl(wrapped: ObservableListener, level: ObservableListener.Level, chain: ObservableListener.Chain)
    extends ListenerWrapper with WrapperEquals {

    val handler: ObservableListener.Handler = (event, chain) => wrapped.handler(event, this.chain ++ chain)

    val filter: ObservableListener.Filter = (observable, chain) => wrapped.filter(observable, this.chain ++ chain)

    def classes: ObservableListener.Classes = wrapped.classes
  }

  def wrapped(listener : ObservableListener) = listener match {
    case wrapper: ListenerWrapper => wrapper.wrapped
    case _ => listener
  }

  def chain(listener : ObservableListener) = listener match {
    case wrapper: ListenerWrapper => wrapper.chain
    case _ => Vector.empty
  }
}