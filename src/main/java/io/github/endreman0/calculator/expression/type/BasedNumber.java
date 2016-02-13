package io.github.endreman0.calculator.expression.type;

import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.util.Patterns;
import io.github.endreman0.calculator.util.Utility;

public class BasedNumber extends Type{
	private int base, value;
	private BasedNumber(int base, int value){this.base = base; this.value = value;}
	
	@Operator("+") public BasedNumber add(BasedNumber other){return valueOf(base, value + Utility.checkNull(other).value);}
	@Operator("-") public BasedNumber subtract(BasedNumber other){return valueOf(base, value - Utility.checkNull(other).value);}
	@Operator("*") public BasedNumber multiply(BasedNumber other){return valueOf(base, value * Utility.checkNull(other).value);}
	@Operator("/") public BasedNumber divide(BasedNumber other){return valueOf(base, value / Utility.checkNull(other).value);}
	@Operator("%") public BasedNumber modulus(BasedNumber other){return valueOf(base, value % Utility.checkNull(other).value);}
	
	public int base(){return base;}
	public int value(){return value;}
	
	@Function("base") public MixedNumber fnBase(){return MixedNumber.valueOf(base);}
	@Function("value") public MixedNumber fnValue(){return MixedNumber.valueOf(value);}
	@Function("equals") public Switch fnEquals(Switch other){return Switch.valueOf(equals(other));}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof BasedNumber){
			BasedNumber number = (BasedNumber)obj;
			return base == number.base && value == number.value;
		}else return false;
	}
	@Override public int hashCode(){return base + value;}
	@Override public String toParseableString(){return base + "x" + Integer.toString(value, base);}
	@Override public String toDescriptorString(){return "BasedNumber[" + base + "," + value + "]";}
	@Override public String toDisplayString(){return base + "x" + Integer.toString(value, base).toUpperCase();}
	
	public static BasedNumber valueOf(int base, int value){return new BasedNumber(base, value);}
	@Factory({Patterns.BASED_NUMBER})
	public static BasedNumber valueOf(String input){
		int index = input.indexOf('x');
		int base = Integer.parseInt(input.substring(0, index));
		int value = Integer.parseInt(input.substring(index+1).toLowerCase(), base);
		return valueOf(base, value);
	}
}
