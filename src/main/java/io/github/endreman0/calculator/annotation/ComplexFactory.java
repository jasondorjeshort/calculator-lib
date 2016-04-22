package io.github.endreman0.calculator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Factory for a complex type, such as a matrix or vector.
 * Methods with this annotation should fit the following pattern:
 * {@code public static T [methodName](Queue<String> tokens)}
 * Where {@code T} is the class declaring the method.
 * The passed queue contains all tokens making up the instance EXCEPT for the first token (detected with {@link #value()}), and may
 * contain some others afterward. The annotated method is required to remove all tokens included in the instance, and none more. 
 * @author endreman0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComplexFactory{
	/**
	 * The starting token for this complex type.
	 * If the given token is found, this method will be called.
	 * @return Starting token of type
	 */
	String value();
}
