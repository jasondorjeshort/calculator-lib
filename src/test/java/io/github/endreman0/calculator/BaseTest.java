package io.github.endreman0.calculator;

import io.github.endreman0.calculator.expression.Expression;
import io.github.endreman0.calculator.expression.InstanceFunctionExpression;
import io.github.endreman0.calculator.expression.OperatorExpression;
import io.github.endreman0.calculator.expression.StaticFunctionExpression;
import io.github.endreman0.calculator.expression.Variable;
import io.github.endreman0.calculator.expression.type.Decimal;
import io.github.endreman0.calculator.expression.type.MixedNumber;
import io.github.endreman0.calculator.expression.type.Switch;

public class BaseTest{
	protected static OperatorExpression op(Expression i1, String symbol, Expression i2){
		return new OperatorExpression(i1, symbol, i2);
	}
	protected static InstanceFunctionExpression fn(Expression obj, String name, Expression... args){
		return new InstanceFunctionExpression(obj, name, args);
	}
	protected static StaticFunctionExpression fn(String name, Expression... args){
		return new StaticFunctionExpression(name, args);
	}
	protected static MixedNumber integer(int i){
		return MixedNumber.valueOf(i);
	}
	protected static Decimal decimal(double d){
		return Decimal.valueOf(d);
	}
	protected static Switch bool(boolean b){
		return Switch.valueOf(b);
	}
	protected static Variable var(String name){
		return Variable.get(name);
	}
}
