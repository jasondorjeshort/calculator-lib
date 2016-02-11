package io.github.endreman0.calculator;

import io.github.endreman0.calculator.error.CalculatorException;
import io.github.endreman0.calculator.token.TokenList;
import io.github.endreman0.calculator.token.type.Decimal;
import io.github.endreman0.calculator.token.type.Type;

/**
 * The main interface with the Calculator library.
 * The {@link #calculate(String)} method is how you calculate the result of an expression, which is the primary purpose of the library.
 * @author endreman0
 */
public class Calculator{
	/**
	 * Initialize constants for the calculation engine.
	 * The only constant currently implemented is {@code pi}, which takes its value from {@link java.lang.Math#PI}.
	 */
	public static void initializeVariables(){
		Variable.declare("pi", Decimal.valueOf(Math.PI));
	}
	/**
	 * Parse and process the given input and return the type representing the result of the calculation.
	 * @param input The expression as a {@link java.lang.String String}
	 * @return The result of the calculation
	 * @throws CalculatorException If the input is not a valid expression, or the calculation throws an error
	 */
	public static Type calculate(String input) throws CalculatorException{
		TokenList tokens = Parser.parse(input);//Parse the input into a collection of tokens
		Type output = Evaluator.evaluate(tokens);//Process the tokens into one value
		return output;
	}
}
