package io.github.endreman0.calculator.error;

public class MisplacedTokenException extends ProcessorException{
	private static final long serialVersionUID = 1L;
	public MisplacedTokenException(String token, String expected){super("Found token \"" + token + "\", expected \"" + expected + "\"");}
}
