package jmmo.component

import jmmo.observable._
import jmmo.observable.impl.{ChildListenersImmSet, ObservableContainerMut}
import scala.reflect.ClassTag

/**
 * User: Tomas
 * Date: 12.09.13
 * Time: 12:11
 */
trait ComponentContainerBase extends ComponentContainer with ObservableBase with ObservableContainerMut[Component[_]] with ChildListenersImmSet {

  protected var allComponents = Set.empty[Class[_]]
  protected var availableComponents = Map.empty[Class[_], Component[_]]

  def components: collection.Set[Class[_]] = allComponents

  def isComponentAvailable(componentClass: Class[_]): Boolean = availableComponents.contains(componentClass)

  def forPrimary[C, U](handler: C => U)(implicit tagC: ClassTag[C]): Option[U] =
    availableComponent map (_.forPrimary(handler))

  def forSecondary[I, U](handler: I => U)(implicit tagI: ClassTag[I]) {
    childObservables foreach (_.forSecondary(handler))
  }

  def addComponent(component: Component[_]) {
    if (allComponentsContains(component)) {
      throw new IllegalArgumentException(s"Can't add $component with duplicate component type ${component.componentType} to $this")
    }

    allComponentsAdd(component)



    component.containerAvailable(this)
  }

  def removeComponent(component: Component[_]) {
    if (!allComponentsContains(component)) {
      throw new IllegalArgumentException(s"$component was not found in $this")
    }

    allComponentsRemove(component)



    component.containerRevoked(this)

    removeChildObservable(component)
  }

  protected val componentListener = ObservableListener( (event, _) => event match {
    case ComponentAvailable(component) => println(component)
    case ComponentRevoked(component) => println(component)
  }, level = ObservableListener.ParentLevel)

  protected def allComponentsAdd(component: Component[_]) {
    allComponents += component.componentType
  }

  protected def allComponentsRemove(component: Component[_]) {
    allComponents -= component.componentType
  }

  protected def allComponentsContains(component: Component[_]): Boolean = {
    allComponents.contains(component.componentType)
  }

  protected def availableComponent[C](implicit tagC: ClassTag[C]): Option[Component[C]] = {
    availableComponents.get(tagC.runtimeClass).asInstanceOf[Option[Component[C]]]
  }

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
}
