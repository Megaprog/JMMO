package jmmo.observable

import jmmo.observable.impl.{ChildElementsImmSet, ChildListenersImmSet, ObservableContainerMut}

/**
 * User: Tomas
 * Date: 02.09.13
 * Time: 9:34
 */
trait ObservableContainerBase[A <: Observable] extends ObservableBase with ObservableContainerMut[A]
    with ChildListenersImmSet with ChildElementsImmSet[A]
