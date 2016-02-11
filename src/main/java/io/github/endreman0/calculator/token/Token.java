package io.github.endreman0.calculator.token;

import java.lang.reflect.Method;

import io.github.endreman0.calculator.util.MethodHandler;
import io.github.endreman0.calculator.util.Operators;
import io.github.endreman0.calculator.util.Utility;

public abstract class Token{
	protected TokenType type;
	protected TokenList parent = null;
	protected Token(TokenType type){
		this.type = Utility.checkNull(type, "null token type");
	}
	public void setParent(TokenList parent){this.parent = parent;}
	public TokenList getParent(){return parent;}
	public TokenType type(){return type;}
	
	public abstract String toParseableString();
	public abstract String toDescriptorString();
	public abstract String toDisplayString();
	@Override public String toString(){return toDisplayString();}
	
	public static Token parse(String input){
		Utility.checkNull(input, "Cannot parse token from null");
		
		//First, search formatting tokens.
		for(TokenType type : TokenType.values())
			if(input.equals(type.text)) return new FormattingToken(type);
		
		//Next, try parsing a data type from it.
		Method factory = MethodHandler.factory(input);
		if(factory != null) return Utility.invokeFactory(factory, input);
		
		//Next, operators.
		Operators op = Operators.valueOf(input);
		if(op != null) return new OperatorToken(op);
		
		//Otherwise, just return it as a string token.
		return new StringToken(input);
	}
	
	public static enum TokenType{
		OPEN_PAREN("("), CLOSE_PAREN(")"), ARGUMENT_SEPARATOR(","), FUNCTION_SEPARATOR("."), OPERATOR(null), TYPE(null), STRING(null);
		public final String text;
		private TokenType(String defaultText){this.text = defaultText;}
	}
}
