package jmmo.property

/**
 * User: Tomas
 * Date: 04.09.13
 * Time: 12:19
 *
 * A Property extends this trait will updates it value and fire ChangedValueEvent in spite of new value equals old one
 */
trait UpdateAlways[A] extends PropertyBase[A] {

  override protected def isItNew(value: A): Boolean = true
}
