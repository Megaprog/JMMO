package jmmo.component.example

import jmmo.component.DepComponentBase
import scala.collection.Set

/**
 * User: Tomas
 * Date: 19.09.13
 * Time: 13:11
 */
class SlaveComponent extends DepComponentBase[Slave] with Slave {

  def componentType = classOf[Slave]

  def tasks = 8

  val require: Set[Class[_]] = Set(classOf[Master])
}
