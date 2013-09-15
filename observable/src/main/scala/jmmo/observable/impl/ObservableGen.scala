package jmmo.observable.impl

import jmmo.observable.{ObservableFirer, ObservableEvent, ObservableListener, Observable}

/**
 * User: Tomas
 * Date: 09.08.13
 * Time: 9:32
 */
trait ObservableGen extends ObservableFirer with SelfListenersSupport {

  def addObservableListener(listener: ObservableListener) {
    if (selfListenersContains(listener)) {
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

  protected def fireObservableEvent(event: ObservableEvent) {
    selfListeners foreach (_.handler(event, Seq.empty))
  }
}
