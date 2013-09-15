package jmmo.component

import jmmo.observable.{Observable, ObservableBase}
import jmmo.observable.impl.{ChildListenersImmSet, ObservableContainerMut}
import scala.reflect.ClassTag


/**
 * User: Tomas
 * Date: 12.09.13
 * Time: 12:11
 */
trait ComponentContainerBase extends ComponentContainer with ObservableBase with ObservableContainerMut[Observable] with ChildListenersImmSet {

  protected var allComponents = Set.empty[Class[_]]
  protected var availableComponents = Map.empty[Class[_], Component[_]]

  def components: collection.Set[Class[_]] = allComponents

  def isComponentAvailable(componentClass: Class[_]): Boolean = availableComponents.contains(componentClass)

  def forPrimary[C, U](handler: C => U)(implicit tagC: ClassTag[C]): Option[U] =
    availableComponents.get(tagC.runtimeClass) map (_.asInstanceOf[Component[C]].forPrimary(handler))

  def forSecondary[I, U](handler: I => U)(implicit tagI: ClassTag[I]) {
    availableComponents.values foreach (_.forSecondary(handler))
  }

  def addComponent(component: Component[_]) {}

  def removeComponent(component: Component[_]) {}

  def becomeAvailable(availableComponent: Component[_]) {}

  def becomeRevoked(revokedComponent: Component[_]) {}


  protected def childObservables: {def foreach[U](f: (Observable) => U): Unit} = ???

  protected def childObservablesAdd(observable: Observable): Boolean = ???

  protected def childObservablesRemove(observable: Observable): Boolean = ???
}
