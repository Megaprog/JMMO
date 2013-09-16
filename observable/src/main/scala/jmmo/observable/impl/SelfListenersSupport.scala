package jmmo.observable.impl

import jmmo.observable.ObservableListener

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 21:27
 */
trait SelfListenersSupport {

  protected def selfListeners: TraversableOnce[ObservableListener]

  protected def selfListenersContains(listener: ObservableListener): Boolean

  protected def selfListenersAdd(listener: ObservableListener)

  protected def selfListenersRemove(listener: ObservableListener)
}
