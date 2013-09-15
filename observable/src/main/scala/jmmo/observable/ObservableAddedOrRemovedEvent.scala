package jmmo.observable

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 18:25
 *
 * Event of adding or removing [[java.util.Observable]] to/from container.
 * Can be fired not only [[jmmo.observable.ObservableContainer]] but any other [[jmmo.observable.Observable]]
 * which contains child observables.
 */
abstract class ObservableAddedOrRemovedEvent extends ObservableEvent {

  /**
   * @return added or removed to(from) source `Observable`
   */
  def participant: Observable
}

case class ObservableAddedEvent(source: Observable, participant: Observable) extends ObservableAddedOrRemovedEvent

case class ObservableRemovedEvent(source: Observable, participant: Observable) extends ObservableAddedOrRemovedEvent