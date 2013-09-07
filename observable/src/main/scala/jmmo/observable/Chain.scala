package jmmo.observable

import scala.reflect.ClassTag

/**
 * User: Tomas
 * Date: 07.09.13
 * Time: 20:19
 */
object Chain {

  def exact1[A <: Observable](chain: ObservableListener.Chain)(op: (A) => Unit)(implicit tagA: ClassTag[A]) {
    if (chain.size == 1) {
      if (tagA.runtimeClass.isInstance(chain(0))) {
        op(chain(0).asInstanceOf[A])
      }
    }
  }

  def exact2[A <: Observable, B <: Observable](chain: ObservableListener.Chain)(op: (A, B) => Unit)(implicit tagA: ClassTag[A], tagB: ClassTag[B]) {
    if (chain.size == 2) {
      if (tagA.runtimeClass.isInstance(chain(0)) && tagB.runtimeClass.isInstance(chain(1))) {
        op(chain(0).asInstanceOf[A], chain(1).asInstanceOf[B])
      }
    }
  }

  def exact3[A <: Observable, B <: Observable, C <: Observable](chain: ObservableListener.Chain)(op: (A, B, C) => Unit)
                                                               (implicit tagA: ClassTag[A], tagB: ClassTag[B], tagC: ClassTag[C]) {
    if (chain.size == 3) {
      if (tagA.runtimeClass.isInstance(chain(0)) && tagB.runtimeClass.isInstance(chain(1)) && tagC.runtimeClass.isInstance(chain(2))) {
        op(chain(0).asInstanceOf[A], chain(1).asInstanceOf[B], chain(2).asInstanceOf[C])
      }
    }
  }

  def exact4[A <: Observable, B <: Observable, C <: Observable, D <: Observable](chain: ObservableListener.Chain)(op: (A, B, C, D) => Unit)
                                               (implicit tagA: ClassTag[A], tagB: ClassTag[B], tagC: ClassTag[C], tagD: ClassTag[D]) {
    if (chain.size == 4) {
      if (tagA.runtimeClass.isInstance(chain(0)) && tagB.runtimeClass.isInstance(chain(1)) && tagC.runtimeClass.isInstance(chain(2))
        && tagD.runtimeClass.isInstance(chain(3))) {
        op(chain(0).asInstanceOf[A], chain(1).asInstanceOf[B], chain(2).asInstanceOf[C], chain(3).asInstanceOf[D])
      }
    }
  }

  def exact5[A <: Observable, B <: Observable, C <: Observable, D <: Observable, E <: Observable](chain: ObservableListener.Chain)
        (op: (A, B, C, D, E) => Unit)(implicit tagA: ClassTag[A], tagB: ClassTag[B], tagC: ClassTag[C], tagD: ClassTag[D], tagE: ClassTag[E]) {
    if (chain.size == 5) {
      if (tagA.runtimeClass.isInstance(chain(0)) && tagB.runtimeClass.isInstance(chain(1)) && tagC.runtimeClass.isInstance(chain(2))
        && tagD.runtimeClass.isInstance(chain(3)) && tagE.runtimeClass.isInstance(chain(4))) {
        op(chain(0).asInstanceOf[A], chain(1).asInstanceOf[B], chain(2).asInstanceOf[C], chain(3).asInstanceOf[D], chain(4).asInstanceOf[E])
      }
    }
  }

  def exact6[A <: Observable, B <: Observable, C <: Observable, D <: Observable, E <: Observable, F <: Observable](chain: ObservableListener.Chain)
        (op: (A, B, C, D, E, F) => Unit)(implicit tagA: ClassTag[A], tagB: ClassTag[B], tagC: ClassTag[C], tagD: ClassTag[D], tagE: ClassTag[E], tagF: ClassTag[F]) {
    if (chain.size == 6) {
      if (tagA.runtimeClass.isInstance(chain(0)) && tagB.runtimeClass.isInstance(chain(1)) && tagC.runtimeClass.isInstance(chain(2))
        && tagD.runtimeClass.isInstance(chain(3)) && tagE.runtimeClass.isInstance(chain(4)) && tagF.runtimeClass.isInstance(chain(5))) {
        op(chain(0).asInstanceOf[A], chain(1).asInstanceOf[B], chain(2).asInstanceOf[C], chain(3).asInstanceOf[D], chain(4).asInstanceOf[E], chain(5).asInstanceOf[F])
      }
    }
  }

  def exact7[A <: Observable, B <: Observable, C <: Observable, D <: Observable, E <: Observable, F <: Observable, G <: Observable]
      (chain: ObservableListener.Chain)(op: (A, B, C, D, E, F, G) => Unit)(implicit tagA: ClassTag[A], tagB: ClassTag[B], tagC: ClassTag[C],
                                        tagD: ClassTag[D], tagE: ClassTag[E], tagF: ClassTag[F], tagG: ClassTag[G]) {
    if (chain.size == 7) {
      if (tagA.runtimeClass.isInstance(chain(0)) && tagB.runtimeClass.isInstance(chain(1)) && tagC.runtimeClass.isInstance(chain(2))
          && tagD.runtimeClass.isInstance(chain(3)) && tagE.runtimeClass.isInstance(chain(4)) && tagF.runtimeClass.isInstance(chain(5))
          && tagG.runtimeClass.isInstance(chain(6))) {
        op(chain(0).asInstanceOf[A], chain(1).asInstanceOf[B], chain(2).asInstanceOf[C], chain(3).asInstanceOf[D], chain(4).asInstanceOf[E],
          chain(5).asInstanceOf[F], chain(6).asInstanceOf[G])
      }
    }
  }

  def exact8[A <: Observable, B <: Observable, C <: Observable, D <: Observable, E <: Observable, F <: Observable, G <: Observable, H <: Observable]
      (chain: ObservableListener.Chain)(op: (A, B, C, D, E, F, G, H) => Unit)(implicit tagA: ClassTag[A], tagB: ClassTag[B], tagC: ClassTag[C],
                                        tagD: ClassTag[D], tagE: ClassTag[E], tagF: ClassTag[F], tagG: ClassTag[G], tagH: ClassTag[H]) {
    if (chain.size == 8) {
      if (tagA.runtimeClass.isInstance(chain(0)) && tagB.runtimeClass.isInstance(chain(1)) && tagC.runtimeClass.isInstance(chain(2))
        && tagD.runtimeClass.isInstance(chain(3)) && tagE.runtimeClass.isInstance(chain(4)) && tagF.runtimeClass.isInstance(chain(5))
        && tagG.runtimeClass.isInstance(chain(6)) && tagH.runtimeClass.isInstance(chain(7))) {
        op(chain(0).asInstanceOf[A], chain(1).asInstanceOf[B], chain(2).asInstanceOf[C], chain(3).asInstanceOf[D], chain(4).asInstanceOf[E],
          chain(5).asInstanceOf[F], chain(6).asInstanceOf[G], chain(7).asInstanceOf[H])
      }
    }
  }

  def exact9[A <: Observable, B <: Observable, C <: Observable, D <: Observable, E <: Observable, F <: Observable, G <: Observable, H <: Observable, I <: Observable]
  (chain: ObservableListener.Chain)(op: (A, B, C, D, E, F, G, H, I) => Unit)(implicit tagA: ClassTag[A], tagB: ClassTag[B], tagC: ClassTag[C],
                              tagD: ClassTag[D], tagE: ClassTag[E], tagF: ClassTag[F], tagG: ClassTag[G], tagH: ClassTag[H], tagI: ClassTag[I]) {
    if (chain.size == 9) {
      if (tagA.runtimeClass.isInstance(chain(0)) && tagB.runtimeClass.isInstance(chain(1)) && tagC.runtimeClass.isInstance(chain(2))
        && tagD.runtimeClass.isInstance(chain(3)) && tagE.runtimeClass.isInstance(chain(4)) && tagF.runtimeClass.isInstance(chain(5))
        && tagG.runtimeClass.isInstance(chain(6)) && tagH.runtimeClass.isInstance(chain(7)) && tagI.runtimeClass.isInstance(chain(8))) {
        op(chain(0).asInstanceOf[A], chain(1).asInstanceOf[B], chain(2).asInstanceOf[C], chain(3).asInstanceOf[D], chain(4).asInstanceOf[E],
          chain(5).asInstanceOf[F], chain(6).asInstanceOf[G], chain(7).asInstanceOf[H], chain(8).asInstanceOf[I])
      }
    }
  }

}
