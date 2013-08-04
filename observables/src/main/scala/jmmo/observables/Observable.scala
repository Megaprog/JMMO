/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observables

/**
 * User: Tomas
 * Date: 04.08.13
 * Time: 14:31
 */
trait Observable {

  def addObservableListener()

  def removeObservableListener()
}
