package io.github.endreman0.calculator.error;

public class ParserException extends CalculatorException{
	private static final long serialVersionUID = 1L;
	protected ParserException(String message){super(message);}
	protected ParserException(Throwable cause){super(cause);}
	protected ParserException(String message, Throwable cause){super(message, cause);}
}
