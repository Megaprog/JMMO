package jmmo.observable

import scala.reflect.ClassTag

/**
 * User: Tomas
 * Date: 07.09.13
 * Time: 20:19
 */
object Chain {

  def exact1[A](chain: ObservableListener.Chain)(op: (A) => Unit)(implicit tagA: ClassTag[A]) {
    if (chain.size == 1) {
      if (tagA.runtimeClass.isInstance(chain(0))) {
        op(chain(0).asInstanceOf[A])
      }
    }
  }
}
