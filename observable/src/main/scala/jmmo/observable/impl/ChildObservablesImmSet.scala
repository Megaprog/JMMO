package jmmo.observable.impl

import jmmo.observable.Observable

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 21:19
 */
trait ChildObservablesImmSet[A <: Observable] extends ChildObservablesSupport[A] {

  protected var childObservables = createChildObservables

  protected def childObservablesAdd(observable: A): Boolean = {
    if (!childObservables.contains(observable)) {
      childObservables += observable
      true
    }
    else {
      false
    }
  }

  protected def childObservablesRemove(observable: A): Boolean = {
    if (childObservables.contains(observable)) {
      childObservables -= observable
      true
    }
    else {
      false
    }
  }

  protected def createChildObservables = Set.empty[A]
}
