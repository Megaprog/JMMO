/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observable

import java.util.EventObject

/**
 * User: Tomas
 * Date: 04.08.13
 * Time: 15:03
 *
 * The parent of all observable events.
 * All observable events should be inheriting from this class.
 */
class ObservableEvent(source: AnyRef) extends EventObject(source) {
}

object ObservableEvent {

  /**
   * Creates instance of the [[jmmo.observable.ObservableEvent]] with the specified event source.
   * @param source source of the event
   * @return new instance of the [[jmmo.observable.ObservableEvent]] class
   */
  def apply(source: AnyRef) = new ObservableEvent(source)

  def unapply(event: ObservableEvent) = if (event eq null) None else Some(event.getSource)
}