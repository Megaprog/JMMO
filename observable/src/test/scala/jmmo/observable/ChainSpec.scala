package jmmo.observable

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

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

     "has exact1 method to execute code if specified chain contains only one element of specified class" in {
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
     }
   }
 }
