/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observable

import org.scalatest.{Suite, WordSpec}
import jmmo.observable.impl.ListenerWrapperSpec

/**
 * User: Tomas
 * Date: 03.08.13
 * Time: 20:34
 */
class ObservableSuite extends WordSpec {

  override val nestedSuites = Vector(
    new ObservableListenerSpec,
    new ObservableFirerSpec,
    new ListenerWrapperSpec,
    new ObservableImmContainerSpec,
    new ObservableContainerSpec,
    new ObservableTransparentContainerSpec,
    new ListenerBuilderSpec,
    new ChainSpec
  )

  "Observables" should {

    "fit all specifications in package" in {}

  }
}
