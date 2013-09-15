/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.observable

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
class ObservableFirerSpec(creator: => PublicFirer) extends WordSpec with ShouldMatchers with MockitoSugar {

  def this() = this(new ObservableBase with PublicFirer {})

  "An ObservableFirer" should {

    val observable = creator
    val handler1 = mock[ObservableListener.Handler]
    val listener1 = ObservableListener(handler1)
    val handler2 = mock[ObservableListener.Handler]
    val listener2 = ObservableListener(handler2)
    val handler3 = mock[ObservableListener.Handler]

    val event = new ObservableEvent { def source = null }

    "provide an addObservableListener method to add new listener and not allow to add duplicate one" in {
      observable.addObservableListener(listener1)

      evaluating {observable.addObservableListener(listener1)} should produce[IllegalArgumentException]
    }

    "provide a fireObservableEvent method to fire event" in {
      observable.publicFireObservableEvent(event)
      verify(handler1).apply(event, Seq.empty)
      verify(handler2, never()).apply(event, Seq.empty)

      reset(handler1, handler2)
      observable.addObservableListener(listener2)
      observable.publicFireObservableEvent(event)
      verify(handler1).apply(event, Seq.empty)
      verify(handler2).apply(event, Seq.empty)

      info("if listenerWrapper's level is smaller than zero event will not be handled")
      observable.addObservableListener(ObservableListener(handler3, level = -1))
      observable.publicFireObservableEvent(event)
      verifyZeroInteractions(handler3)
    }

    "provide a removeObservableListener method to remove listener" in {
      reset(handler1, handler2)
      observable.removeObservableListener(listener1)
      observable.publicFireObservableEvent(event)
      verify(handler1, never()).apply(event, Seq.empty)
      verify(handler2).apply(event, Seq.empty)

      info("if removed listener doesn't contains in observable nothing happens")
      observable.removeObservableListener(listener1)

      reset(handler1, handler2)
      observable.removeObservableListener(listener2)
      observable.publicFireObservableEvent(event)
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
      observable.publicFireObservableEvent(event)
      flag should be (true)
      verifyZeroInteractions(handler1)

      flag = false
      observable.publicFireObservableEvent(event)
      flag should be (false)
      verify(handler1).apply(event, Seq.empty)
    }
  }
}

trait PublicFirer extends ObservableFirer {

  def publicFireObservableEvent(event: ObservableEvent) = fireObservableEvent(event)
}