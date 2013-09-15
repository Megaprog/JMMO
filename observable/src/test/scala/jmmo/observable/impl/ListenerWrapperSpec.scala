package jmmo.observable.impl

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import jmmo.observable.{ObservableEvent, ObservableListener, Observable}

/**
 * User: Tomas
 * Date: 08.08.13
 * Time: 14:01
 */
class ListenerWrapperSpec extends WordSpec with ShouldMatchers {

  "An ListenerWrapper" should {

    class EmptyObservable extends Observable{
      def addObservableListener(listener: ObservableListener) {}
      def removeObservableListener(listener: ObservableListener) {}
    }

    "provide an apply method to create ListenerWrapper instance" in {
      val observable1 = new EmptyObservable
      val observable2 = new EmptyObservable

      val listener = ObservableListener((_, _) => {}, level = 1)
      val wrapper = ListenerWrapper(listener, 0, observable1, observable2)

      wrapper.wrapped should be theSameInstanceAs(listener)
      wrapper.level should be (0)
      wrapper.chain should be (Seq(observable1, observable2))
    }

    "provide an unapply method for pattern matching" in {
      val observable1 = new EmptyObservable
      val observable2 = new EmptyObservable

      val listener = ObservableListener((_, _) => {}, level = 1)
      val wrapper = ListenerWrapper(listener, 0, observable1, observable2)

      (wrapper match {
        case ListenerWrapper(w, l, o1, o2) if (w eq listener) && (l == 0) && (o1 eq observable1) && (o2 eq observable2) => true
        case _ => false
      }) should be (true)

      (wrapper match {
        case ListenerWrapper(w, l, o1, _*) if (w eq listener) && (l == 0) && (o1 eq observable1) => true
        case _ => false
      }) should be (true)
    }

    "provide a canEqual and equals methods to be equal ListenerWrapper with same parameters" in {
      val observable1 = new EmptyObservable
      val observable2 = new EmptyObservable
      val listener = ObservableListener((_, _) => {}, level = 2)

      val wrapper1 = ListenerWrapper(listener, 1, observable1, observable2)
      val wrapper2 = ListenerWrapper(listener, 1, observable1, observable2)
      val wrapper3 = ListenerWrapper(listener, 1, observable1)

      wrapper1 should equal(wrapper2)
      wrapper2 should equal(wrapper1)

      wrapper1 should not equal(wrapper3)
      wrapper3 should not equal(wrapper1)
    }

    "provide a wrapped method to extract wrapped value from wrapper or original listenerWrapper" in {
      val listener = ObservableListener((_, _) => {})
      ListenerWrapper.wrapped(listener) should be theSameInstanceAs(listener)

      val wrapper = ListenerWrapper(listener, 1)
      ListenerWrapper.wrapped(wrapper) should be theSameInstanceAs(listener)
    }

    "provide a chain method to extract chain value from wrapper or empty sequence from original listenerWrapper" in {
      val listener = ObservableListener((_, _) => {})
      ListenerWrapper.chain(listener) should be ('empty)

      val observable1 = new EmptyObservable
      val observable2 = new EmptyObservable
      val wrapper = ListenerWrapper(listener, 1, observable1, observable2)
      ListenerWrapper.chain(wrapper) should be (Seq(observable1, observable2))
    }
  }
}
