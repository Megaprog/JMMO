package jmmo.observables

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

  def apply(wrapped: ObservableListener, level: Int, chain: Observable*): ListenerWrapper = ListenerWrapperImpl(wrapped, level, chain: _*)

  def apply(listener: ObservableListener, observable: Observable): ListenerWrapper = null


  def unapplySeq(wrapper: ListenerWrapper) = if (wrapper eq null) None else Some(wrapper.wrapped, wrapper.level, wrapper.chain)


  private[ListenerWrapper] case class ListenerWrapperImpl(wrapped: ObservableListener, level: Int, chain: Observable*) extends ListenerWrapper {

    val handler: ObservableListener.Handler = (event, chain) => wrapped.handler(event, this.chain ++ chain)

    val filter: ObservableListener.Filter = (observable, chain) => wrapped.filter(observable, this.chain ++ chain)

    val classes: ObservableListener.Classes = wrapped.classes
  }
}