package jmmo.observable.impl

import jmmo.observable.Observable

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 21:36
 */
trait ChildElementsTraversable[+A <: Observable] {

  protected def childElements: TraversableOnce[A]
}
