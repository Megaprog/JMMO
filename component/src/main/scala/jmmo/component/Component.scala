package jmmo.component

import scala.reflect.ClassTag

/**
 * User: Tomas
 * Date: 08.09.13
 * Time: 11:59
 */
trait Component[A] {

  def getType: Class[A]

  def componentInterface: A

  def forInterface[I](handler: (I) => Unit)(implicit tag: ClassTag[I])

  def containerAvailable(container: ComponentContainer)

  def containerRevoked(container: ComponentContainer)

  def componentAvailable(component: Component[_])

  def componentRevoked(component: Component[_])
}
