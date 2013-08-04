package jmmo.observables

/**
 * User: Tomas
 * Date: 04.08.13
 * Time: 17:24
 */
trait ObservableHandler[-A <: ObservableEvent] extends ((A, Observable*) => Unit)
