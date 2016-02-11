package io.github.endreman0.calculator.token.type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.endreman0.calculator.Calculator;
import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.error.CalculatorException;
import io.github.endreman0.calculator.util.Patterns;
import io.github.endreman0.calculator.util.Utility;

public class Set extends Type implements Iterable<MixedNumber>{
	private MixedNumber[] data;
	private Set(MixedNumber[] data){this.data = copy(data);}
	private Set(int size){this.data = new MixedNumber[size];}
	
	@Operator("+")
	public Set unite(Set other){
		Utility.checkNull(other, "Cannot unite with null");
		List<MixedNumber> list = new ArrayList<MixedNumber>();
		for(MixedNumber number : this) list.add(number);
		for(MixedNumber number : other)
			if(!list.contains(number)) list.add(number);//Add the contents of both lists while preventing duplicates
		return valueOf(list.toArray(new MixedNumber[0]));
	}
	@Operator("-")
	public Set intersect(Set other){
		Utility.checkNull(other, "Cannot intersect null");
		List<MixedNumber> list = new ArrayList<MixedNumber>();
		for(MixedNumber number : this)//Looping over contents limits it to what this set contains
			if(other.contains(number)) list.add(number);//The if limits it to what the other set contains as well, creating the intersection
		return Set.valueOf(list.toArray(new MixedNumber[0]));
	}
	
	public int size(){return data.length;}
	@Function("size") public MixedNumber fnSize(){return MixedNumber.valueOf(size());}
	public MixedNumber get(int index){return data[index];}
	@Function("get") public MixedNumber fnGet(MixedNumber index){return get(Utility.checkNull(index, "Cannot get null index").whole());}
	public boolean contains(MixedNumber number){
		for(MixedNumber n : this)
			if(n.equals(number)) return true;
		return false;
	}
	@Function("contains") public Switch fnContains(MixedNumber number){return Switch.valueOf(contains(number));}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Set)) return false;
		Set set = (Set)obj;
		if(size() != set.size()) return false;
		for(int i=0; i<size(); i++)
			if(!get(i).equals(set.get(i))) return false;
		return true;
	}
	@Override
	public int hashCode(){
		double sum = 0;
		for(MixedNumber cell : data)
			sum += cell.value();
		return (int)sum;
	}
	public String toParseableString(){return toDisplayString().replace(" ", "");}
	public String toDescriptorString(){return "Set[" + size() + "]";}
	public String toDisplayString(){
		StringBuilder sb = new StringBuilder("{");
		for(int i=0; i<size(); i++)
			sb.append(get(i)).append(i+1 < size() ? " | " : '}');//Append entry and close bracket for last entry, or comma otherwise
		return sb.toString();
	}
	public MixedNumber[] toArray(){return copy(data);}
	public Set clone(){return new Set(data);}
	@Override
	public Iterator<MixedNumber> iterator(){
		return new Iterator<MixedNumber>(){
			private int index;
			public boolean hasNext(){return index < Set.this.data.length;}
			public MixedNumber next(){return Set.this.get(index++);}
		};
	}
	
	public static Set valueOf(MixedNumber... data){return new Set(data);}
	@Factory({Patterns.SET})
	public static Set valueOf(String string) throws CalculatorException{
		String[] input = string.substring(1, string.length()-1).split("\\|");//Remove "{" and "}", then split on separator (pipe)
		MixedNumber[] data = new MixedNumber[input.length];
		for(int i=0; i<input.length; i++)
			data[i] = (MixedNumber)Calculator.calculate(input[i]);
		return new Set(data);
	}
	
	private static void arrayCheck(MixedNumber[] data){
		if(data == null) throw new IllegalArgumentException("data cannot equal null");
		if(data.length == 0) throw new IllegalArgumentException("Cannot create empty set");
		for(int i=0; i<data.length; i++)
			for(int j=i+1; j<data.length; j++)
				if(data[i].equals(data[j])) throw new IllegalArgumentException("Set cannot contain duplicate number (" + data[i] + ")");
	}
	private static MixedNumber[] copy(MixedNumber[] data){
		arrayCheck(data);
		MixedNumber[] ret = new MixedNumber[data.length];
		for(int i=0; i<data.length; i++) ret[i] = data[i] == null ? MixedNumber.valueOf(0) : data[i].clone();
		return ret;
	}
}
