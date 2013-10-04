package jmmo.component.impl

import jmmo.observable.ObservableListener
import jmmo.observable.impl.{ObservableGen, ObservableContainerMut}
import scala.reflect.ClassTag
import jmmo.component.{ComponentRevoked, ComponentAvailable, Component, ComponentContainer}

/**
 * User: Tomas
 * Date: 12.09.13
 * Time: 12:11
 */
trait ComponentContainerGen extends ComponentContainer with ObservableGen with ObservableContainerMut[Component[_]]
                              with AllComponentsSupport with AvailableComponentsSupport {

  def components: collection.Set[Class[_]] = allComponents.keySet

  def isComponentAvailable(componentType: Class[_]): Boolean = availableComponent(componentType).isDefined

  def forPrimary[C, U](handler: C => U)(implicit tagC: ClassTag[C]): Option[U] =
    availableComponent(tagC.runtimeClass.asInstanceOf[Class[C]]) map (_.forPrimary(handler))

  def forSecondary[I, U](handler: I => U)(implicit tagI: ClassTag[I]) {
    childElements foreach (_.forSecondary(handler))
  }

  def addComponent(component: Component[_]) {
    if (allComponents.contains(component.componentType)) {
      throw new IllegalArgumentException(s"Can't add $component with duplicate component type ${component.componentType} to $this")
    }

    allComponentsAdd(component)

    component.addObservableListener(componentListener)

    component.containerAvailable(this)
  }

  def removeComponent(componentType: Class[_]) {
    val component = allComponents.getOrElse(componentType, null)
    if (component eq null) {
      throw new IllegalArgumentException(s"$componentType was not found in $this")
    }

    allComponentsRemove(componentType)

    component.removeObservableListener(componentListener)

    component.containerRevoked(this)

    removeChildElement(component)
  }

  protected val componentListener = ObservableListener( (event, _) => event match {
    case ComponentAvailable(component) => {

      if (isComponentAvailable(component.componentType)) {
        throw new IllegalArgumentException(s"$component was already available in $this")
      }

      addChildElement(component)
    }

    case ComponentRevoked(component) => {

      if (!isComponentAvailable(component.componentType)) {
        throw new IllegalArgumentException(s"$component was not available in $this")
      }

      removeChildElement(component)
    }

  }, level = ObservableListener.ParentLevel)
}
