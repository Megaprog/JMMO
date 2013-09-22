package jmmo.component.impl

import jmmo.component.Component

/**
 * User: Tomas
 * Date: 15.09.13
 * Time: 22:36
 */
trait AllComponentsSupport {

  protected def allComponentsAdd(component: Component[_])

  protected def allComponentsRemove(componentType: Class[_])

  protected def allComponents: collection.Map[Class[_], Component[_]]
}
