package jmmo.property

import jmmo.observable.ObservableEvent

/**
 * User: Tomas
 * Date: 03.09.13
 * Time: 10:52
 */
case class ChangedValueEvent[A](source: ObservableValue[A], oldValue: A, newValue: A) extends ObservableEvent {

  def this(source: ObservableValue[A], oldValue: A) = this(source, oldValue, source())
}
