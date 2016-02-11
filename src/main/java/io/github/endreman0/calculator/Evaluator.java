package io.github.endreman0.calculator;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.github.endreman0.calculator.error.MisplacedTokenException;
import io.github.endreman0.calculator.error.NoSuchInstanceFunctionException;
import io.github.endreman0.calculator.error.NoSuchOperatorException;
import io.github.endreman0.calculator.error.NoSuchStaticFunctionException;
import io.github.endreman0.calculator.error.EvaluatorException;
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

/**
 * Processes expressions that has already been parsed and calculates the result. There should be no reason to use this class without the Parser, unless you recieve a set of pre-parsed tokens.
 * @author endreman0
 */
public class Evaluator{
	/**
	 * Evaluates the given expression.
	 * @param tokens a {@link io.github.endreman0.calculator.token.TokenList TokenList} representing the expression to parse
	 * @return the result of the evaluation 
	 * @throws EvaluatorException if an exception is thrown during the evaluation
	 */
	public static Type evaluate(TokenList tokens) throws EvaluatorException{return evaluate(tokens.iterator());}//Processing uses a TokenListIterator, so use the iterator from the input list
	/**
	 * Evaluates the given expression.
	 * @param tokens a {@link io.github.endreman0.calculator.token.TokenList.TokenListIterator TokenListIterator} representing the expression to parse
	 * @return the result of the evaluation 
	 * @throws EvaluatorException if an exception is thrown during the evaluation
	 */
	public static Type evaluate(TokenListIterator tokens) throws EvaluatorException{
		TokenList operators = evaluateFunctions(tokens);//Process all of the functions, so that the token list is just "<operand> <operator> <operand> <operator> <operand>" etc.
		Type output = evaluateOperators(operators);//Process all of the operators down to one result
		return output;
	}
	/**
	 * Processes the functions in an expression, so that only the operators remain.
	 * This is used internally as the first step of an evaluation: evaluating all of the terms in the expression. Afterward, {@linkplain #evaluateOperators(TokenList) the operators are evaluated}.
	 * @param iterator The {@linkplain io.github.endreman0.calculator.token.TokenList.TokenListIterator iterator} of the expression to process
	 * @return A new list of tokens containing only instances of {@link io.github.endreman0.calculator.token.type.Type Type} and operators
	 * @throws EvaluatorException if an exception is thrown during the evaluation
	 */
	private static TokenList evaluateFunctions(TokenListIterator iterator) throws EvaluatorException{
		TokenList operators = new TokenList();//New list to be returned
		while(iterator.hasNext() && !(iterator.peek().toParseableString().equals(")")) && !(iterator.peek().toParseableString().equals(","))){//Read until the end of the expression
			operators.add(evaluateOperand(iterator));//Process one operand (this will iterate over everything until the next operator) 
			if(iterator.hasNext() && iterator.peek().type().equals(TokenType.OPERATOR))
				operators.add(iterator.next());//Add the operator
		}
		return operators;
	}
	/**
	 * Evaluates a single operand in an expression.
	 * This is used internally in {@link #evaluateFunctions(TokenListIterator)} to evaluate a single term.
	 * @param iterator the iterator to evaluate from, pointing at the first token of the operand
	 * @return The operand, evaluated
	 * @throws EvaluatorException if an exception is thrown during the evaluation
	 */
	private static Type evaluateOperand(TokenListIterator iterator) throws EvaluatorException{//Process one operand
		Token t = iterator.next();
		Type obj = null;//To-be returned object
		if(t instanceof StringToken){//Starting with a string means that the operand is either a static function call or a variable.
			if(iterator.hasNext() && iterator.peek().type().equals(TokenType.OPEN_PAREN)){//If the next token is an open parenthesis, it's a function.
				String fn = ((StringToken)t).text();//Get the function name
				expect(iterator.next(), "(");//Start of the function arguments
				List<Type> argList = new ArrayList<Type>();
				while(iterator.hasNext() && !(iterator.peek() instanceof FormattingToken)){//Read until the next close paren
					argList.add(evaluate(iterator));//Process the next expression as an argument
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
			obj = evaluate(iterator);//Read until the paired close parenthesis
			expect(iterator.next(), ")");//Read close parenthesis
		}else throw new MisplacedTokenException(t.toParseableString(), "String, type, or parenthesis");//Unknown input
		
		//Process instance functions on type
		while(iterator.hasNext() && iterator.peek().type().equals(TokenType.FUNCTION_SEPARATOR)){//For each function
			expect(iterator.next(), ".");//Iterator over function separator
			String fn = expect(iterator.next(), TokenType.STRING, StringToken.class).text();//Read function name from string token
			expect(iterator.next(), "(");//Iterate over the parenthesis
			List<Type> argList = new ArrayList<Type>();
			while(iterator.hasNext() && !(iterator.peek() instanceof FormattingToken)){//For each argument
				argList.add(evaluate(iterator));//Process the argument
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
	/**
	 * Read the token, and throw an error if it's not the expected token.
	 * <br>
	 * This is used to ensure that tokens represent a valid expression.
	 * <br>
	 * This method expects that the passed token is not {@code null}, and that it contains the given text. If either expectations are not met, an exception is thrown.
	 * @param t the token to test
	 * @param text the text to expect the token to contain
	 * @return the input token, if all expectations are met
	 * @throws MisplacedTokenException if an expectation is not met
	 */
	private static Token expect(Token t, String text) throws MisplacedTokenException{
		if(t == null) throw new MisplacedTokenException(null, text);
		else if(!t.toParseableString().equals(text)) throw new MisplacedTokenException(t.toParseableString(), text);
		else return t;
	}
	/**
	 * Expect a token of the given type, and cast it to the type if it is correct.
	 * <br>
	 * For some parts of the evaluation, the next token read is expected to be of a certain type. This method ensures that is so.
	 * <br>
	 * This method expects that the passed token is not {@code null}, that it is of the given type, and that it is an instance of the passed class.
	 * If any of these expecations are not met, an exception is thrown.
	 * @param t the token to test
	 * @param type the type that the token is expected to be
	 * @param clazz the class that the token is expected to be an instance of
	 * @return the input token, if all expectations were met
	 * @throws MisplacedTokenException if an expectation is not met
	 */
	private static <T extends Token> T expect(Token t, TokenType type, Class<T> clazz) throws MisplacedTokenException{
		if(t == null) throw new MisplacedTokenException(null, type.name());
		else if(!t.type().equals(type)) throw new MisplacedTokenException(t.toParseableString(), type.name());
		else return clazz.cast(t);
	}
	/**
	 * Get the class of each member of an array and return them as an array.
	 * @param args The {@link io.github.endreman0.calculator.token.type.Type Type}s
	 * @return The classes
	 */
	@SuppressWarnings("unchecked")
	private static Class<? extends Type>[] getArgTypes(Type[] args){
		Class<? extends Type>[] types = (Class<? extends Type>[])Array.newInstance(Class.class, args.length);
		for(int i=0; i<args.length; i++) types[i] = args[i].getClass();
		return types;
	}
	/**
	 * Evaluate an expression of operators and terms.
	 * <br>
	 * This method requires that the input tokens consist only of one or more {@link io.github.endreman0.calculator.token.type.Type Type}s, with exactly one Operator between each pair.
	 * This is achieved by calling {@link #evaluateFunctions(TokenListIterator)} and using the result.
	 * @param tokens the expression to evaluate
	 * @return the result of the evaluation
	 * @throws EvaluatorException if an exception is thrown during the evaluation
	 */
	private static Type evaluateOperators(TokenList tokens) throws EvaluatorException{
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
	/**
	 * Invoke the given operator on the two inputs.
	 * <br>
	 * If the first element has an operator method that accepts the other type and matches the given symbol, that method is called and the result is returned.
	 * If a matching method is not found, the commutativity of the operator is checked. If the operator is commutative, the first step is performed again with the two inputs reversed.
	 * Finally, if the operator is not commutative or no matching commutative method is found, {@code null} is returned.
	 * @param i1 the first type to operate on
	 * @param i2 the second type to operate on
	 * @param op the operator to apply
	 * @return the result of the operation, or {@code null} if no matching operator was found
	 */
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
