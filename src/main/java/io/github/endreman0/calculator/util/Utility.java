package io.github.endreman0.calculator.util;

import java.lang.reflect.Method;

import io.github.endreman0.calculator.token.type.Type;

public class Utility{
	public static <T> T checkNull(T t){return checkNull(t, "Argument cannot equal null");}
	public static <T> T checkNull(T t, String error){return checkNull(t, new IllegalArgumentException(error));}
	public static <T, E extends Exception> T checkNull(T t, E error) throws E{
		if(t == null) throw error;
		else return t;
	}
	private static Object invoke(Method method, Object target, Object... args){
		try{
			return method.invoke(target, args);
		}catch(ReflectiveOperationException ex){
			return null;
		}
	}
	public static Type invokeStaticFunction(Method function, Type[] args){return (Type)invoke(function, null, (Object[])args);}
	public static Type invokeOperator(Method operator, Type i1, Type i2){return (Type)invoke(operator, i1, i2);}
	public static Type invokeInstanceFunction(Method function, Type obj, Type[] args){return (Type)invoke(function, obj, (Object[])args);}
	public static Type invokeFactory(Method factory, String input){return (Type)invoke(factory, null, input);}
}
