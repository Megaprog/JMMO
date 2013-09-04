package jmmo.property

import jmmo.observable.ObservableBase

/**
 * User: Tomas
 * Date: 04.09.13
 * Time: 11:50
 */
trait PropertyBase[A] extends ObservableProperty[A] with ObservableBase {

  def update(value: A) {
    if (isItNew(value)) {
      val oldValue: A = apply()
      updateInner(value)
      fireObservableEvent(ChangedValueEvent(this, oldValue, value))
    }
  }

  protected def isItNew(value: A): Boolean = {
    value != apply()
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[ObservableValue[A]]

  override def equals(other: Any): Boolean = other match {
    case that: ObservableValue[A] =>
      that.canEqual(this) &&
      name == that.name &&
      this() == that()
    case _ => false
  }

  override def toString: String = s"ObservableProperty(name=$name,value=${apply()})"

  protected def updateInner(value: A)
}
