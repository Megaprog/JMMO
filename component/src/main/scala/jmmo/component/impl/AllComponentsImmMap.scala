package jmmo.component.impl

import jmmo.component.Component

/**
 * User: Tomas
 * Date: 15.09.13
 * Time: 22:40
 */
trait AllComponentsImmMap extends AllComponentsSupport {

  protected var allComponents = createAllComponents

  protected def allComponentsAdd(component: Component[_]) {
    allComponents += (component.componentType -> component)
  }

  protected def allComponentsRemove(componentType: Class[_]) {
    allComponents -= componentType
  }

  protected def createAllComponents = Map.empty[Class[_], Component[_]]
}
