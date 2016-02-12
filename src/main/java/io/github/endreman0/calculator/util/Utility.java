package io.github.endreman0.calculator.util;

public class Utility{
	public static <T> T checkNull(T t){return checkNull(t, "Argument cannot equal null");}
	public static <T> T checkNull(T t, String error){return checkNull(t, new IllegalArgumentException(error));}
	public static <T, E extends Exception> T checkNull(T t, E error) throws E{
		if(t == null) throw error;
		else return t;
	}
}
