package io.github.endreman0.calculator.error;

public class ProcessorException extends CalculatorException{
	private static final long serialVersionUID = 1L;
	protected ProcessorException(String message){super(message);}
	protected ProcessorException(Throwable cause){super(cause);}
	protected ProcessorException(String message, Throwable cause){super(message, cause);}
}
