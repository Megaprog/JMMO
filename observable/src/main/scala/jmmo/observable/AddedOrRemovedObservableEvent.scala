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
abstract class AddedOrRemovedObservableEvent extends ObservableEvent {

  /**
   * @return added or removed to(from) source `Observable`
   */
  def participant: Observable
}

case class AddedObservableEvent(source: Observable, participant: Observable) extends AddedOrRemovedObservableEvent

case class RemovedObservableEvent(source: Observable, participant: Observable) extends AddedOrRemovedObservableEvent