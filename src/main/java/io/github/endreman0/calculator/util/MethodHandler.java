package io.github.endreman0.calculator.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.endreman0.calculator.Variable;
import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.token.type.BasedNumber;
import io.github.endreman0.calculator.token.type.Decimal;
import io.github.endreman0.calculator.token.type.Matrix;
import io.github.endreman0.calculator.token.type.MixedNumber;
import io.github.endreman0.calculator.token.type.Set;
import io.github.endreman0.calculator.token.type.Switch;
import io.github.endreman0.calculator.token.type.Time;
import io.github.endreman0.calculator.token.type.Type;
import io.github.endreman0.calculator.token.type.Vector;

public class MethodHandler{
	private static Map<Class<? extends Type>, Map<Class<? extends Type>, Map<String, Method>>> operators = new HashMap<Class<? extends Type>, Map<Class<? extends Type>, Map<String, Method>>>();
	private static Map<Class<? extends Type>, Map<String, Method>> instanceFunctions = new HashMap<Class<? extends Type>, Map<String, Method>>();
	private static Map<String, List<Method>> staticFunctions = new HashMap<String, List<Method>>();
	private static Map<Class<? extends Type>, Map<Class<? extends Type>, Method>> casters = new HashMap<Class<? extends Type>, Map<Class<? extends Type>, Method>>();
	private static Map<String, Method> factories = new HashMap<String, Method>();
	static{
		register(MixedNumber.class);
		register(Matrix.class);
		register(Vector.class);
		register(Set.class);
		register(Decimal.class);
		register(Switch.class);
		register(BasedNumber.class);
		register(Time.class);
		register(Variable.class);
	}
	private static void register(Class<? extends Type> clazz){
		for(Method method : clazz.getMethods()){
			if(method.getName().equals("cast")){
				registerCast(clazz, checkMethod(method), method);
			}
			if(method.getAnnotation(Operator.class) != null){
				Operator annotation = method.getAnnotation(Operator.class);
				registerOperator(clazz, checkMethod(method), annotation.value(), method);
			}
			if(method.getAnnotation(Function.class) != null){
				Function annotation = method.getAnnotation(Function.class);
				String name = (annotation.value().isEmpty() ? method.getName() : annotation.value());
				if(Modifier.isStatic(method.getModifiers())){
					registerStaticFunction(name, method);
				}else{
					registerInstanceFunction(clazz, name, method);
				}
			}
			if(method.getAnnotation(Factory.class) != null){
				registerFactory(method.getAnnotation(Factory.class).value(), method);
			}
		}
	}
	private static Class<? extends Type> checkMethod(Method method){
		if(method.getParameterCount() != 1) err("Cannot create caster with !=1 arguments", method);
		Class<?> src = method.getParameterTypes()[0];
		if(!Type.class.isAssignableFrom(src)) err("Cannot create cast from non-Type argument", method);
		return cast(src);
	}
	private static void err(String message, Method method){
		throw new ExceptionInInitializerError(message + " [class=" + method.getDeclaringClass() + ",method" + method.toGenericString() + "]");
	}
	@SuppressWarnings("unchecked") private static Class<? extends Type> cast(Class<?> clazz){return (Class<? extends Type>)clazz;}
	
	private static void registerOperator(Class<? extends Type> i1, Class<? extends Type> i2, String symbol, Method method){
		Map<Class<? extends Type>, Map<String, Method>> map1 = operators.computeIfAbsent(i1, (Class<? extends Type> key) -> new HashMap<Class<? extends Type>, Map<String, Method>>());
		Map<String, Method> map2 = map1.computeIfAbsent(i2, (Class<? extends Type> key) -> new HashMap<String, Method>());
		map2.put(symbol, method);
	}
	private static void registerCast(Class<? extends Type> src, Class<? extends Type> target, Method method){
		Map<Class<? extends Type>, Method> map = casters.computeIfAbsent(target, (Class<? extends Type> key) -> new HashMap<Class<? extends Type>, Method>());
		map.put(src, method);
	}
	private static void registerInstanceFunction(Class<? extends Type> clazz, String name, Method method){
		Map<String, Method> map = instanceFunctions.computeIfAbsent(clazz, (Class<? extends Type> key) -> new HashMap<String, Method>());
		map.put(name, method);
	}
	private static void registerStaticFunction(String name, Method method){
		List<Method> list = staticFunctions.computeIfAbsent(name, (String key) -> new ArrayList<Method>());
		list.add(method);
	}
	private static void registerFactory(String[] patterns, Method method){
		for(String pattern : patterns) factories.put(pattern, method);
	}
	
	public static Method operator(Class<? extends Type> i1, Class<? extends Type> i2, String symbol){
		for(Map.Entry<Class<? extends Type>, Map<Class<? extends Type>, Map<String, Method>>> entry : operators.entrySet())
			if(entry.getKey().isAssignableFrom(i1))//First type matches
				for(Map.Entry<Class<? extends Type>, Map<String, Method>> entry2 : entry.getValue().entrySet())
					if(entry2.getKey().isAssignableFrom(i2))//Second type matches
						return entry2.getValue().get(symbol);
		return null;//No operator found
	}
	public static Method instanceFunction(Class<? extends Type> i, String name){
		try{
			return instanceFunctions.get(i).get(name);
		}catch(NullPointerException ex){return null;}
	}
	public static Method staticFunction(String name, Class<? extends Type>[] args){
		for(Method m : staticFunctions.get(name)){
			if(Arrays.equals(m.getParameterTypes(), args)) return m;
		}
		throw new IllegalArgumentException("Unknown static function \"" + name + "\" for argument types " + Arrays.toString(args));
	}
	public static Method caster(Class<? extends Type> src, Class<? extends Type> target){
		try{
			return casters.get(target).get(src);
		}catch(NullPointerException ex){return null;}
	}
	public static Method factory(String input){
		for(Map.Entry<String, Method> entry : factories.entrySet())
			if(input.matches(entry.getKey())) return entry.getValue();
		return null;
	}
}
