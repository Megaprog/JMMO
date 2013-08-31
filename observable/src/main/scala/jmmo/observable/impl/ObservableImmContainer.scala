package jmmo.observable.impl

import jmmo.observable.{Observable, ObservableListener}

/**
 * User: Tomas
 * Date: 31.08.13
 * Time: 19:18
 */
trait ObservableImmContainer extends Observable {

  abstract override def addObservableListener(listener: ObservableListener) {
    super.addObservableListener(listener)
    if (listener.level > 0) {
      addChildListener(listener)
    }
  }

  abstract override def removeObservableListener(listener: ObservableListener) {
    super.removeObservableListener(listener)
    if (listener.level > 0) {
      removeChildListener(listener)
    }
  }


  protected def addChildListener(listener: ObservableListener) {
    val childListener = createChildListener(listener)

    if (childListenerExists(listener)) {
      throw new IllegalArgumentException(s"Observable listener $listener already exists in $this")
    }

    childListenersAdd(childListener)

    childObservables foreach (_.addObservableListener(childListener))
  }

  protected def removeChildListener(listener: ObservableListener) {
    val childListener = createChildListener(listener)

    childListenerRemove(childListener)

    childObservables foreach (_.removeObservableListener(childListener))
  }

  protected def createChildListener(listener: ObservableListener) = ListenerWrapper(listener, this)

  protected def childListenerExists(listener: ObservableListener): Boolean

  protected def childListeners: TraversableOnce[ObservableListener]

  protected def childListenersAdd(listener: ObservableListener)

  protected def childListenerRemove(listener: ObservableListener)

  protected def childObservables: TraversableOnce[Observable]
}
