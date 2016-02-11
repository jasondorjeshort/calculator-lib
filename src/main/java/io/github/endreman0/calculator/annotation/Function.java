package io.github.endreman0.calculator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation representing a function method, either static or instance.
 * If the annotated method is static, the function will be invoked using just the method name and its arguments.
 * Otherwise, the user will need to acquire an instance of the declaring class, then call the method on that instance.
 * @author endreman0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Function{
	/**
	 * @return The optional function name. If the name is not specified, the method name will be used.
	 */
	String value() default "";
}
