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
trait SelfListenersImmutableSet { this: ObservableElement =>
  protected var listeners = createImmutableListenersSet

  protected def selfListenersExists(listener: ObservableListener) = listeners.contains(listener)

  protected def selfListeners = listeners

  protected def selfListenersAdd(listener: ObservableListener) = listeners += listener

  protected def selfListenersRemove(listener: ObservableListener) = listeners -= listener

  protected def createImmutableListenersSet = Set.empty[ObservableListener]
}
