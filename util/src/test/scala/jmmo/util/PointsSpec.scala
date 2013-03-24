/*
 * Copyright (c) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.util

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import scala.math._

/**
 * @author Tomas Shestakov
 */
class PointsSpec extends WordSpec with ShouldMatchers {

  "A Point2d" should {

    val x1 = 1
    val y1 = 2
    val x2 = 5
    val y2 = 5
    val p1 = Point2d(x1, y1)
    val p2 = Point2d(x1, y1)
    val p3 = Point2d(x2, y2)

    "calculate distance between points" in {
      val d = sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
      p1.distance(x2, y2) should be(d plusOrMinus 1e-7)
      p1.distance(p3) should be(d plusOrMinus 1e-7)
    }

    "check if distance between this and other point is less or equal specified length" in {
      val d = sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
      p1.distanceLessOrEqual(x2, y2, scala.math.round(d).toInt) should be(true)
      p1.distanceLessOrEqual(p3, scala.math.round(d).toInt) should be(true)
    }

    "be equal other Point2d with equal coordinates" in {
      p1 should equal(p2)
      p1 should not equal (p3)
      p2 should equal(p1)
      p2 should not equal (p3)
      p3 should not equal (p1)
      p3 should not equal (p2)
    }

    "has hashCode fit the general contract of hashCode" in {
      val h1 = p1.hashCode()
      val h2 = p1.hashCode()
      val h3 = p1.hashCode()
      h1 should (equal(h2) and equal(h3))
      p1.hashCode() should equal(p2.hashCode())
      p1.hashCode() should not equal (p3.hashCode())
    }

    "return appropriate toString value" in {
      Point2d(3, 7).toString should be("Point2d(3,7)")
      val x = 536
      val y = 845
      Point2d(x, y).toString should be("Point2d(%d,%d)".format(x, y))
    }

    "calculate new Point2d with coordinates equal sum of coordinates this Point2d and other Point2d" in {
      p1.plus(x2, y2) should be(Point2d(x1 + x2, y1 + y2))
      p1.plus(p3) should be(Point2d(x1 + x2, y1 + y2))
    }

    "be Ordered: if (p1.x > p2.x) then p1 > p2; if (p1.x == p2.x && p1.y > p2.y) then p1 > p2" in {
      (p1 < p3) should be(true)
      (p3 > p1) should be(true)
      (Point2d(2, 0) > Point2d(1, 5)) should be(true)
      (Point2d(2, 3) > Point2d(2, 2)) should be(true)
    }
  }

  "A Cell" should {

    val x1 = 1
    val y1 = 2
    val x2 = 5
    val y2 = 5
    val c1 = Cell(x1, y1)
    val c2 = Cell(x1, y1)
    val c3 = Cell(x2, y2)

    "calculate distance between points" in {
      val d = sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
      c1.distance(x2, y2) should be(d plusOrMinus 1e-7)
      c1.distance(c3) should be(d plusOrMinus 1e-7)
    }

    "check if distance between this and other cell is less or equal specified length" in {
      val d = sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
      c1.distanceLessOrEqual(x2, y2, scala.math.round(d).toInt) should be(true)
      c1.distanceLessOrEqual(c3, scala.math.round(d).toInt) should be(true)
    }

    "be equal other Point2d with equal coordinates" in {
      c1 should equal(c2)
      c1 should not equal (c3)
      c2 should equal(c1)
      c2 should not equal (c3)
      c3 should not equal (c1)
      c3 should not equal (c2)
    }

    "has hashCode fit the general contract of hashCode" in {
      val h1 = c1.hashCode()
      val h2 = c1.hashCode()
      val h3 = c1.hashCode()
      h1 should (equal(h2) and equal(h3))
      c1.hashCode() should equal(c2.hashCode())
      c1.hashCode() should not equal (c3.hashCode())
    }

    "return appropriate toString value" in {
      Cell(3, 7).toString should be("Cell(3,7)")
      val x = 536
      val y = 845
      Cell(x, y).toString should be("Cell(%d,%d)".format(x, y))
    }

    "calculate new Cell with coordinates equal sum of coordinates this Cell and other Cell" in {
      c1.plus(x2, y2) should be(Cell(x1 + x2, y1 + y2))
      c1.plus(c3) should be(Cell(x1 + x2, y1 + y2))
    }

    "has converted method to the Point2d with same coordinates as the Cell" in {
      val cx = 9
      val cy = 5
      Cell(cx, cy).asPoint2d should be(Point2d(cx, cy))
    }

    "be Ordered: if (p1.x > p2.x) then p1 > p2; if (p1.x == p2.x && p1.y > p2.y) then p1 > p2" in {
      (c1 < c3) should be(true)
      (c3 > c1) should be(true)
      (Cell(2, 0) > Cell(1, 5)) should be(true)
      (Cell(2, 3) > Cell(2, 2)) should be(true)
    }

  }
}
