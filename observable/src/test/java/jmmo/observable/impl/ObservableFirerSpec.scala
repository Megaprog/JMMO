/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observable.impl

import jmmo.observable.{ObservableEvent, ObservableListener, ObservableBase}
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import java.lang.IllegalArgumentException

/**
 * User: Tomas
 * Date: 11.08.13
 * Time: 13:01
 */
class ObservableFirerSpec(creator: => ObservableFirer) extends WordSpec with ShouldMatchers with MockitoSugar {

  def this() = this(new ObservableBase {})

  "An ObservableFirer" should {

    val observable = creator
    val handler1 = mock[ObservableListener.Handler]
    val listener1 = ObservableListener(handler1)
    val handler2 = mock[ObservableListener.Handler]
    val listener2 = ObservableListener(handler2)

    val event = new ObservableEvent(new AnyRef)

    "have addObservableListener method to add new listener and not allow to add duplicate one" in {
      observable.addObservableListener(listener1)

      evaluating {observable.addObservableListener(listener1)} should produce[IllegalArgumentException]
    }

    "have fireObservableEvent method to fire event" in {
      observable.fireObservableEvent(event)
      verify(handler1).apply(event, Seq.empty)
      verify(handler2, never()).apply(event, Seq.empty)

      reset(handler1, handler2)
      observable.addObservableListener(listener2)
      observable.fireObservableEvent(event)
      verify(handler1).apply(event, Seq.empty)
      verify(handler2).apply(event, Seq.empty)
    }

    "have removeObservableListener method to remove listener" in {
      reset(handler1, handler2)
      observable.removeObservableListener(listener1)
      observable.fireObservableEvent(event)
      verify(handler1, never()).apply(event, Seq.empty)
      verify(handler2).apply(event, Seq.empty)

      info("if removed listener doesn't contains in observable nothing happens")
      observable.removeObservableListener(listener1)

      reset(handler1, handler2)
      observable.removeObservableListener(listener2)
      observable.fireObservableEvent(event)
      verifyZeroInteractions(handler1, handler2)
    }

    "allow to add or remove listeners inside event handling" in {

    }
  }
}
