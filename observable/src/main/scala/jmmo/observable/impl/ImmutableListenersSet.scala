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

  protected def selfListenerExists(listener: ObservableListener) = listeners.contains(listener)

  protected def selfListeners = listeners

  protected def addSelfListener(listener: ObservableListener) = listeners += listener

  protected def removeSelfListener(listener: ObservableListener) = listeners -= listener

  protected def createImmutableListenersSet = Set.empty[ObservableListener]
}
