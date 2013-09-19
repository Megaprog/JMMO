package jmmo.component.items

import jmmo.component.ComponentBase

/**
 * User: Tomas
 * Date: 19.09.13
 * Time: 13:11
 */
class RechargeableComponent extends ComponentBase[Rechargeable] with Rechargeable {

  def componentType: Class[Rechargeable] = classOf[Rechargeable]

  def getQuantityInCartridge: Int = 1

  def run() {}
}
