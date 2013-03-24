/*
 * Copyright (c) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.util

object ValueWrapperClass {

  val W_Byte    = classOf[java.lang.Byte]
  val W_Short   = classOf[java.lang.Short]
  val W_Char    = classOf[java.lang.Character]
  val W_Int     = classOf[java.lang.Integer]
  val W_Long    = classOf[java.lang.Long]
  val W_Float   = classOf[java.lang.Float]
  val W_Double  = classOf[java.lang.Double]
  val W_Boolean = classOf[java.lang.Boolean]
  val W_Unit    = classOf[java.lang.Void]

  def isWrapper(wrapper: Class[_]) = wrapper match {
    case W_Byte    => true
    case W_Short   => true
    case W_Char    => true
    case W_Int     => true
    case W_Long    => true
    case W_Float   => true
    case W_Double  => true
    case W_Boolean => true
    case W_Unit    => true
    case _         => false
  }

  def apply[T](primitive: Class[T]): Class[T] = primitive match {
    case ValuePrimitiveClass.P_Byte    => W_Byte.asInstanceOf[Class[T]]
    case ValuePrimitiveClass.P_Short   => W_Short.asInstanceOf[Class[T]]
    case ValuePrimitiveClass.P_Char    => W_Char.asInstanceOf[Class[T]]
    case ValuePrimitiveClass.P_Int     => W_Int.asInstanceOf[Class[T]]
    case ValuePrimitiveClass.P_Long    => W_Long.asInstanceOf[Class[T]]
    case ValuePrimitiveClass.P_Float   => W_Float.asInstanceOf[Class[T]]
    case ValuePrimitiveClass.P_Double  => W_Double.asInstanceOf[Class[T]]
    case ValuePrimitiveClass.P_Boolean => W_Boolean.asInstanceOf[Class[T]]
    case ValuePrimitiveClass.P_Unit    => W_Unit.asInstanceOf[Class[T]]
    case other                         => other
  }

  def unapply[T](wrapper: Class[T]): Option[Class[T]] =
    if (isWrapper(wrapper)) Some(ValuePrimitiveClass(wrapper))
    else None

}
