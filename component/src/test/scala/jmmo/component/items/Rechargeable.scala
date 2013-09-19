package jmmo.component.items

/**
 * User: Tomas
 * Date: 19.09.13
 * Time: 13:09
 */
trait Rechargeable extends Runnable {

  def getQuantityInCartridge: Int
}
