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
trait SelfListenersImmSet extends SelfListenersSupport {

  protected var selfListeners = createSelfListeners

  protected def selfListenersExists(listener: ObservableListener) = selfListeners.contains(listener)

  protected def selfListenersAdd(listener: ObservableListener) = selfListeners += listener

  protected def selfListenersRemove(listener: ObservableListener) = selfListeners -= listener

  protected def createSelfListeners = Set.empty[ObservableListener]
}
