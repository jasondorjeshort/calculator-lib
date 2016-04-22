package io.github.endreman0.calculator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation representing a caster.
 * The signature of the method this is attached to is generally as follows:
 * <p>{@code public static T toT(U);}</p>
 * Where {@code T} is the class the method casts to, and {@code U} is the declaring class.
 * In the event that a function is called or an operator is used with invalid argument types (e.g. {@code 3_1/2 + 2.3}), this method will be called to attempt to convert the arguments into types that match a pattern of the operator.
 * @author endreman0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Caster{}
