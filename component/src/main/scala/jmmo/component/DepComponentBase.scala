package jmmo.component

import jmmo.observable.ObservableBase
import jmmo.component.impl.DepComponentGen

/**
 * User: Tomas
 * Date: 21.09.13
 * Time: 23:07
 */
trait DepComponentBase[A] extends DepComponentGen[A] with ObservableBase
