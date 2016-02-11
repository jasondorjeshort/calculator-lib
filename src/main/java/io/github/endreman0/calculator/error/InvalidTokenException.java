package io.github.endreman0.calculator.error;

public class InvalidTokenException extends ParserException{
	private static final long serialVersionUID = 1L;
	public InvalidTokenException(String token){super('\"' + token + '\"');}
}
