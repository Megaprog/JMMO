package jmmo.observable

/**
 * User: Tomas
 * Date: 31.08.13
 * Time: 20:57
 *
 *<p>Fires events:</p>
 *[[AddedObservableEvent]]<br>
 *[[RemovedObservableEvent]]<br>
 */
trait ObservableContainer[A <: Observable] extends Observable {

  protected def addChildObservable(observable: A): Boolean

  protected def removeChildObservable(observable: A): Boolean

  protected def childObservables: TraversableOnce[A]
}
