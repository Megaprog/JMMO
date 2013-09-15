package jmmo.component

import jmmo.observable.ObservableListener
import jmmo.observable.impl.{ObservableElement, ObservableContainerMut}
import scala.reflect.ClassTag

/**
 * User: Tomas
 * Date: 12.09.13
 * Time: 12:11
 */
trait ComponentContainerGen extends ComponentContainer with ObservableElement with ObservableContainerMut[Component[_]]
                              with AllComponentsSupport with AvailableComponentsSupport {

  def components: collection.Set[Class[_]] = allComponents

  def isComponentAvailable(componentClass: Class[_]): Boolean = availableComponent(componentClass).isDefined

  def forPrimary[C, U](handler: C => U)(implicit tagC: ClassTag[C]): Option[U] =
    availableComponent(tagC.runtimeClass.asInstanceOf[Class[C]]) map (_.forPrimary(handler))

  def forSecondary[I, U](handler: I => U)(implicit tagI: ClassTag[I]) {
    childObservables foreach (_.forSecondary(handler))
  }

  def addComponent(component: Component[_]) {
    if (allComponentsContains(component)) {
      throw new IllegalArgumentException(s"Can't add $component with duplicate component type ${component.componentType} to $this")
    }

    allComponentsAdd(component)

    component.addObservableListener(componentListener)

    component.containerAvailable(this)
  }

  def removeComponent(component: Component[_]) {
    if (!allComponentsContains(component)) {
      throw new IllegalArgumentException(s"$component was not found in $this")
    }

    allComponentsRemove(component)

    component.removeObservableListener(componentListener)

    component.containerRevoked(this)

    removeChildObservable(component)
  }

  protected val componentListener = ObservableListener( (event, _) => event match {
    case ComponentAvailable(component) => {

      if (isComponentAvailable(component.componentType)) {
        throw new IllegalArgumentException(s"$component was already available in $this")
      }

      addChildObservable(component)
    }

    case ComponentRevoked(component) => {

      if (!isComponentAvailable(component.componentType)) {
        throw new IllegalArgumentException(s"$component was not available in $this")
      }

      removeChildObservable(component)
    }

  }, level = ObservableListener.ParentLevel)
}
