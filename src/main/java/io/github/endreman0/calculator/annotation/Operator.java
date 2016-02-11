package io.github.endreman0.calculator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation representing an operator.
 * An example method signature for the addition of mixed numbers:
 * {@code public MixedNumber add(MixedNumber) throws CalculatorException;}
 * The core components are:
 * <ul>
 * <li>The method is not static.</li>
 * <li>The method has one argument, of the type of the other input for the operator.</li>
 * <li>The method must return the type of the operator's output.</li>
 * <li>The method may throw any exception type.</li>
 * </ul>
 * @author endreman0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operator{
	String value();
}
