package jmmo.observable

/**
 * User: Tomas
 * Date: 31.08.13
 * Time: 20:57
 *
 *<p>Fires events:</p>
 *[[jmmo.observable.AddedObservableEvent]]<br>
 *[[jmmo.observable.RemovedObservableEvent]]<br>
 */
trait ObservableContainer[A <: Observable] extends ObservableFirer {

  protected def addChildObservable(observable: A): Boolean

  protected def removeChildObservable(observable: A): Boolean

  protected def childObservables: { def foreach[U](f: A => U): Unit }
}
