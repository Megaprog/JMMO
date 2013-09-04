/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.property

import jmmo.observable.Observable

/**
 * User: Tomas
 * Date: 02.09.13
 * Time: 15:53
 *
 * Observable which contains some value.
 *
 *<p>Fires events:</p>
 *[[jmmo.property.ChangedValueEvent]]<br>
 *
 * @tparam A type of value
 */
trait ObservableValue[+A] extends Observable with Equals {

  /**
   * @return an `Observable` value.
   */
  def apply(): A

  /**
   * @return the name of the value. It is desirable to be unique but can be any except null
   */
  def name: String
}

object ObservableValue {

  /**
   * Extracts name and value of the `ObservableValue`
   */
  def unapply[A](observableValue: ObservableValue[A]) = if (observableValue eq null) None else Some(observableValue.name, observableValue())
}
