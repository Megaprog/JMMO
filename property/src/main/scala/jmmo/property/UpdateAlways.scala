package jmmo.property

/**
 * User: Tomas
 * Date: 04.09.13
 * Time: 12:19
 */
trait UpdateAlways[A] extends PropertyBase[A] {

  override protected def isItNew(value: A): Boolean = true
}
