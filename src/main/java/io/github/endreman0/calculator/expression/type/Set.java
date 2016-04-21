package io.github.endreman0.calculator.expression.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import io.github.endreman0.calculator.Processor;
import io.github.endreman0.calculator.annotation.ComplexFactory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.util.Utility;

public class Set extends Type{
	private NumericType[] components;
	private Set(NumericType... components){
		Arrays.sort(this.components = components);
	}
	private Set(int size){
		this(new NumericType[size]);
	}
	
	@Operator("+")
	public Set unite(Set other){
		Utility.checkNull(other, "Cannot unite with null");
		List<NumericType> list = new ArrayList<>();
		for(NumericType number : components) list.add(number);
		for(NumericType number : other.components)
			if(!list.contains(number)) list.add(number);//Add the contents of both lists while preventing duplicates
		return valueOf(list.toArray(new MixedNumber[0]));
	}
	@Operator("-")
	public Set intersect(Set other){
		Utility.checkNull(other, "Cannot intersect null");
		List<NumericType> list = new ArrayList<>();
		for(NumericType number : components)//Looping over contents limits it to what this set contains
			if(other.contains(number)) list.add(number);//The if limits it to what the other set contains as well, creating the intersection
		return Set.valueOf(list.toArray(new NumericType[0]));
	}
	
	@Function
	public int size(){
		return components.length;
	}
	@Function
	public boolean contains(NumericType value){
		return indexOf(value) >= 0;
	}
	@Function
	public int indexOf(NumericType value){
		if(value == null)
			return -1;
		else
			return Arrays.binarySearch(components, value);
	}
	
	public Set clone(){
		return valueOf(components);
	}
	
	@Override
	public String toParseableString(){
		StringBuilder sb = new StringBuilder("{");
		for(int i = 0; i < components.length; i++){
			sb.append(components[i].toParseableString());
			if(i + 1 < components.length) sb.append(", ");
		}
		return sb.append('}').toString();
	}
	@Override
	public String toDisplayString(){
		StringBuilder sb = new StringBuilder("{");
		for(int i = 0; i < components.length; i++){
			sb.append(components[i].toDisplayString());
			if(i + 1 < components.length) sb.append(", ");
		}
		return sb.append('}').toString();
	}
	@Override
	public String toDescriptorString(){
		StringBuilder sb = new StringBuilder("Set[");
		for(int i = 0; i < components.length; i++){
			sb.append(components[i].toDescriptorString());
			if(i + 1 < components.length) sb.append(',');
		}
		return sb.append(']').toString();
	}
	@Override
	@Function
	public boolean equals(Object obj){
		return obj instanceof Set ? Arrays.equals(components, ((Set)obj).components) : false;
	}
	@Override
	public int hashCode(){
		double sum = 0;
		for(NumericType component : components)
			sum += component.value();
		return (int)sum;
	}
	
	public static Set valueOf(Expression... components){
		return new Set(check(components));
	}
	
	@ComplexFactory("{")
	public static Set valueOf(Queue<String> tokens){
		List<Expression> components = new ArrayList<>();
		while(!"}".equals(tokens.peek())){
			components.add(readComponent(tokens).evaluate());
			if(",".equals(tokens.peek())) tokens.poll();//Read ","
		}
		tokens.poll();//Read "}"
		return valueOf(components.toArray(new Expression[components.size()]));
	}
	
	private static NumericType[] check(Expression[] components){
		Utility.checkNull(components, "Cannot create Set of null components");
		NumericType[] ret = new NumericType[components.length];
		for(int i = 0; i < components.length; i++){
			Type t = components[i].evaluate();
			if(t instanceof NumericType)
				ret[i] = (NumericType)t;
			else
				throw new IllegalArgumentException("Sets may only contain numeric types");
		}
		return ret;
	}
	private static Expression readComponent(Queue<String> tokens){
		List<String> ts = new ArrayList<>();
		while(tokens.peek() != null && !(tokens.peek().equals(",") || tokens.peek().equals("}")))
			ts.add(tokens.poll());
		return Processor.process(ts);
	}
}
