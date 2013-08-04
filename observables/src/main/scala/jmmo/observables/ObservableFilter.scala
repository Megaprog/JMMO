package jmmo.observables

/**
 * User: Tomas
 * Date: 04.08.13
 * Time: 17:26
 */
trait ObservableFilter[-A <: Observable] extends ((A, Observable*) => Boolean)
