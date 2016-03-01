package io.github.endreman0.calculator;

import java.util.ArrayList;
import java.util.List;

import io.github.endreman0.calculator.util.StringIterator;

public class Parser{
	public static String[] parse(String input){return parse(new StringIterator(input)).toArray(new String[0]);}//Parsing works primarily with StringIterators, so immediately wrap the string in an iterator
	private static List<String> parse(StringIterator input){
		List<String> tokens = new ArrayList<String>();
		String[] targets = {".", "(", ")", ",", " ", "[", "]", "<", ">", "{", "}"};//Assemble an array of everything that signifies the end of a token: space,
		
		while(input.hasNext()){//For all tokens
			String s = input.readTo(targets);
			if(input.hasNext() && input.peek() == '.' && Character.isDigit(input.peek(2).charAt(1))){
				input.next();//Read '.'
				s = s + "." + input.readTo(targets);//Read past '.' if it's a decimal point in a decimal
			}
			tokens.add(s);//Read to the next end-of-token, parse it, and add it to the list 
			input.trim();//Remove spaces, because they aren't tokens
		}
		return tokens;
	}
}
