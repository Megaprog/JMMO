package jmmo.property

/**
 * User: Tomas
 * Date: 03.09.13
 * Time: 10:39
 */
trait ObservableProperty[A] extends ObservableValue[A] {

  /**
   * Change `Property` value to specified one.
   * @param value new Property value
   */
  def update(value: A)
}
