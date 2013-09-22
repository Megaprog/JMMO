/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observable

/**
 * User: Tomas
 * Date: 04.08.13
 * Time: 15:03
 *
 * The parent of all observable events.
 * All observable events should be inheriting from this class.
 */
trait ObservableEvent {

  /**
   * @return the source of the event
   */
  def source: Observable
}

object ObservableEvent {

  /**
   * Extracts the source of the event
   */
  def unapply(event: ObservableEvent) = if (event eq null) None else Some(event.source)
}