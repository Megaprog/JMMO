package jmmo.observable

/**
 * User: Tomas
 * Date: 31.08.13
 * Time: 20:57
 */
trait ObservableContainer[A <: Observable] extends Observable {

  protected def addChildObservable(observable: A): Boolean

  protected def removeChildObservable(observable: A): Boolean

  protected def childObservables: TraversableOnce[A]
}
