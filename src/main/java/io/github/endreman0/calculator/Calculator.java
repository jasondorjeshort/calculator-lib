package io.github.endreman0.calculator;

import io.github.endreman0.calculator.error.CalculatorException;
import io.github.endreman0.calculator.error.ProgramEnd;
import io.github.endreman0.calculator.token.TokenList;
import io.github.endreman0.calculator.token.type.Decimal;
import io.github.endreman0.calculator.token.type.Type;

public class Calculator{
	public static void initializeVariables(){
		Variable.declare("pi", Decimal.valueOf(Math.PI));
	}
	public static Type calculate(String input) throws CalculatorException{
		if(input.equalsIgnoreCase("quit")) throw new ProgramEnd();//End on "quit"
		else{
			TokenList tokens = Parser.parse(input);//Parse the input into a collection of tokens
			Type output = Processor.process(tokens);//Process the tokens
			return output;
		}
	}
}
