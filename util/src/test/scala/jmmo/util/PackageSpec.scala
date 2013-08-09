/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.util

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * User: Tomas
 * Date: 08.08.13
 * Time: 21:45
 */
class PackageSpec extends WordSpec with ShouldMatchers  {

  "A package object util" should {

    "have Last object which extract last element from sequence" in {
      (List(1, 2, 3) match {
        case _ Last elem => elem
      }) should be (3)

      ((1 to 5).toList match {
        case List(1, 2, 3, 4) Last 5 => true
      }) should be (true)

      ((1 to 5).toList match {
        case List(1, 2, 3) Last 4 Last 5 => true
      }) should be (true)
    }
  }
}