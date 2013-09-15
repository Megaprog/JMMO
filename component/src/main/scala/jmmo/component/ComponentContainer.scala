package jmmo.component

import jmmo.observable.Observable
import scala.reflect.ClassTag

/**
 * User: Tomas
 * Date: 11.09.13
 * Time: 8:13
 */
trait ComponentContainer extends Observable {

  def components: collection.Set[Class[_]]

  def isComponentAvailable(componentClass: Class[_]): Boolean

  def forPrimary[C, U](handler: C => U)(implicit tagC: ClassTag[C]): Option[U]

  def forSecondary[I, U](handler: I => U)(implicit tagI: ClassTag[I])

  def addComponent(component: Component[_])

  def removeComponent(component: Component[_])

  def becomeAvailable(availableComponent: Component[_])

  def becomeRevoked(revokedComponent: Component[_])
}
