package jmmo.observable.impl

import jmmo.observable.{Observable, ObservableListener}

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 18:03
 */
trait TransparentContainerGen[+A <: Observable] extends TransparentContainer[A] with ObservableInstanceSupport {

  override protected def observableInstance: Observable
}
