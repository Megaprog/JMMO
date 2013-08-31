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
trait ChildListenersImmutableSet {

  protected var childListeners = createChildListeners

  protected def childListenersExists(listener: ObservableListener) = childListeners.contains(listener)

  protected def childListenersAdd(listener: ObservableListener) = childListeners += listener

  protected def childListenersRemove(listener: ObservableListener) = childListeners -= listener

  protected def createChildListeners = Set.empty[ObservableListener]
}
