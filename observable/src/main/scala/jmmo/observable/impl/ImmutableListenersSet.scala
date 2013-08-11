/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observable.impl

import jmmo.observable.ObservableListener

/**
 * User: Tomas
 * Date: 11.08.13
 * Time: 9:35
 */
trait ImmutableListenersSet { this: ObservableElement =>
  protected var listeners = createImmutableListenersSet

  abstract override protected def selfListenerExists(listener: ObservableListener) = listeners.contains(listener)

  abstract override protected def selfListeners = listeners

  abstract override protected def addSelfListener(listener: ObservableListener) {
    listeners += listener
  }

  abstract override protected def removeSelfListener(listener: ObservableListener) {
    listeners -= listener
  }

  protected def createImmutableListenersSet = Set.empty[ObservableListener]
}
