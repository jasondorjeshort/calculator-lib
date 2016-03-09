package io.github.endreman0.calculator.expression;

import java.util.HashMap;
import java.util.Map;

import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.expression.type.Type;
import io.github.endreman0.calculator.util.Patterns;
import io.github.endreman0.calculator.util.Utility;

public class Variable extends Type{
	private static Map<String, Variable> variables = new HashMap<String, Variable>();
	public static void initConstants(){
		init("pi", Math.PI, true);
		init("e", Math.E, true);
		init("g", 9.807, true);
		init("G", 6.67 * Math.pow(10, -11), true);
	}
	private static void init(String name, Object value, boolean constant){
		variables.put(name, new Variable(name, Utility.wrap(value), constant));
	}
	@Factory(Patterns.VARIABLE)
	public static Variable get(String name){
		return variables.computeIfAbsent(name, (key) -> new Variable(name, null));
	}
	private String name;
	private Type value;
	private boolean constant;
	private Variable(String name, Type value){this(name, value, false);}
	private Variable(String name, Type value, boolean constant){
		this.name = name; this.value = value; this.constant = constant;
	}
	public String name(){return name;}
	public Type get(){return value;}
	public boolean constant(){return constant;}
	
	@Operator("=")
	public Type set(Type value){
		if(constant) throw new UnsupportedOperationException("Cannot set constant variable " + name);
		else{
			return this.value = value;
		}
	}
	
	@Override protected Object eval() throws ReflectiveOperationException{return value != null ? value : this;}
	@Override public boolean isEvaluatable(){return value != null;}
	@Override public String toParseableString(){return name;}
	@Override public String toDisplayString(){return name;}
	@Override public String toDescriptorString(){return "Variable[" + name + "=" + (value == null ? "null" : value.toDescriptorString()) + "]";}
}
