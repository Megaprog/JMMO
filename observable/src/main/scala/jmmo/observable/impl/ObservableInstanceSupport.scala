package jmmo.observable.impl

import jmmo.observable.Observable

/**
 * User: Tomas
 * Date: 14.10.13
 * Time: 16:28
 */
trait ObservableInstanceSupport { this: Observable =>

  protected def observableInstance: Observable = this
}
