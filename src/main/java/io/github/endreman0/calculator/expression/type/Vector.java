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

public class Vector extends Type{
	private NumericType[] components;
	public static Vector valueOf(Expression... components){return new Vector(check(components));}
	
	@ComplexFactory("<")
	public static Vector valueOf(Queue<String> tokens){
		List<Expression> components = new ArrayList<>();
		while(!">".equals(tokens.peek())){
			components.add(readComponent(tokens).evaluate());
			if(",".equals(tokens.peek())) tokens.poll();//Read ","
		}
		tokens.poll();//Read ">"
		return valueOf(components.toArray(new Expression[components.size()]));
	}
	private Vector(NumericType... components){this.components = components;}
	private Vector(int size){this(new NumericType[size]);}
	
	@Operator("+")
	public Vector add(Vector other){
		checkSize(other);
		Vector ret = new Vector(size());
		for(int i=0; i<size(); i++) ret.components[i] = components[i].add(other.components[i]);
		return ret;
	}
	@Operator("-")
	public Vector subtract(Vector other){
		checkSize(other);
		Vector ret = new Vector(size());
		for(int i=0; i<size(); i++) ret.components[i] = components[i].subtract(other.components[i]);
		return ret;
	}
	@Operator("*")
	public Vector multiply(MixedNumber other){
		Utility.checkNull(other, "Cannot multiply by null");
		Vector ret = new Vector(size());
		for(int i=0; i<size(); i++) ret.components[i] = components[i].multiply(other);
		return ret;
	}
	@Operator("/")
	public Vector divide(MixedNumber other){
		Utility.checkNull(other, "Cannot divide by null");
		Vector ret = new Vector(size());
		for(int i=0; i<size(); i++) ret.components[i] = components[i].divide(other);
		return ret;
	}
	
	public int size(){return components.length;}
	@Function("size") public MixedNumber fnSize(){return MixedNumber.valueOf(size());}
	public NumericType get(int index){return components[index];}
	@Function public NumericType get(NumericType index){return get((int)Math.floor(index.value()));}
	@Function
	public Vector toQuadrant(MixedNumber quadrant){
		Utility.checkNull(quadrant, "Cannot convert to null quadrant");
		if(size() != 2) throw new IllegalArgumentException("Cannot change quadrants on " + size() + "D vector (must be 2D)");
		NumericType x = get(0).abs(), y = get(1).abs(), negative = MixedNumber.valueOf(-1);
		switch(quadrant.whole()){
			case(1): return valueOf(x, y);
			case(2): return valueOf(x.multiply(negative), y);
			case(3): return valueOf(x.multiply(negative), y.multiply(negative));
			case(4): return valueOf(x, y.multiply(negative));
			default: throw new IllegalArgumentException("Unknown quadrant " + quadrant);
		}
	}
	@Function
	public Decimal magnitude(){
		NumericType sum = MixedNumber.valueOf(0);
		for(NumericType number : components) sum = sum.add(number.multiply(number));//a^2 + b^2 + c^2 + ...
		return Decimal.valueOf(Math.sqrt(sum.value()));
	}
	@Function
	public Decimal direction(){
		if(size() != 2) throw new IllegalArgumentException("Cannot get direction of " + size() + "D vector (must be 2D)");
		return Decimal.valueOf(Math.toDegrees(Math.atan2(get(1).value(), get(0).value())));
	}
	@Function
	public MixedNumber quadrant(){
		if(size() != 2) throw new IllegalArgumentException("Cannot get quadrant of " + size() + "D vector (must be 2D)");
		double x = get(0).value(), y = get(1).value();
		if(x >= 0 && y >= 0) return MixedNumber.valueOf(1);
		else if(x < 0 && y >= 0) return MixedNumber.valueOf(2);
		else if(x < 0 && y < 0) return MixedNumber.valueOf(3);
		else return MixedNumber.valueOf(4);
	}
	@Function
	public Vector opposite(){
		Vector ret = new Vector(size());
		MixedNumber negative = MixedNumber.valueOf(-1);
		for(int i=0; i<size(); i++) ret.components[i] = get(i).multiply(negative);
		return ret;
	}
	private void checkSize(Vector other){
		Utility.checkNull(other, "Cannot compare to null vector");
		if(size() != other.size()) throw new IllegalArgumentException("Unequal size: " + size() + " vs " + other.size());
	}
	public NumericType[] toArray(){return check(components);}
	public Vector clone(){return valueOf(components);}
	
	@Override
	public String toParseableString(){
		StringBuilder sb = new StringBuilder("<");
		for(int i=0; i<components.length; i++){
			sb.append(components[i].toParseableString());
			if(i+1 < components.length) sb.append(", ");
		}
		return sb.append('>').toString();
	}
	@Override
	public String toDisplayString(){
		StringBuilder sb = new StringBuilder("<");
		for(int i=0; i<components.length; i++){
			sb.append(components[i].toDisplayString());
			if(i+1 < components.length) sb.append(", ");
		}
		return sb.append('>').toString();
	}
	@Override
	public String toDescriptorString(){
		StringBuilder sb = new StringBuilder("Vector[");
		for(int i=0; i<components.length; i++){
			sb.append(components[i].toDescriptorString());
			if(i+1 < components.length) sb.append(',');
		}
		return sb.append(']').toString();
	}
	@Override @Function
	public boolean equals(Object obj){
		return obj instanceof Vector ? Arrays.equals(components, ((Vector)obj).components) : false;
	}
	@Override
	public int hashCode(){
		double sum = 0;
		for(NumericType component : components) sum += component.value();
		return (int)sum;
	}
	
	private static NumericType[] check(Expression[] components){
		Utility.checkNull(components, "Cannot create vector of null components");
		NumericType[] ret = new NumericType[components.length];
		for(int i=0; i<components.length; i++){
			Type t = components[i].evaluate();
			if(t instanceof NumericType) ret[i] = (NumericType)t;
			else throw new IllegalArgumentException("Vectors may only contain numeric types");
		}
		return ret;
	}
	private static Expression readComponent(Queue<String> tokens){
		List<String> ts = new ArrayList<>();
		while(tokens.peek() != null && !(tokens.peek().equals(",") || tokens.peek().equals(">"))) ts.add(tokens.poll());
		return Processor.process(ts);
	}
}
