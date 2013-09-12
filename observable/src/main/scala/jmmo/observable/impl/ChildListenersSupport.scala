package jmmo.observable.impl

import jmmo.observable.ObservableListener

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 21:29
 */
trait ChildListenersSupport {

  protected def childListeners: { def foreach[U](f: ObservableListener => U): Unit }

  protected def childListenersExists(listener: ObservableListener): Boolean

  protected def childListenersAdd(listener: ObservableListener)

  protected def childListenersRemove(listener: ObservableListener)
}
