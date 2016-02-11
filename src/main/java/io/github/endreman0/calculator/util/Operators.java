package io.github.endreman0.calculator.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Operators{
	public static final int MIN_PRECEDENCE = 0;
	private static int precCurrent = -1;//Incremented value
	private static List<Operators> vals = new ArrayList<Operators>();
	private static final ArrayList<ArrayList<Operators>> prec = new ArrayList<ArrayList<Operators>>();
	private static Operators op(String symbol, boolean commutative){
		Operators op = new Operators(symbol, precCurrent, commutative);
		vals.add(op);
		prec.get(precCurrent).add(op);
		return op;
	}
	private static int prec(){
		prec.add(new ArrayList<Operators>());//Add a new list for operators to be added to
		return ++precCurrent;
	}
	
	/**Precedence constant for variable assignment {@code var = value}.*/
	public static final int PREC_A = prec();
	public static final Operators ASSIGN = op("=", false);
	
	/**Precedence constant for boolean {@code xor} and {@code xnor}.*/
	public static final int PREC_XN = prec();
	public static final Operators XOR = op("X|", true), XNOR = op("!X|", true);
	
	/**Precedence constant for boolean {@code and} and {@code or}.*/
	public static final int PREC_AO = prec();
	public static final Operators AND = op("&&", true), OR = op("||", true);
	
	/**Precedence constant for addition and subtraction.*/
	public static final int PREC_AS = prec();
	public static final Operators ADDITION = op("+", true), SUBTRACTION = op("-", false);
	
	/**Precedence constant for multiplication, division, and modulus.*/
	public static final int PREC_MDM = prec();
	public static final Operators MULTIPLICATION = op("*", true), DIVISION = op("/", false), MODULUS = op("%", false);
	
	/**Precedence constant for exponentation.*/
	public static final int PREC_E = prec();
	public static final Operators EXPONENTATION = op("^", false);
	
	public static final int MAX_PRECEDENCE = precCurrent;
	public static final List<Operators> VALUES = Collections.unmodifiableList(vals);
	public static Operators valueOf(String symbol){
		for(Operators op : VALUES)
			if(op.symbol.equals(symbol)) return op;
		return null;
	}
	
	public final String symbol;
	public final int precedence;
	public final boolean commutative;
	private Operators(String symbol, int precedence, boolean commutative){this.symbol = symbol; this.precedence = precedence; this.commutative = commutative;}
	public boolean precedeces(Operators other){return precedence >= other.precedence;}
	public String toString(){return "Operator[" + symbol + "]";}
	@SuppressWarnings("unchecked")
	public static List<Operators> byPrecendence(int precedence){return (List<Operators>)prec.get(precedence).clone();}
	public static Operators bySymbol(String symbol){
		for(Operators op: VALUES){
			if(op.symbol.equals(symbol)) return op;
		}
		return null;
	}
}
