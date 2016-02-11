package io.github.endreman0.calculator.token.type;

import java.util.Iterator;

import io.github.endreman0.calculator.Calculator;
import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.error.CalculatorException;
import io.github.endreman0.calculator.util.Patterns;
import io.github.endreman0.calculator.util.Utility;

public class Vector extends Type implements Iterable<MixedNumber>{
	private MixedNumber[] data;
	private Vector(MixedNumber[] data){this.data = copy(data);}
	private Vector(int size){this.data = new MixedNumber[size];}
	
	@Operator("+")
	public Vector add(Vector other){
		checkSize(other);
		Vector ret = new Vector(size());
		for(int i=0; i<size(); i++) ret.data[i] = data[i].add(other.data[i]);
		return ret;
	}
	@Operator("-")
	public Vector subtract(Vector other){
		checkSize(other);
		Vector ret = new Vector(size());
		for(int i=0; i<size(); i++) ret.data[i] = data[i].subtract(other.data[i]);
		return ret;
	}
	@Operator("*")
	public Vector multiply(MixedNumber other){
		Utility.checkNull(other, "Cannot multiply by null");
		Vector ret = new Vector(size());
		for(int i=0; i<size(); i++) ret.data[i] = data[i].multiply(other);
		return ret;
	}
	@Operator("/")
	public Vector divide(MixedNumber other){
		Utility.checkNull(other, "Cannot divide by null");
		Vector ret = new Vector(size());
		for(int i=0; i<size(); i++) ret.data[i] = data[i].divide(other);
		return ret;
	}
	
	public int size(){return data.length;}
	@Function("size") public MixedNumber fnSize(){return MixedNumber.valueOf(size());}
	public MixedNumber get(int index){return data[index];}
	@Function public MixedNumber get(MixedNumber index){return get(index.whole());}
	@Function
	public Vector toQuadrant(MixedNumber quadrant){
		Utility.checkNull(quadrant, "Cannot convert to null quadrant");
		if(size() != 2) throw new IllegalArgumentException("Cannot change quadrants on " + size() + "D vector (must be 2D)");
		MixedNumber x = MixedNumber.abs(get(0)), y = MixedNumber.abs(get(1)), negative = MixedNumber.valueOf(-1);
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
		MixedNumber sum = MixedNumber.valueOf(0);
		for(MixedNumber number : data) sum = sum.add(number.multiply(number));//a^2 + b^2 + c^2 + ...
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
		for(int i=0; i<size(); i++) ret.data[i] = get(i).multiply(negative);
		return ret;
	}
	
	private void checkSize(Vector other){
		Utility.checkNull(other, "Cannot compare to null vector");
		if(size() != other.size()) throw new IllegalArgumentException("Unequal size: " + size() + " vs " + other.size());
	}
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Vector)) return false;
		Vector vector = (Vector)obj;
		try{checkSize(vector);}catch(IllegalArgumentException ex){return false;}//Check dimensions
		for(int i=0; i<size(); i++)
			if(!get(i).equals(vector.get(i))) return false;
		return true;
	}
	@Override
	public int hashCode(){
		double sum = 0;
		for(MixedNumber cell : data) sum += cell.value();
		return (int)sum;
	}
	public String toParseableString(){return toDisplayString().replace(" ", "");}
	public String toDescriptorString(){return "Vector[" + size() + "]";}
	public String toDisplayString(){
		StringBuilder sb = new StringBuilder("<");
		for(int i=0; i<size(); i++)
			sb.append(get(i)).append(i+1 < size() ? " | " : ">");//Append entry and close bracket for last entry, or comma otherwise
		return sb.toString();
	}
	public MixedNumber[] toArray(){return copy(data);}
	public Vector clone(){return new Vector(data);}
	public Iterator<MixedNumber> iterator(){
		return new Iterator<MixedNumber>(){
			private int index;
			public boolean hasNext(){return index < Vector.this.size();}
			public MixedNumber next(){return Vector.this.get(index++);}
		};
	}
	
	public static Vector valueOf(MixedNumber... data){return new Vector(data);}
	@Factory({Patterns.VECTOR})
	public static Vector valueOf(String string) throws CalculatorException{
		String[] input = string.substring(1, string.length()-1).split("\\|");//Remove "<" and ">", then split on separator (pipe)
		MixedNumber[] data = new MixedNumber[input.length];
		for(int i=0; i<input.length; i++)
			data[i] = (MixedNumber)Calculator.calculate(input[i]);
		return new Vector(data);
	}
	
	private static void arrayCheck(MixedNumber[] data){
		if(data == null) throw new IllegalArgumentException("data cannot equal null");
		if(data.length == 0) throw new IllegalArgumentException("Cannot create empty vector");
	}
	private static MixedNumber[] copy(MixedNumber[] data){
		arrayCheck(data);
		MixedNumber[] ret = new MixedNumber[data.length];
		for(int i=0; i<data.length; i++) ret[i] = data[i] == null ? MixedNumber.valueOf(0) : data[i].clone();
		return ret;
	}
}
