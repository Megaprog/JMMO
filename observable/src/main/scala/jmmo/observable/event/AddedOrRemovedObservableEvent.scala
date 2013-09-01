package jmmo.observable.event

import jmmo.observable.ObservableEvent
import java.util.Observable

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 18:25
 */
/**
 * Event of adding or removing [[java.util.Observable]] to/from container.
 */
abstract class AddedOrRemovedObservableEvent extends ObservableEvent {

  /**
   * @return added or removed to(from) source `Observable`
   */
  def participant: Observable
}

case class AddedObservableEvent(source: AnyRef, participant: Observable) extends AddedOrRemovedObservableEvent

case class RemovedObservableEvent(source: AnyRef, participant: Observable) extends AddedOrRemovedObservableEvent