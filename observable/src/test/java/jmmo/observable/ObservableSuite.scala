/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observable

import org.scalatest.WordSpec
import jmmo.observable.impl.ListenerWrapperSpec

/**
 * User: Tomas
 * Date: 03.08.13
 * Time: 20:34
 */
class ObservableSuite extends WordSpec {

  override val nestedSuites = List(
    new ObservableListenerSpec,
    new ObservableFirerSpec,
    new ListenerWrapperSpec,
    new ObservableImmContainerSpec,
    new ObservableContainerSpec()
  )

  "Observables" should {

    "fit all specifications in package" in {}

  }
}
