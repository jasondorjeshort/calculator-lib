package io.github.endreman0.calculator.token;

public class FormattingToken extends Token{
	private String text;
	protected FormattingToken(TokenType type){this(type, type.text);}
	protected FormattingToken(TokenType type, String text){
		super(type);
		if(type.text == null) throw new IllegalArgumentException("Token type " + type + " is not a formatting token type");
		this.text = text;
	}
	@Override public String toParseableString(){return text;}
	@Override public String toDescriptorString(){return "FormattingToken[" + type + "]";}
	@Override public String toDisplayString(){return toParseableString();}
	@Override public boolean equals(Object obj){return obj instanceof FormattingToken && text.equals(((FormattingToken)obj).text);}
	@Override public int hashCode(){return text.hashCode();}
}
