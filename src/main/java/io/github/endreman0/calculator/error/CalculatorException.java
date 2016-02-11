package io.github.endreman0.calculator.error;

public class CalculatorException extends Exception{
	private static final long serialVersionUID = 1L;
	protected CalculatorException(String message){super(message);}
	protected CalculatorException(Throwable cause){super(cause);}
	protected CalculatorException(String message, Throwable cause){super(message, cause);}
}
