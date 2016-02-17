package io.github.endreman0.calculator.expression.type;

import io.github.endreman0.calculator.annotation.Caster;
import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.util.Patterns;
import io.github.endreman0.calculator.util.Utility;

public class MixedNumber extends Type{
	private int numerator, denominator;
	public static MixedNumber valueOf(int whole){return new MixedNumber(whole, 1);}
	public static MixedNumber valueOf(int numerator, int denominator){return new MixedNumber(numerator, denominator);}
	public static MixedNumber valueOf(int whole, int numerator, int denominator){return new MixedNumber(whole*denominator + (int)Math.copySign(numerator, whole), denominator);}
	public static MixedNumber valueOf(double value){
		int whole = (int)Math.floor(value);
		String num = String.valueOf(value);
		num = num.substring(num.indexOf('.')+1);
		int denominator = (int)Math.pow(10, num.length());
		return valueOf(whole, Integer.parseInt(num), denominator);
	}
	private MixedNumber(int numerator, int denominator){
		if(denominator < 0){//If the denominator is negative, flip signs
			numerator = -numerator; denominator = -denominator;
		}else if(denominator == 0) throw new IllegalArgumentException("Denominator cannot be 0");
		//Reduce
		int reductionFactor = reductionFactor(numerator, denominator);
		this.numerator = numerator / reductionFactor;
		this.denominator = denominator / reductionFactor;
	}
	private static int reductionFactor(int numerator, int denominator){
		if(denominator == 0) return Math.abs(numerator);
		else return reductionFactor(denominator, numerator % denominator);
	}
	
	@Operator("+")
	public MixedNumber add(MixedNumber other){
		Utility.checkNull(other, "Cannot add null");
		return valueOf(numerator*other.denominator + other.numerator*denominator, denominator*other.denominator);//Convert to LCD and add numerators
	}
	@Operator("-")
	public MixedNumber subtract(MixedNumber other){
		Utility.checkNull(other, "Cannot subtract null");
		return valueOf(numerator*other.denominator - other.numerator*denominator, denominator*other.denominator);//Convert to LCD and add numerators
	}
	@Operator("*")
	public MixedNumber multiply(MixedNumber other){
		Utility.checkNull(other, "Cannot multiply by null");
		return valueOf(numerator * other.numerator, denominator * other.denominator);
	}
	@Operator("/")
	public MixedNumber divide(MixedNumber other){
		Utility.checkNull(other, "Cannot divide by null");
		if(other.numerator == 0) throw new IllegalArgumentException("Cannot divide by 0");
		else return multiply(other.reciprocal());
	}
	@Operator("%")
	public MixedNumber modulus(MixedNumber other){
		Utility.checkNull(other, "Cannot modulate by null");
		int n1 = numerator * other.denominator, n2 = other.numerator * denominator,
				d = denominator * other.denominator;
		return valueOf(n1 % n2, d);//Convert to LCD and add numerators
	}
	
	@Function
	public MixedNumber reciprocal(){
		return valueOf(denominator, numerator);
	}
	@Function
	public static MixedNumber abs(MixedNumber number){
		return valueOf(Math.abs(Utility.checkNull(number, "Cannot take absolute value of null").numerator), number.denominator);
	}
	
	@Caster @Function
	public Decimal toDecimal(){
		return Decimal.valueOf(this.value());
	}
	
	@Function public int whole(){return (int)Math.copySign(Math.floorDiv(Math.abs(numerator), denominator), numerator);}//Sign-dependent floor(): rounds closer to zero not down
	@Function public int numerator(){return numerator % denominator;}
	@Function public int numeratorImproper(){return numerator;}
	@Function public int denominator(){return denominator;}
	@Function public double value(){return (double)numerator / denominator;}
	public MixedNumber clone(){return new MixedNumber(numerator, denominator);}
	
	@Factory({Patterns.INTEGER_COMMAS, Patterns.FRACTION, Patterns.MIXED_NUMBER})
	public static MixedNumber valueOf(String s){
		s = s.replaceAll("[\\s\\,]", "");
		if(s.matches("^\\-?\\d{1,}_\\d{1,}/\\d{1,}")){// "3_4/5" (typical mixed number notation)
			int ind1 = s.indexOf('_'), ind2 = s.indexOf('/');
			int whole = Integer.parseInt(s.substring(0, ind1)),//Before underscore
			numerator = Integer.parseInt(s.substring(ind1+1, ind2)),//Between underscore and slash
			denominator = Integer.parseInt(s.substring(ind2+1));//After slash
			return valueOf(whole, numerator, denominator);
		}else if(s.matches("\\-?\\d{1,}$")){//integers
			return valueOf(Integer.parseInt(s));
		}else if(s.matches("^\\-?\\d{1,}/\\d{1,}")){//fractions
			int ind = s.indexOf('/');
			return valueOf(Integer.parseInt(s.substring(0, ind)), Integer.parseInt(s.substring(ind+1)));
		}else throw new NumberFormatException("Input \"" + s + "\" doesn't match any number patterns");
	}
	public String toParseableString(){
		if(numerator == 0) return "0";
		else if(whole() != 0) return whole() + (numerator()==0 ? "" : "_" + Math.abs(numerator()) + "/" + denominator);
		else return numerator + "/" + denominator;
	}
	public String toDescriptorString(){
		return "MixedNumber[" + numerator + "," + denominator + "]";
	}
	public String toDisplayString(){
		return toParseableString();
	}
	@Override public int hashCode(){return numerator;}
	@Override @Function
	public boolean equals(Object obj){
		if(obj instanceof MixedNumber){
			MixedNumber other = (MixedNumber)obj;
			return this.numerator == other.numerator && this.denominator == other.denominator;
		}else return false;
	}
}
