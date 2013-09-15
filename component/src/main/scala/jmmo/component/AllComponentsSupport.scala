package jmmo.component

/**
 * User: Tomas
 * Date: 15.09.13
 * Time: 22:36
 */
trait AllComponentsSupport {

  protected def allComponentsAdd(component: Component[_])

  protected def allComponentsRemove(component: Component[_])

  protected def allComponentsContains(component: Component[_]): Boolean

  protected def allComponents: collection.Set[Class[_]]
}
