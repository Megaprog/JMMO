package jmmo.observable

/**
 * User: Tomas
 * Date: 31.08.13
 * Time: 20:57
 *
 *<p>Fires events:</p>
 *[[jmmo.observable.ObservableAddedEvent]]<br>
 *[[jmmo.observable.ObservableRemovedEvent]]<br>
 */
trait ObservableContainer[A] extends ObservableFirer {

  protected def addChildElement(element: A): Boolean

  protected def removeChildElement(element: A): Boolean

  protected def childElements: TraversableOnce[A]
}
