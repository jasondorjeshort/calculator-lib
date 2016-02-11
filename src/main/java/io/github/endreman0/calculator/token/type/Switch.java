package io.github.endreman0.calculator.token.type;

import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.util.Patterns;
import io.github.endreman0.calculator.util.Utility;

public class Switch extends Type{
	private boolean value;
	private Switch(boolean value){this.value = value;}
	
	@Operator("&&") @Function public Switch and(Switch other){return valueOf(value && Utility.checkNull(other).value);}
	@Operator("||") @Function public Switch or(Switch other){return valueOf(value || Utility.checkNull(other).value);}
	@Operator("X|") @Function public Switch xor(Switch other){return valueOf(value != Utility.checkNull(other).value);}
	@Operator("!X|") @Function public Switch xnor(Switch other){return valueOf(value == Utility.checkNull(other).value);}
	
	@Function public static Switch not(Switch input){return valueOf(!Utility.checkNull(input).value);}
	
	public boolean value(){return value;}
	public Switch clone(){return valueOf(value);}
	@Override public boolean equals(Object obj){return obj instanceof Switch && value == ((Switch)obj).value;}
	@Override public int hashCode(){return value ? 1 : 0;}
	@Override public String toParseableString(){return value ? "true" : "false";}
	@Override public String toDescriptorString(){return "Switch[" + value + "]";}
	@Override public String toDisplayString() {return toParseableString();}
	
	public static Switch valueOf(boolean value){return new Switch(value);}
	@Factory({Patterns.SWITCH})
	public static Switch valueOf(String value){
		if("true".equalsIgnoreCase(value)) return valueOf(true);
		else if("false".equalsIgnoreCase(value)) return valueOf(false);
		else throw new IllegalArgumentException("Input \"" + value + "\"does not match any input types");
	}
}
