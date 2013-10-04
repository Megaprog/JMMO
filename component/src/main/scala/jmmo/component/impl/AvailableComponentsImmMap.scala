package jmmo.component.impl

import jmmo.component.Component

/**
 * User: Tomas
 * Date: 15.09.13
 * Time: 22:50
 */
trait AvailableComponentsImmMap extends AvailableComponentsSupport {

  protected var availableComponents = createAvailableComponents

  protected def childElementsAdd(component: Component[_]): Boolean = {
    if (!availableComponents.contains(component.componentType)) {
      availableComponents += (component.componentType -> component)
      true
    }
    else {
      false
    }
  }

  protected def childElementsRemove(component: Component[_]): Boolean = {
    if (availableComponents.contains(component.componentType)) {
      availableComponents -= component.componentType
      true
    }
    else {
      false
    }
  }

  protected def childElements = availableComponents.values

  protected def availableComponent[C](componentClass: Class[C]): Option[Component[C]] = {
    availableComponents.get(componentClass).asInstanceOf[Option[Component[C]]]
  }

  protected def createAvailableComponents = Map.empty[Class[_], Component[_]]
}
