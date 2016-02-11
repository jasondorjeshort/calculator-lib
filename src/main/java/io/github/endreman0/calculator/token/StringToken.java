package io.github.endreman0.calculator.token;

public class StringToken extends Token{
	private String s;
	protected StringToken(String s){super(TokenType.STRING); this.s = s;}
	public String text(){return s;}
	@Override public String toParseableString(){return s;}
	@Override public String toDescriptorString(){return "StringToken[" + s + "]";}
	@Override public String toDisplayString(){return toParseableString();}
}
