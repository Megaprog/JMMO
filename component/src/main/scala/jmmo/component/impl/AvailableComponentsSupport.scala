package jmmo.component.impl

import jmmo.observable.impl.ChildObservablesSupport
import jmmo.component.Component

/**
 * User: Tomas
 * Date: 15.09.13
 * Time: 22:49
 */
trait AvailableComponentsSupport extends ChildObservablesSupport[Component[_]] {

  protected def availableComponent[C](componentClass: Class[C]): Option[Component[C]]
}
