package jmmo.observable.impl

import jmmo.observable.{ObservableListener, ObservableContainer, Observable}

/**
 * User: Tomas
 * Date: 31.08.13
 * Time: 23:21
 */
trait ObservableContainerMut[A <: Observable] extends ObservableContainer[A] with ObservableContainerImm[A] {

  override protected def addChildListenerWrapper(listenerWrapper: ObservableListener) {
    if (childListenersExists(listenerWrapper)) {
      throw new IllegalArgumentException(s"Observable listenerWrapper $listenerWrapper already exists in $this")
    }

    childListenersAdd(listenerWrapper)

    super.addChildListenerWrapper(listenerWrapper)
  }

  override protected def removeChildListenerWrapper(listenerWrapper: ObservableListener) {
    childListenersRemove(listenerWrapper)

    super.removeChildListenerWrapper(listenerWrapper)
  }

  protected def childListenersExists(listener: ObservableListener): Boolean

  protected def childListenersAdd(listener: ObservableListener)

  protected def childListenersRemove(listener: ObservableListener)

  protected def childListeners: TraversableOnce[ObservableListener]
}
