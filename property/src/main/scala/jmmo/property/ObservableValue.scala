/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.property

import java.util.Observable

/**
 * User: Tomas
 * Date: 02.09.13
 * Time: 15:53
 *
 * Observable which contains some value.
 * @tparam A type of value
 */
trait ObservableValue[A] extends Observable {

  /**
   * @return an observable value.
   */
  def apply(): A

  /**
   * @return the name of the value. It is desirable to be unique but can be any except null
   */
  def name(): String
}
