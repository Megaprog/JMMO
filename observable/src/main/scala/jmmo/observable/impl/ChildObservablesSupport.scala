package jmmo.observable.impl

import jmmo.observable.Observable

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 21:38
 */
trait ChildObservablesSupport[A <: Observable] extends ChildObservablesTraversable[A] {

  protected def childObservablesAdd(observable: A): Boolean

  protected def childObservablesRemove(observable: A): Boolean
}
