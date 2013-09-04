package jmmo.property

/**
 * User: Tomas
 * Date: 04.09.13
 * Time: 13:23
 */
class PropertyAlways[A](name: String = "", value: A = jmmo.util.sysDefault[A]) extends PropertyImpl(name, value) with UpdateAlways [A]
