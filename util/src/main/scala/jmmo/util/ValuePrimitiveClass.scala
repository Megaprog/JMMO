/*
 * Copyright (c) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.util

object ValuePrimitiveClass {

  val P_Byte    = classOf[Byte]
  val P_Short   = classOf[Short]
  val P_Char    = classOf[Char]
  val P_Int     = classOf[Int]
  val P_Long    = classOf[Long]
  val P_Float   = classOf[Float]
  val P_Double  = classOf[Double]
  val P_Boolean = classOf[Boolean]
  val P_Unit    = classOf[Unit]

  def isPrimitive(primitive: Class[_]) = primitive match {
    case P_Byte    => true
    case P_Short   => true
    case P_Char    => true
    case P_Int     => true
    case P_Long    => true
    case P_Float   => true
    case P_Double  => true
    case P_Boolean => true
    case P_Unit    => true
    case _         => false
  }

  def apply[T](wrapper: Class[T]): Class[T] = wrapper match {
    case ValueWrapperClass.W_Byte    => P_Byte.asInstanceOf[Class[T]]
    case ValueWrapperClass.W_Short   => P_Short.asInstanceOf[Class[T]]
    case ValueWrapperClass.W_Char    => P_Char.asInstanceOf[Class[T]]
    case ValueWrapperClass.W_Int     => P_Int.asInstanceOf[Class[T]]
    case ValueWrapperClass.W_Long    => P_Long.asInstanceOf[Class[T]]
    case ValueWrapperClass.W_Float   => P_Float.asInstanceOf[Class[T]]
    case ValueWrapperClass.W_Double  => P_Double.asInstanceOf[Class[T]]
    case ValueWrapperClass.W_Boolean => P_Boolean.asInstanceOf[Class[T]]
    case ValueWrapperClass.W_Unit    => P_Unit.asInstanceOf[Class[T]]
    case other                       => other
  }

  def unapply[T](primitive: Class[T]): Option[Class[T]] =
    if (isPrimitive(primitive)) Some(ValueWrapperClass(primitive))
    else None

}
