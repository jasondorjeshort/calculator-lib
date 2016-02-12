package io.github.endreman0.calculator.util;

import java.lang.reflect.Method;

import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.expression.type.Type;

public class ReflectionUtils{
	public static Method operator(Type i1, Type i2, String symbol){
		for(Method m : i1.getClass().getMethods()){
			if(m.isAnnotationPresent(Operator.class) && m.getAnnotation(Operator.class).value().equals(symbol)) return m;
		}
		return null;
	}
}
