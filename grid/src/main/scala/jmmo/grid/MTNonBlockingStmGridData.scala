/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import scala.concurrent.stm._

/**
 * Multi-threaded not blocking based on ScalaSTM library Grid data trait.
 * No read or write operations are blocking.
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait MTNonBlockingStmGridData[A] extends STGridData[A] {

  def numLockedCells: Int

  def locksDim = new GridDimension {
    def cellSize = numLockedCells
    def width = MTNonBlockingStmGridData.this.width / numLockedCells
    def height = MTNonBlockingStmGridData.this.height / numLockedCells
  }

  val locks = Array.fill(locksDim.width)(TArray(List.fill(locksDim.height)(false)))

  abstract override def addObject(obj: A) {
    val lockCells = for (cell <- objectCells(obj); if isCellInGrid(cell)) yield locksDim.pointToCell(cell.asPoint2d)

    atomic {
      implicit txn =>
        lockCells.foreach {
          lockCell =>
            if (locks(lockCell.cx)(lockCell.cy)) retry
            locks(lockCell.cx)(lockCell.cy) = true
        }
    }

    super.addObject(obj)

    atomic {
      implicit txn =>
        lockCells.foreach {
          lockCell =>
            locks(lockCell.cx)(lockCell.cy) = false
        }
    }
  }

  abstract override def removeObject(obj: A) {
    val lockCells = for (cell <- objectCells(obj); if isCellInGrid(cell)) yield locksDim.pointToCell(cell.asPoint2d)

    atomic {
      implicit txn =>
        lockCells.foreach {
          lockCell =>
            if (locks(lockCell.cx)(lockCell.cy)) retry
            locks(lockCell.cx)(lockCell.cy) = true
        }
    }

    super.removeObject(obj)

    atomic {
      implicit txn =>
        lockCells.foreach {
          lockCell =>
            locks(lockCell.cx)(lockCell.cy) = false
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
