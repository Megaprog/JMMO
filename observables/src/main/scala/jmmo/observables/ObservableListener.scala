package jmmo.observables

import java.util.EventListener

/**
 * User: Tomas
 * Date: 04.08.13
 * Time: 17:00
 */
trait ObservableListener extends EventListener {

  type Handler = ObservableHandler[ObservableEvent]

  type Filter = ObservableFilter[Observable]

  /**
   * @return event handler
   */
  def handler: Handler

  /**
   * @return filter of observables
   */
  def filter: Filter

  /**
   * @return Level of child Observers to which will be added the listener
   */
  def level: Int

  /**
   * @return classes of events what listener will handle. If empty all events will be handle
   */
  def classes: collection.Set[Class[_ <: ObservableEvent]]
}
