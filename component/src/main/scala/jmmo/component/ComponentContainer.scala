package jmmo.component

import jmmo.observable.Observable
import scala.reflect.ClassTag

/**
 * User: Tomas
 * Date: 11.09.13
 * Time: 8:13
 */
trait ComponentContainer extends Observable {

  def components: Seq[Class[_]]

  def iface[C](clazz: Class[C]): Option[C]

  def iface[C](implicit tag: ClassTag[C]): Option[C]

  def handleIface[I](handler: (I) => Unit)(implicit tag: ClassTag[I])

  def addComponent(component: Component[_])

  def removeComponent(component: Component[_])

  def becomeAvailable(availableComponent: Component[_])

  def becomeRevoked(revokedComponent: Component[_])
}
