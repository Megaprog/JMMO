package jmmo.component

/**
 * User: Tomas
 * Date: 15.09.13
 * Time: 22:50
 */
trait AvailableComponentsImmMap extends AvailableComponentsSupport {

  protected var availableComponents = createAvailableComponents

  protected def childObservablesAdd(component: Component[_]): Boolean = {
    if (!availableComponents.contains(component.componentType)) {
      availableComponents += (component.componentType -> component)
      true
    }
    else {
      false
    }
  }

  protected def childObservablesRemove(component: Component[_]): Boolean = {
    if (availableComponents.contains(component.componentType)) {
      availableComponents -= component.componentType
      true
    }
    else {
      false
    }
  }

  protected def childObservables = availableComponents.values

  protected def availableComponent[C](componentClass: Class[C]): Option[Component[C]] = {
    availableComponents.get(componentClass).asInstanceOf[Option[Component[C]]]
  }

  protected def createAvailableComponents = Map.empty[Class[_], Component[_]]
}
