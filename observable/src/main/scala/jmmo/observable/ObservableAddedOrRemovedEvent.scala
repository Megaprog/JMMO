package jmmo.observable

/**
 * Event of adding or removing [[scala.Any]] to/from container.
 * Can be fired not only [[jmmo.observable.ObservableContainer]] but any other [[jmmo.observable.Observable]]
 * which contains child elements.
 */
sealed trait ElementAddedOrRemovedEvent extends ObservableEvent {

  /**
   * @return added or removed child element to(from) source `Observable`
   */
  def participant: Any
}

case class ElementAddedEvent(source: Observable, participant: Any) extends ElementAddedOrRemovedEvent

case class ElementRemovedEvent(source: Observable, participant: Any) extends ElementAddedOrRemovedEvent

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 18:25
 *
 * Event of adding or removing [[java.util.Observable]] to/from container.
 * Can be fired not only [[jmmo.observable.ObservableContainer]] but any other [[jmmo.observable.Observable]]
 * which contains child observables.
 */
sealed trait ObservableAddedOrRemovedEvent extends ElementAddedOrRemovedEvent {

  /**
   * @return added or removed child observable to(from) source `Observable`
   */
  def participant: Observable
}

case class ObservableAddedEvent(source: Observable, participant: Observable) extends ObservableAddedOrRemovedEvent

case class ObservableRemovedEvent(source: Observable, participant: Observable) extends ObservableAddedOrRemovedEvent
