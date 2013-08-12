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
    val handler3 = mock[ObservableListener.Handler]

    val event = new ObservableEvent(new {})

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

      info("if listener's level is smaller than zero event will not be handled")
      observable.addObservableListener(ObservableListener(handler3, -1))
      observable.fireObservableEvent(event)
      verifyZeroInteractions(handler3)
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
      var flag = false
      lazy val addRemoveInside: ObservableListener = ObservableListener((_, _) => {
        observable.removeObservableListener(addRemoveInside)
        observable.addObservableListener(listener1)
        flag = true
      })

      reset(handler1)
      observable.addObservableListener(addRemoveInside)
      observable.fireObservableEvent(event)
      flag should be (true)
      verifyZeroInteractions(handler1)

      flag = false
      observable.fireObservableEvent(event)
      flag should be (false)
      verify(handler1).apply(event, Seq.empty)
    }
  }
}
