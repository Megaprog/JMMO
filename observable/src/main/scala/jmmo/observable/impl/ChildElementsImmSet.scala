package jmmo.observable.impl

import jmmo.observable.Observable

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 21:19
 */
trait ChildElementsImmSet[A <: Observable] extends ChildElementsSupport[A] {

  protected var childElements = createChildObservables

  protected def childElementsAdd(observable: A): Boolean = {
    if (!childElements.contains(observable)) {
      childElements += observable
      true
    }
    else {
      false
    }
  }

  protected def childElementsRemove(observable: A): Boolean = {
    if (childElements.contains(observable)) {
      childElements -= observable
      true
    }
    else {
      false
    }
  }

  protected def createChildObservables = Set.empty[A]
}
