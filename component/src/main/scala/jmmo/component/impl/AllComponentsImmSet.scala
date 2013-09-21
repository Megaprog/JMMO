package jmmo.component.impl

import jmmo.component.Component

/**
 * User: Tomas
 * Date: 15.09.13
 * Time: 22:40
 */
trait AllComponentsImmSet extends AllComponentsSupport {

  protected var allComponents = createAllComponents

  protected def allComponentsAdd(component: Component[_]) {
    allComponents += component.componentType
  }

  protected def allComponentsRemove(component: Component[_]) {
    allComponents -= component.componentType
  }

  protected def allComponentsContains(component: Component[_]): Boolean = {
    allComponents.contains(component.componentType)
  }

  protected def createAllComponents = Set.empty[Class[_]]
}
