package jmmo.observable.impl

import jmmo.observable.Observable

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 21:36
 */
trait ChildObservablesTraversable[+A <: Observable] {

  protected def childObservables: { def foreach[U](f: A => U): Unit }
}
