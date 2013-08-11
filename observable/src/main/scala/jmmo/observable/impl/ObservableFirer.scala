/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observable.impl

import jmmo.observable.{ObservableEvent, Observable}

/**
 * User: Tomas
 * Date: 11.08.13
 * Time: 13:12
 */
trait ObservableFirer extends Observable {

  protected[impl] def fireObservableEvent(event: ObservableEvent)
}
