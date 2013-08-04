/*
 * Copyright (c) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.util

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import tags.Sub

/**
 * @param mapSet some instance of `MapSet` to tests
 * @author Tomas Shestakov
 */
@Sub
class MapSetSpec(mapSet: MapSet[String, Int]) extends WordSpec with ShouldMatchers {

  def this() = this(MapSet.immImm)

  "A MapSet" should {

    "provide appropriate `toString` method" in {
      info(mapSet.toString)
      mapSet.toString should be(mapSet.getClass.getSimpleName + "(" + mapSet.underlyingMap + ")")
    }

    "provide appropriate `equals` method" in {
      val ms1 = new MapSetImmImm(Map(("a", Set(1, 2)), ("b", Set(4, 5))))
      val ms2 = (ms1 + ("a" -> 3)) + ("b" -> 6)
      ms2 should equal (new MapSetImmImm(Map(("a", Set(1, 2, 3)), ("b", Set(4, 5, 6)))))
    }

    "provide `underlyingMap` method to returns underlying Map" in {
      val ms = new MapSetImmImm(Map(("a", Set(1, 2, 3)), ("b", Set(4, 5, 6))))
      ms.underlyingMap should equal (Map(("a", Set(1, 2, 3)), ("b", Set(4, 5, 6))))
    }

    "provide `addBinding` method to add one key value" in {
      //mapSet must be empty for valid testing
      mapSet.underlyingMap.isEmpty should be (true)
      
      mapSet("A") should be (Set())
      val ms1 = mapSet.addBinding("A", 1)
      ms1("A") should be (Set(1))
      val ms2 = ms1.addBinding("A", 2)
      ms2("A") should be (Set(1,2))
      val ms3 = ms2.addBinding("B", 3)
      ms3("A") should be (Set(1,2))
      ms3("B") should be (Set(3))
      val ms4 = ms3.addBinding("A", 1)
      ms4("A") should be (Set(1,2))
    }

    "provide `removeBinding` method to remove one key value" in {
      val ms1 = mapSet.addBinding("A", 1).addBinding("A", 2)
      val ms2 = ms1.removeBinding("A", 3)
      ms2("A") should be (Set(1,2))
      val ms3 = ms2.removeBinding("B", 1)
      ms3("A") should be (Set(1,2))
      val ms4 = ms3.removeBinding("A", 1)
      ms4("A") should be (Set(2))
      val ms5 = ms4.removeBinding("A", 2)
      ms5("A") should be (Set())
    }

    "provide `removeAllBindings` method to remove all key values" in {
      val ms1 = mapSet.addBinding("A", 1).addBinding("A", 2)
      ms1("A") should be (Set(1,2))
      val ms2 = ms1.removeAllBindings("A")
      ms2("A") should be (Set())
    }
    
    "provide `+=`, `-=`, or `+`, `-` methods with same result as addBinding() and removeBinding()" in {
      if (mapSet.isInstanceOf[MapSetImmImm[_,_]]) {
        info("fully immutable MapSetImmImm support `+` and `-` operators which returns new instance of MapSetImmImm with added or removed key values")
        //immutable MapSet must be empty here
        mapSet.underlyingMap.isEmpty should be (true)
        
        val imm1 = mapSet.asInstanceOf[MapSetImmImm[String, Int]]
        val imm2 = imm1 + ("c" -> 3)
        imm2("c") should be (Set(3))
        imm2 eq imm1 should be (false)
        val imm3 = imm2 + ("d" -> 4) + ("c" -> 5)
        imm3.underlyingMap should be (Map("c" -> Set(3, 5), "d" -> Set(4)))
        imm3 eq imm2 should be (false)
        val imm4 = imm3 - ("c" -> 3) - ("d" -> 4)
        imm4.underlyingMap should be (Map("c" -> Set(5)))
        imm4 eq imm3 should  be (false)
      }
      else {
        info("mutable MapSetMutMut MapSetMutImm MapSetImmMut support `+=` and `-=` operators which add or remove key values and returns same instance of MapSet")
        val mut1 = mapSet.removeAllBindings("A").removeAllBindings("B").asInstanceOf[MapSetMutable[String, Int]]
        mut1.underlyingMap.isEmpty should be (true)
        
        mut1 += ("c" -> 3)
        mut1("c") should be (Set(3))
        mut1 eq mapSet should be (true)
        val mut2 = ((mut1 += ("d" -> 4)) += ("c" -> 5))
        mut2.underlyingMap should be (Map("c" -> Set(3, 5), "d" -> Set(4)))
        mut2 eq mut1 should be (true)
        val mut3 = ((mut2 -= ("c" -> 3)) -= ("d" -> 4))
        mut3.underlyingMap should be (Map("c" -> Set(5)))
        mut3 eq mut2 should be (true)
      }
    }
  }
}

/**
 * Run tests for all MapSet derived classes
 */
class MapSetAllSpec extends WordSpec {
  override val nestedSuites = List(
    new MapSetSpec(MapSet.mutMut),
    new MapSetSpec(MapSet.mutImm),
    new MapSetSpec(MapSet.immMut),
    new MapSetSpec(MapSet.immImm)
  )
}