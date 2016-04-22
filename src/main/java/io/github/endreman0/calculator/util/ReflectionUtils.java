package io.github.endreman0.calculator.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import io.github.endreman0.calculator.annotation.ComplexFactory;
import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.expression.Variable;
import io.github.endreman0.calculator.expression.type.BasedNumber;
import io.github.endreman0.calculator.expression.type.Decimal;
import io.github.endreman0.calculator.expression.type.Matrix;
import io.github.endreman0.calculator.expression.type.MixedNumber;
import io.github.endreman0.calculator.expression.type.Set;
import io.github.endreman0.calculator.expression.type.Switch;
import io.github.endreman0.calculator.expression.type.Time;
import io.github.endreman0.calculator.expression.type.Type;
import io.github.endreman0.calculator.expression.type.Vector;

public class ReflectionUtils{
	private static List<Method> factories = new ArrayList<>();
	private static Map<String, Method> complexFactories = new HashMap<>();
	private static List<Method> staticFunctions = new ArrayList<>();
	public static void addStatics(Class<? extends Expression> clazz){
		for(Method m : clazz.getMethods()){
			if(test(m, true, (Class<?>[])null) && isFunction(m, null))
				staticFunctions.add(m);
			else if(test(m, true, String.class) && m.isAnnotationPresent(Factory.class))
				factories.add(m);
			else if(test(m, true, Queue.class) && m.isAnnotationPresent(ComplexFactory.class)) complexFactories.put(m.getAnnotation(ComplexFactory.class).value(), m);//Starting token to method
		}
	}
	static{
		addStatics(BasedNumber.class);
		addStatics(Decimal.class);
		addStatics(Matrix.class);
		addStatics(MixedNumber.class);
		addStatics(Set.class);
		addStatics(Switch.class);
		addStatics(Time.class);
		addStatics(Variable.class);
		addStatics(Vector.class);
	}
	public static Method operator(Expression i1, Expression i2, String symbol){
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
			if(test(m, true, Utility.getClasses(args)) && isFunction(m, name)) return m;
		return null;
	}
	public static Method factory(String input){
		for(Method m : factories){
			for(String pattern : m.getAnnotation(Factory.class).value()){
				if(input.matches(pattern)) return m;
			}
		}
		return null;
	}
	public static Method complexFactory(String token){
		return complexFactories.get(token);
	}
	
	private static boolean test(Method m, boolean isStatic, Class<?>... argTypes){
		if(Modifier.isStatic(m.getModifiers()) != isStatic) return false;
		if(argTypes != null && m.getParameterCount() != argTypes.length) return false;
		if(argTypes != null){
			Class<?>[] params = m.getParameterTypes();
			for(int i = 0; i < params.length; i++)
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
			if(annotation.value().equalsIgnoreCase(name) || //Pass function name into annotation,
					(annotation.value().isEmpty() && m.getName().equalsIgnoreCase(name)))//or read it from the method name
				return true;
		return false;
	}
	
}
