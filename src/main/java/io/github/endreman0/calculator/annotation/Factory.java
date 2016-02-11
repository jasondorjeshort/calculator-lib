package io.github.endreman0.calculator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation representing a factory method.
 * The signature of the method this is attached to is generally as follows:
 * {@code public static T valueOf(String);}
 * Where {@code T} is the class declaring the method.
 * This method will be called when any of the specified patterns are matched, and use that input to parse the String.
 * @author endreman0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Factory{
	/**
	 * @return The patterns that this factory matches
	 */
	String[] value();
}
