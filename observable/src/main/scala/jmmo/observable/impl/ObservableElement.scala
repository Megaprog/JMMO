package jmmo.observable.impl

import jmmo.observable.{ObservableFirer, ObservableEvent, ObservableListener, Observable}

/**
 * User: Tomas
 * Date: 09.08.13
 * Time: 9:32
 */
trait ObservableElement extends ObservableFirer {

  def addObservableListener(listener: ObservableListener) {
    if (selfListenersExists(listener)) {
      throw new IllegalArgumentException(s"Observable listener $listener already exists in $this")
    }

    if (listener.level >= 0 && listener.filter(this, Seq.empty)) {
      selfListenersAdd(listener)
    }
  }

  def removeObservableListener(listener: ObservableListener) {
    if (listener.level >= 0) {
      selfListenersRemove(listener)
    }
  }

  protected[observable] def fireObservableEvent(event: ObservableEvent) {
    selfListeners foreach (_.handler(event, Seq.empty))
  }

  protected def selfListenersExists(listener: ObservableListener): Boolean

  protected def selfListeners: TraversableOnce[ObservableListener]

  protected def selfListenersAdd(listener: ObservableListener)

  protected def selfListenersRemove(listener: ObservableListener)
}
