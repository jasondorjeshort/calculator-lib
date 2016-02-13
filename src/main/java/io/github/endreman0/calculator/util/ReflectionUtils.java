package io.github.endreman0.calculator.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.expression.type.BasedNumber;
import io.github.endreman0.calculator.expression.type.Decimal;
import io.github.endreman0.calculator.expression.type.MixedNumber;
import io.github.endreman0.calculator.expression.type.Switch;
import io.github.endreman0.calculator.expression.type.Time;
import io.github.endreman0.calculator.expression.type.Type;

public class ReflectionUtils{
	private static List<Method> staticFunctions = new ArrayList<Method>();
	public static void addStaticFunctions(Class<?> clazz){
		for(Method m : clazz.getMethods()){
			if(test(m, true, (Class<?>[])null) && isFunction(m, null))
				staticFunctions.add(m);
		}
	}
	static{
		addStaticFunctions(BasedNumber.class);
		addStaticFunctions(Decimal.class);
		addStaticFunctions(MixedNumber.class);
		addStaticFunctions(Switch.class);
		addStaticFunctions(Time.class);
	}
	public static Method operator(Type i1, Type i2, String symbol){
		for(Method m : i1.getClass().getMethods())
			if(test(m, false, i2.getClass()) && isOperator(m, symbol)) return m;
		return null;
	}
	public static Method instanceFunction(Type obj, String name, Type... args){
		for(Method m : obj.getClass().getMethods())
			if(test(m, false, Utility.getClasses(args)) && isFunction(m, name)) return m;
		return null;
	}
	public static Method staticFunction(String name, Type... args){
		for(Method m : staticFunctions)
			if(test(m, true, Utility.getClasses(args)))
				return m;
		return null;
	}
	private static boolean test(Method m, boolean isStatic, Class<?>... argTypes){
		if(Modifier.isStatic(m.getModifiers()) != isStatic) return false;
		if(argTypes != null && m.getParameterCount() != argTypes.length) return false;
		if(argTypes != null){
			Class<?>[] params = m.getParameterTypes();
			for(int i=0; i<params.length; i++)
				if(!params[i].isAssignableFrom(argTypes[i])) return false;
		}
		return true;
	}
	private static boolean isOperator(Method m, String symbol){
		for(Operator annotation : m.getAnnotationsByType(Operator.class))
			if(annotation.value().equalsIgnoreCase(symbol)) return true;
		return false;
	}
	private static boolean isFunction(Method m, String name){
		if(name == null && m.isAnnotationPresent(Function.class)) return true;
		for(Function annotation : m.getAnnotationsByType(Function.class))
			if(annotation.value().equalsIgnoreCase(name) ||//Pass function name into annotation,
					(annotation.value().isEmpty() && m.getName().equalsIgnoreCase(name)))//or read it from the method name
				return true;
		return false;
	}
}
