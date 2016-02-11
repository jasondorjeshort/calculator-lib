package io.github.endreman0.calculator;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.github.endreman0.calculator.error.MisplacedTokenException;
import io.github.endreman0.calculator.error.NoSuchInstanceFunctionException;
import io.github.endreman0.calculator.error.NoSuchOperatorException;
import io.github.endreman0.calculator.error.NoSuchStaticFunctionException;
import io.github.endreman0.calculator.error.ProcessorException;
import io.github.endreman0.calculator.token.FormattingToken;
import io.github.endreman0.calculator.token.OperatorToken;
import io.github.endreman0.calculator.token.StringToken;
import io.github.endreman0.calculator.token.Token;
import io.github.endreman0.calculator.token.Token.TokenType;
import io.github.endreman0.calculator.token.TokenList;
import io.github.endreman0.calculator.token.TokenList.Element;
import io.github.endreman0.calculator.token.TokenList.TokenListIterator;
import io.github.endreman0.calculator.token.type.Type;
import io.github.endreman0.calculator.util.MethodHandler;
import io.github.endreman0.calculator.util.Operators;
import io.github.endreman0.calculator.util.Utility;

public class Processor{
	public static Type process(TokenList tokens) throws ProcessorException{return process(tokens.iterator());}//Processing uses a TokenListIterator, so use the iterator from the input list
	public static Type process(TokenListIterator tokens) throws ProcessorException{
		TokenList operators = processFunctions(tokens);//Process all of the functions, so that the token list is just "<operand> <operator> <operand> <operator> <operand>" etc.
		Type output = processOperators(operators);//Process all of the operators down to one result
		return output;
	}
	private static TokenList processFunctions(TokenListIterator iterator) throws ProcessorException{
		TokenList operators = new TokenList();//New list to be returned
		while(iterator.hasNext() && !(iterator.peek().toParseableString().equals(")")) && !(iterator.peek().toParseableString().equals(","))){//Read until the end of the expression
			operators.add(processOperand(iterator));//Process one operand (this will iterate over everything until the next operator) 
			if(iterator.hasNext() && iterator.peek().type().equals(TokenType.OPERATOR))
				operators.add(iterator.next());//Add the operator
		}
		return operators;
	}
	private static Type processOperand(TokenListIterator iterator) throws ProcessorException{//Process one operand
		Token t = iterator.next();
		Type obj = null;//To-be returned object
		if(t instanceof StringToken){//Starting with a string means that the operand is either a static function call or a variable.
			if(iterator.hasNext() && iterator.peek().type().equals(TokenType.OPEN_PAREN)){//If the next token is an open parenthesis, it's a function.
				String fn = ((StringToken)t).text();//Get the function name
				expect(iterator.next(), "(");//Start of the function arguments
				List<Type> argList = new ArrayList<Type>();
				while(iterator.hasNext() && !(iterator.peek() instanceof FormattingToken)){//Read until the next close paren
					argList.add(process(iterator));//Process the next expression as an argument
					if(iterator.hasNext() && iterator.peek().type().equals(TokenType.ARGUMENT_SEPARATOR))//If there are more arguments
						iterator.next();//Iterate past argument separator and continue parsing
					else break;//If end of arguments, stop processing arguments
				}
				expect(iterator.next(), ")");
				
				Type[] args = argList.toArray(new Type[0]);//Argument objects
				Class<? extends Type>[] argClasses = getArgTypes(args);//Argument classes
				
				Method function = MethodHandler.staticFunction(fn, argClasses);//Get the static function matching this signature
				if(function == null) throw new NoSuchStaticFunctionException(fn, argClasses);
				else obj = Utility.invokeStaticFunction(function, args);
			}else{//If there is not a parenthesis, the operand must be a variable.
				Variable var = Variable.get(((StringToken)t).text());
				obj = var.get();
				if(obj == null) obj = var;
			}
		}else if(t instanceof Type){//Just an object literal
			obj = (Type)t;
		}else if(t.type().equals(TokenType.OPEN_PAREN)){//Parentheses
			obj = process(iterator);//Read until the paired close parenthesis
			expect(iterator.next(), ")");//Read close parenthesis
		}else throw new MisplacedTokenException(t.toParseableString(), "String, type, or parenthesis");//Unknown input
		
		//Process instance functions on type
		while(iterator.hasNext() && iterator.peek().type().equals(TokenType.FUNCTION_SEPARATOR)){//For each function
			expect(iterator.next(), ".");//Iterator over function separator
			String fn = expect(iterator.next(), TokenType.STRING, StringToken.class).text();//Read function name from string token
			expect(iterator.next(), "(");//Iterate over the parenthesis
			List<Type> argList = new ArrayList<Type>();
			while(iterator.hasNext() && !(iterator.peek() instanceof FormattingToken)){//For each argument
				argList.add(process(iterator));//Process the argument
				if(iterator.hasNext() && iterator.peek().type().equals(TokenType.ARGUMENT_SEPARATOR))//If there is another argument after the one just read
					iterator.next();//Iterate past argument separator and continue processing
				else break;//Otherwise (end of args), end loop
			}
			expect(iterator.next(), ")");//Iterate over close paren
			
			Type[] args = argList.toArray(new Type[0]);//Argument array
			Class<? extends Type>[] argClasses = getArgTypes(args);//Argument class array
			
			Method function = MethodHandler.instanceFunction(obj.getClass(), fn);//Get instance function on the given type with the given name
			if(function == null) throw new NoSuchInstanceFunctionException(obj.getClass(), fn, argClasses);
			else obj = Utility.invokeInstanceFunction(function, obj, args);
		}
		return obj;
	}
	//Read the token, and throw an error if it's not the expected token
	private static Token expect(Token t, String text) throws MisplacedTokenException{
		if(t == null) throw new MisplacedTokenException(null, text);
		else if(!t.toParseableString().equals(text)) throw new MisplacedTokenException(t.toParseableString(), text);
		else return t;
	}
	//Expect a token of the given type, and cast it to the type if it is correct
	private static <T extends Token> T expect(Token t, TokenType type, Class<T> clazz) throws MisplacedTokenException{
		if(t == null) throw new MisplacedTokenException(null, type.name());
		else if(!t.type().equals(type)) throw new MisplacedTokenException(t.toParseableString(), type.name());
		else return clazz.cast(t);
	}
	//Abstracted to a method because generics and arrays don't agree. Creating arrays of generic types isn't allowed.
	@SuppressWarnings("unchecked")
	private static Class<? extends Type>[] getArgTypes(Type[] args){
		Class<? extends Type>[] types = (Class<? extends Type>[])Array.newInstance(Class.class, args.length);
		for(int i=0; i<args.length; i++) types[i] = args[i].getClass();
		return types;
	}
	private static Type processOperators(TokenList tokens) throws ProcessorException{
		for(int precedence = Operators.MAX_PRECEDENCE; precedence>=Operators.MIN_PRECEDENCE; precedence--){//Start with highest precedence and work down
			for(Element e = tokens.firstElement().next(); e != null; e = e.next().next()){//Iterate over every other second element (operators)
				Operators op = expect(e.token(), TokenType.OPERATOR, OperatorToken.class).operator();//Get operator
				if(op.precedence >= precedence){//If the operator is in this precedence bracket
					Type i1 = (Type)e.previous().token(),//Read the two inputs
							i2 = (Type)e.next().token();
					Type result = operate(i1, i2, op);
					if(result != null){
						tokens.remove(e.previous());//Remove the two operators
						tokens.remove(e.next());
						e.replace(result);//Replace the operator token with the result
						if(tokens.size() == 1) break;//If the result is the last calculation, end the loop and return it
						//At this point, there is another operator in the calculation. The loop iterates over every other symbol expecting the current element to contain an operator, so the loop needs to move to the nearest operator. 
						else if(e.previous() != null && e.previous().previous() != null) e = e.previous();//If there's an operator before this, step back to it;
						else e = e.next();//Otherwise, there has to be one after this one (otherwise size() == 1), so step forward to it.
					}else throw new NoSuchOperatorException(i1.getClass(), i2.getClass(), op.symbol);
				}
			}
		}
		return (Type)tokens.first();//size() == 1 now (because that's when the break executes), so return the only element (the result)
	}
	private static Type operate(Type i1, Type i2, Operators op){
		Method operator = MethodHandler.operator(i1.getClass(), i2.getClass(), op.symbol);//Get the method that represents the operator
		if(operator != null) return Utility.invokeOperator(operator, i1, i2);
		else if(op.commutative){
			operator = MethodHandler.operator(i2.getClass(), i1.getClass(), op.symbol);
			if(operator != null) return Utility.invokeOperator(operator, i2, i1);
		}
		return null;
	}
}
