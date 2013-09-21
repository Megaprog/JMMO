package jmmo.component

import jmmo.observable.ObservableBase
import jmmo.component.impl.ComponentGen

/**
 * User: Tomas
 * Date: 19.09.13
 * Time: 13:00
 */
trait ComponentBase[A] extends ComponentGen[A] with ObservableBase