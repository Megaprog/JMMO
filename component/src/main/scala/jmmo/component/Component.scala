package jmmo.component

import scala.reflect.ClassTag
import jmmo.observable.Observable

/**
 * User: Tomas
 * Date: 08.09.13
 * Time: 11:59
 */
trait Component[A] extends Observable {

  def componentType: Class[A]

  def forPrimary[U](handler: A => U): U

  def forSecondary[I, U](handler: I => U)(implicit tagI: ClassTag[I])

  def containerAvailable(container: ComponentContainer)

  def containerRevoked(container: ComponentContainer)

  def componentAvailable(component: Component[_])

  def componentRevoked(component: Component[_])
}
