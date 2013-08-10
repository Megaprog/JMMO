/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observable

/**
 * User: Tomas
 * Date: 04.08.13
 * Time: 14:31
 */
trait Observable {

  def addObservableListener(listener: ObservableListener)

  def removeObservableListener(listener: ObservableListener)
}
