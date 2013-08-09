package jmmo.observables

/**
 * User: Tomas
 * Date: 09.08.13
 * Time: 12:17
 */
trait ListenerWrapper extends ObservableListener with Equals {

  def chain: Seq[Observable]

  def wrapped: ObservableListener
}

object ListenerWrapper {

  private[ListenerWrapper] case class ListenerWrapperImpl(wrapped: ObservableListener, level: Int, chain: Observable*) extends ObservableListener {

    def handler: ObservableListener.Handler = ???

    def filter: ObservableListener.Filter = ???

    def classes: ObservableListener.Classes = ???
  }
}