package jmmo.observable.impl

import jmmo.observable.{ObservableEvent, ObservableListener, Observable}

/**
 * User: Tomas
 * Date: 09.08.13
 * Time: 9:32
 */
trait ObservableElement extends ObservableFirer {

  def addObservableListener(listener: ObservableListener) {
    if (selfListenerExists(listener)) {
      throw new IllegalArgumentException(s"Observable listener $listener already exists in $this")
    }

    if (listener.level >= 0 && listener.filter(this, Seq.empty)) {
      addSelfListener(listener)
    }
  }

  def removeObservableListener(listener: ObservableListener) {
    removeSelfListener(listener)
  }

  protected[impl] def fireObservableEvent(event: ObservableEvent) {
    selfListeners foreach (_.handler(event, Seq.empty))
  }

  protected def selfListenerExists(listener: ObservableListener): Boolean

  protected def selfListeners: TraversableOnce[ObservableListener]

  protected def addSelfListener(listener: ObservableListener)

  protected def removeSelfListener(listener: ObservableListener)
}
