package io.github.endreman0.calculator.token;

import io.github.endreman0.calculator.util.Operators;

public class OperatorToken extends Token{
	private Operators op;
	public OperatorToken(Operators op){super(TokenType.OPERATOR); this.op = op;}
	public Operators operator(){return op;}
	@Override public String toParseableString(){return op.symbol;}
	@Override public String toDescriptorString(){return "OperatorToken[" + op.symbol + "]";}
	@Override public String toDisplayString(){return op.symbol;}
}
