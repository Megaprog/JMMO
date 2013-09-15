package jmmo.component

import jmmo.observable.{Observable, ObservableEvent}

/**
 * User: Tomas
 * Date: 15.09.13
 * Time: 11:14
 */
sealed trait ComponentAvailableOrRevokedEvent[A] extends ObservableEvent {

  def source: Component[A]
}

case class ComponentAvailable[A](source: Component[A]) extends ComponentAvailableOrRevokedEvent[A]

case class ComponentRevoked[A](source: Component[A]) extends ComponentAvailableOrRevokedEvent[A]
