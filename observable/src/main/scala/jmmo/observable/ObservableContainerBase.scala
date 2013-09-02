package jmmo.observable

import jmmo.observable.impl.{ChildObservablesImmSet, ChildListenersImmSet, ObservableContainerMut}

/**
 * User: Tomas
 * Date: 02.09.13
 * Time: 9:34
 */
trait ObservableContainerBase[A <: Observable] extends ObservableBase with ObservableContainerMut[A]
    with ChildListenersImmSet with ChildObservablesImmSet[A]
