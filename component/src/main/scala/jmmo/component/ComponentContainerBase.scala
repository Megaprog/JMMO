package jmmo.component

import jmmo.observable.impl.{ChildListenersImmSet, SelfListenersImmSet}
import jmmo.component.impl.{ComponentContainerGen, AvailableComponentsImmMap, AllComponentsImmMap}

/**
 * User: Tomas
 * Date: 15.09.13
 * Time: 23:04
 */
trait ComponentContainerBase extends ComponentContainerGen with SelfListenersImmSet with ChildListenersImmSet
                                                           with AllComponentsImmMap with AvailableComponentsImmMap
