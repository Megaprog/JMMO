package jmmo.observable.impl

import jmmo.observable.Observable

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 21:38
 */
trait ChildElementsSupport[A <: Observable] extends ChildElementsTraversable[A] {

  protected def childElementsAdd(observable: A): Boolean

  protected def childElementsRemove(observable: A): Boolean
}
