package jmmo.observables

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

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

    "have apply method to create ListenerWrapper instance" in {
      val observable1 = new EmptyObservable
      val observable2 = new EmptyObservable

      val listener = ObservableListener((_: ObservableEvent, _: Seq[Observable]) => {}, 1)
      val wrapper = ListenerWrapper(listener, 0, observable1, observable2)

      wrapper.wrapped should be theSameInstanceAs(listener)
      wrapper.level should be (0)
      wrapper.chain should be (Seq(observable1, observable2))
    }

    "have unapply method for pattern matching" in {
      val observable1 = new EmptyObservable
      val observable2 = new EmptyObservable

      val listener = ObservableListener((_: ObservableEvent, _: Seq[Observable]) => {}, 1)
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

    "have implicit conversion from (ObservableEvent, Seq[Observable]) => Unit to ObservableListener" in {
//      val handler = (_: ObservableEvent, _: Seq[Observable]) => {}
//
//      import ObservableListener.ImplicitObservableListener
//
//      handler.handler should be theSameInstanceAs(handler)
//      handler.filter should be theSameInstanceAs(ObservableListener.PassAll)
//      handler.classes should be theSameInstanceAs(ObservableListener.AllClasses)
//      handler.level should equal(ObservableListener.MaxLevel)
    }
  }
}
