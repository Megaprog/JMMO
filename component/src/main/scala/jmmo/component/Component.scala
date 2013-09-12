package jmmo.component

import scala.reflect.ClassTag

/**
 * User: Tomas
 * Date: 08.09.13
 * Time: 11:59
 */
trait Component[A] {

  def componentType: Class[A]

  def componentIface: A

  def handleIface[I](handler: (I) => Unit)(implicit tag: ClassTag[I])

  def containerAvailable(container: ComponentContainer)

  def containerRevoked(container: ComponentContainer)

  def componentAvailable(component: Component[_])

  def componentRevoked(component: Component[_])
}
