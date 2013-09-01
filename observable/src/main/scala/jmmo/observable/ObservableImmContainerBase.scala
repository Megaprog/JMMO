package jmmo.observable

import jmmo.observable.impl.{ChildListenersImmutableSet, ObservableContainerImm}

/**
 * User: Tomas
 * Date: 31.08.13
 * Time: 23:49
 */
trait ObservableImmContainerBase[+A <: Observable] extends ObservableBase with ObservableContainerImm[A]