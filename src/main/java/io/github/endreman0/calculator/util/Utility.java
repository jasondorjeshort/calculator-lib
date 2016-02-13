package io.github.endreman0.calculator.util;

import java.lang.reflect.Array;

import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.expression.type.Decimal;
import io.github.endreman0.calculator.expression.type.MixedNumber;
import io.github.endreman0.calculator.expression.type.Switch;
import io.github.endreman0.calculator.expression.type.Type;

public class Utility{
	public static <T> T checkNull(T t){return checkNull(t, "Argument cannot equal null");}
	public static <T> T checkNull(T t, String error){return checkNull(t, new IllegalArgumentException(error));}
	public static <T, E extends Exception> T checkNull(T t, E error) throws E{
		if(t == null) throw error;
		else return t;
	}
	@SuppressWarnings("unchecked")
	public static Class<? extends Type>[] getClasses(Type[] args){
		Class<? extends Type>[] classes = (Class<? extends Type>[])Array.newInstance(Class.class, args.length);
		for(int i=0; i<args.length; i++) classes[i] = args[i].getClass();
		return classes;
	}
	public static Type wrap(Object o){
		if(o instanceof Type) return (Type)o;
		else if(o instanceof Expression) return ((Expression)o).evaluate();
		else if(o instanceof Boolean) return Switch.valueOf(o.toString());
		else if(o instanceof Integer) return MixedNumber.valueOf(o.toString());
		else if(o instanceof Double || o instanceof Float) return Decimal.valueOf(o.toString());
		else return null;
	}
}
