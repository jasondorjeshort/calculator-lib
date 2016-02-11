package io.github.endreman0.calculator;

import io.github.endreman0.calculator.error.ParserException;
import io.github.endreman0.calculator.token.Token;
import io.github.endreman0.calculator.token.TokenList;
import io.github.endreman0.calculator.util.StringIterator;

public class Parser{
	public static TokenList parse(String input) throws ParserException{return parse(new StringIterator(input));}//Parsing works primarily with StringIterators, so immediately wrap the string in an iterator
	private static TokenList parse(StringIterator input) throws ParserException{
		TokenList tokens = new TokenList();
		String[] targets = {".", "(", ")", ",", " "};//Assemble an array of everything that signifies the end of a token: space,
		
		while(input.hasNext()){//For all tokens
			String s = input.readTo(targets);
			if(input.hasNext() && input.peek() == '.' && Character.isDigit(input.peek(2).charAt(1))){
				input.next();//Read '.'
				s = s + "." + input.readTo(targets);//Read past '.' if it's a decimal point in a decimal
			}
			tokens.add(Token.parse(s));//Read to the next end-of-token, parse it, and add it to the list 
			input.trim();//Remove spaces, because they aren't tokens
		}
		return tokens;
	}
}
