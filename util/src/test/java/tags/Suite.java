/*
 * Copyright (C) 2013 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */
package tags;

import org.scalatest.TagAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: Tomas
 * Date: 04.08.13
 * Time: 8:57
 */
@TagAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Suite {
}
