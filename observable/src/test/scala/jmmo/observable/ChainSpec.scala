package jmmo.observable

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

/**
  * User: Tomas
  * Date: 06.09.13
  * Time: 22:00
  */
class ChainSpec extends WordSpec with ShouldMatchers with MockitoSugar {

  class Observable1 extends ObservableBase {}
  class Observable2 extends ObservableBase {}

  val observable1 = new Observable1
  val observable2 = new Observable2

  "A Chain" should {

     "provide exact1 method to execute code if specified chain contains only one element of specified class" in {
       var flag = false
       Chain.exact1[Observable1](Seq(observable1)) { (observable) =>
         flag = true
         observable should equal(observable1)
       }
       flag should be (true)

       flag = false
       Chain.exact1[Observable2](Seq(observable1)) { (observable) =>
         flag = true
       }
       flag should be (false)

       flag = false
       Chain.exact2[Observable1, Observable2](Seq(observable1)) { (obs1, obs2) =>
         flag = true
       }
       flag should be (false)
     }

    "provide first1 method to execute code if specified chain contains first element of specified class" in {
      var flag = false
      Chain.first1[Observable1](Seq(observable1, observable2)) { (observable) =>
        flag = true
        observable should equal(observable1)
      }
      flag should be (true)

      flag = false
      Chain.first1[Observable2](Seq(observable1, observable2)) { (observable) =>
        flag = true
      }
      flag should be (false)

      flag = false
      Chain.first1[Observable1](Seq(observable2, observable1)) { (observable) =>
        flag = true
      }
      flag should be (false)
    }

    "provide last1 method to execute code if specified chain contains last element of specified class" in {
      var flag = false
      Chain.last1[Observable2](Seq(observable1, observable2)) { (observable) =>
        flag = true
        observable should equal(observable2)
      }
      flag should be (true)

      flag = false
      Chain.last1[Observable1](Seq(observable1, observable2)) { (observable) =>
        flag = true
      }
      flag should be (false)

      flag = false
      Chain.last1[Observable2](Seq(observable2, observable1)) { (observable) =>
        flag = true
      }
      flag should be (false)
    }

  }
 }
