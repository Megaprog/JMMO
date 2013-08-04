/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.util

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import tags.Sub

/**
 * @param mapCount some instance of `mapCount` to tests
 * @author Tomas Shestakov
 */
@Sub
class MapCountSpec(mapCount: MapCount[String]) extends WordSpec with ShouldMatchers with MockitoSugar {

  def this() = this(MapCount.imm)

  "A MapCount" should {

    "provide appropriate `toString` method" in {
      info(mapCount.toString)
      mapCount.toString should be(mapCount.getClass.getSimpleName + "(" + mapCount.underlyingMap + ")")
    }

    "provide appropriate `equals` method" in {
      val mc1 = new MapCountImm(Map(("a", 2), ("b", 5)))
      val mc2 = mc1 + "a" + "b"
      mc2 should equal (new MapCountImm(Map(("a", 3), ("b", 6))))
    }

    "provide `underlyingMap` method to returns underlying Map" in {
      val mc = new MapCountImm(Map(("a", 1), ("b", 2)))
      mc.underlyingMap should equal (Map(("a", 1), ("b", 2)))
    }

    "provide `addKey` method to increment by one element count" in {
      val mockAction = mock[() => Unit]

      //mapCount must be empty for valid testing
      mapCount.underlyingMap.isEmpty should be (true)

      mapCount("A") should be (0)

      info("When adding to MapCount the first element it is called the action")
      val mc1 = mapCount.addKey("A", mockAction())
      mc1("A") should be (1)
      verify(mockAction).apply()

      val mc2 = mc1.addKey("A", mockAction())
      mc2("A") should be (2)
      verifyNoMoreInteractions(mockAction)

      val mc3 = mc2.addKey("B")
      mc3("A") should be (2)
      mc3("B") should be (1)
    }

    "provide `removeKey` method to decrement by one element count" in {
      val mockAction = mock[() => Unit]

      //if mapCount is immutable needed to add two "A" counts
      val mc1 = if (mapCount.isInstanceOf[MapCountImm[_]]) {
        mapCount.addKey("A").addKey("A")
      }
      else {
        mapCount
      }

      val mc2 = mc1.removeKey("A", mockAction())
      mc2("A") should be (1)
      verify(mockAction, never()).apply()

      val mc3 = mc2.removeKey("B")
      mc3("A") should be (1)
      mc3("B") should be (0)

      info("When removing from MapCount the last element it is called the action")
      val mc4 = mc3.removeKey("A", mockAction())
      mc4("A") should be (0)
      verify(mockAction).apply()

      val mc5 = mc4.removeKey("A", mockAction())
      mc5("A") should be (0)
      verifyNoMoreInteractions(mockAction)
    }

    "provide `removeAllKeys` method to remove all element counts" in {
      val mc1 = mapCount.addKey("A").addKey("A")

      mc1("A") should be (2)
      val mc2 = mc1.removeAllKeys("A")
      mc2("A") should be (0)
    }

    "provide `+=`, `-=`, or `+`, `-` methods with same result as addKey() and removeKey()" in {
      if (mapCount.isInstanceOf[MapCountImm[_]]) {
        info("fully immutable MapCountImm support `+` and `-` operators which returns new instance of MapCountImm with added or removed counts")
        //immutable MapCountImm must be empty here
        mapCount.underlyingMap.isEmpty should be (true)

        val imm1 = mapCount.asInstanceOf[MapCountImm[String]]
        val imm2 = imm1 + ("c")
        imm2("c") should be (1)
        imm2 eq imm1 should be (false)
        val imm3 = imm2 + "d" + "c"
        imm3.underlyingMap should be (Map("c" -> 2, "d" -> 1))
        imm3 eq imm2 should be (false)
        val imm4 = imm3 - "c" - "d"
        imm4.underlyingMap should be (Map("c" -> 1))
        imm4 eq imm3 should  be (false)
      }
      else {
        info("mutable MapCountMut support `+=` and `-=` operators which add or remove element count and returns the same instance")
        val mut1 = mapCount.removeAllKeys("A").removeAllKeys("B").asInstanceOf[MapCountMut[String]]
        mut1.underlyingMap.isEmpty should be (true)

        mut1 += "c"
        mut1("c") should be (1)
        mut1 eq mapCount should be (true)
        val mut2 = ((mut1 += "d") += "c")
        mut2.underlyingMap should be (Map("c" -> 2, "d" -> 1))
        mut2 eq mut1 should be (true)
        val mut3 = ((mut2 -= "c") -= "d")
        mut3.underlyingMap should be (Map("c" -> 1))
        mut3 eq mut2 should be (true)
      }
    }

  }
}

/**
 * Run tests for all MapCount derived classes
 */
class MapCountAllSpec extends WordSpec {
  override val nestedSuites = List(
    new MapCountSpec(MapCount.imm),
    new MapCountSpec(MapCount.mut)
  )
}