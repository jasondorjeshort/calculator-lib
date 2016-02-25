package io.github.endreman0.calculator;

import java.util.ArrayList;
import java.util.List;

import io.github.endreman0.calculator.util.StringIterator;

public class Parser{
	public static String[] parse(String input){
		return null;
	}
	private static void parseTerm(StringIterator input, List<String> tokens){
		while(input.hasNext()){
			tokens.add(input.readTo(".", "("));
			if(input.hasNext() && input.peek() == '.'){//Instance function
				tokens.add(input.next() + "");//"."
				tokens.add(input.readTo("("));//Function name
				tokens.add(input.readTo(")"));
			}else ;//Variable
		}
	}
}
