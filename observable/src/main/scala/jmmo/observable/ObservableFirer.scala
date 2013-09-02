/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observable

/**
 * User: Tomas
 * Date: 11.08.13
 * Time: 13:12
 *
 * Observable which can fire events
 */
trait ObservableFirer extends Observable {

  protected def fireObservableEvent(event: ObservableEvent)
}
