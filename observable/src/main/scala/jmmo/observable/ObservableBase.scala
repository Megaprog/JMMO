/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observable

import jmmo.observable.impl.{SelfListenersImmutableSet, ObservableElement}

/**
 * User: Tomas
 * Date: 11.08.13
 * Time: 12:48
 */
trait ObservableBase extends ObservableElement with SelfListenersImmutableSet