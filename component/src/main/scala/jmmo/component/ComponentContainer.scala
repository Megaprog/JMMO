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

  def isComponentAvailable(componentType: Class[_]): Boolean

  def forPrimary[C, U](handler: C => U)(implicit tagC: ClassTag[C]): Option[U]

  def forSecondary[I, U](handler: I => U)(implicit tagI: ClassTag[I])

  def addComponent(component: Component[_])

  def removeComponent(componentClass: Class[_])
}
