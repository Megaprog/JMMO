/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import collection.immutable.TreeSet
import java.util.concurrent.locks.ReentrantLock
import jmmo.util.Cell

/**
 * Multi-threaded based on partial locks trait.
 * Write operations locks only cells which operated
 * No read operations are blocking.
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait MTPartialLocksGridData[A] extends STGridData[A] {

  def numLockedCells: Int

  def locksDim = new GridDimension {
    def cellSize = numLockedCells

    def width = MTPartialLocksGridData.this.width / numLockedCells

    def height = MTPartialLocksGridData.this.height / numLockedCells
  }

  val locks = Array.fill(locksDim.width)(Array.fill(locksDim.height)(new ReentrantLock))

  abstract override def addObject(obj: A) {
    //Cells stored to Set to avoid duplicates
    //Cells sorted to avoid Deadlocks
    val lockCells = objectCells(obj).withFilter(isCellInGrid).map(cell =>
      locksDim.pointToCell(cell.asPoint2d)) (jmmo.util.buildWith[Traversable[Cell], Cell, Set[Cell]](TreeSet.newBuilder[Cell]))
    /*(new CanBuildFrom[Traversable[Cell], Cell, Set[Cell]] {
      def apply(from: Traversable[Cell]) = apply()
      def apply() = new SetBuilder(TreeSet.empty[Cell])
    })*/

    lockCells.foreach {
      lockCell =>
        locks(lockCell.cx)(lockCell.cy).lock()
    }

    try {
      super.addObject(obj)
    }
    finally {
      lockCells.foreach {
        lockCell =>
          locks(lockCell.cx)(lockCell.cy).unlock()
      }
    }
  }

  abstract override def removeObject(obj: A) {
    val lockCells = objectCells(obj).withFilter(isCellInGrid).map(cell =>
      locksDim.pointToCell(cell.asPoint2d)) (jmmo.util.buildWith[Traversable[Cell], Cell, Set[Cell]](TreeSet.newBuilder[Cell]))
    /*(new CanBuildFrom[Traversable[Cell], Cell, Set[Cell]] {
      def apply(from: Traversable[Cell]) = apply()
      def apply() = new SetBuilder(TreeSet.empty[Cell])
    })*/

    lockCells.foreach {
      lockCell =>
        locks(lockCell.cx)(lockCell.cy).lock()
    }

    try {
      super.removeObject(obj)
    }
    finally {
      lockCells.foreach {
        lockCell =>
          locks(lockCell.cx)(lockCell.cy).unlock()
      }
    }
  }

  //  protected abstract override def addObjectToCell(obj: A, cell: Cell) {
  //    val lockCell = locksDim.pointToCell(Point2d(cell.cx, cell.cy))
  //
  //    atomic { implicit txn =>
  //      val locked = locks(lockCell.cx)(lockCell.cy)
  //      if (locked)
  //        retry
  //      locks(lockCell.cx)(lockCell.cy) = true
  //    }
  //
  //    super.addObjectToCell(obj, cell)
  //
  //    atomic { implicit txn =>
  //      locks(lockCell.cx)(lockCell.cy) = false
  //    }
  //  }
  //
  //  protected abstract override def removeObjectFromCell(obj: A, cell: Cell) {
  //    val lockCell = locksDim.pointToCell(Point2d(cell.cx, cell.cy))
  //
  //    atomic { implicit txn =>
  //      val locked = locks(lockCell.cx)(lockCell.cy)
  //      if (locked)
  //        retry
  //      locks(lockCell.cx)(lockCell.cy) = true
  //    }
  //
  //    super.removeObjectFromCell(obj, cell)
  //
  //    atomic { implicit txn =>
  //      locks(lockCell.cx)(lockCell.cy) = false
  //    }
  //  }

}
