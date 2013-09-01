package jmmo.observable.impl

import jmmo.observable.{Observable, ObservableListener}

/**
 * User: Tomas
 * Date: 01.09.13
 * Time: 18:03
 */
trait TransparentContainer[+A <: Observable] extends ObservableContainerImm[A] {

  abstract override protected def createChildListener(listener: ObservableListener): ObservableListener = listener
}
