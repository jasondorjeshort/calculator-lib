package io.github.endreman0.calculator;

import java.util.HashMap;
import java.util.Map;

import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.token.type.Type;

public class Variable extends Type{
	private static Map<String, Variable> variables = new HashMap<String, Variable>();
	public static Variable declare(String name, Type val){
		return variables.computeIfAbsent(name, (key) -> new Variable(key, val));
	}
	public static Variable get(String name){return declare(name, null);}//Initialize with empty value if it doesn't already exist
	private String name;
	private Type value;
	private Variable(String key, Type value){this.name = key; this.value = value;}
	public String key(){return name;}
	public Type get(){return value;}
	@Operator("=") public Type set(Type value){return this.value = value;}
	@Override public String toParseableString(){return name;}
	@Override public String toDescriptorString(){return "Variable[" + name + ", " + value + "]";}
	@Override public String toDisplayString(){return name;}
}
