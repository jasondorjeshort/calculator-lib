package io.github.endreman0.calculator.error;

public class EvaluatorException extends CalculatorException{
	private static final long serialVersionUID = 1L;
	protected EvaluatorException(String message){super(message);}
	protected EvaluatorException(Throwable cause){super(cause);}
	protected EvaluatorException(String message, Throwable cause){super(message, cause);}
}
