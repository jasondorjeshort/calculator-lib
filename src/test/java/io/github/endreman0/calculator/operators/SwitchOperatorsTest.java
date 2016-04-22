package io.github.endreman0.calculator.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.endreman0.calculator.expression.type.Switch;
import static io.github.endreman0.calculator.expression.type.Switch.valueOf;

public class SwitchOperatorsTest{
	@Test
	public void testAnd(){
		and(valueOf("true"), valueOf("false"), valueOf("false"));
		and(valueOf("false"), valueOf("false"), valueOf("false"));
		and(valueOf("true"), valueOf("true"), valueOf("true"));
	}
	private void and(Switch i1, Switch i2, Switch result){
		assertEquals(result, i1.and(i2));
	}
	@Test
	public void testOr(){
		or(valueOf("true"), valueOf("false"), valueOf("true"));
		or(valueOf("false"), valueOf("false"), valueOf("false"));
		or(valueOf("true"), valueOf("true"), valueOf("true"));
	}
	private void or(Switch i1, Switch i2, Switch result){
		assertEquals(result, i1.or(i2));
	}
	@Test
	public void testXor(){
		xor(valueOf("true"), valueOf("false"), valueOf("true"));
		xor(valueOf("false"), valueOf("false"), valueOf("false"));
		xor(valueOf("true"), valueOf("true"), valueOf("false"));
	}
	private void xor(Switch i1, Switch i2, Switch result){
		assertEquals(result, i1.xor(i2));
	}
	@Test
	public void testXnor(){
		xnor(valueOf("true"), valueOf("false"), valueOf("false"));
		xnor(valueOf("false"), valueOf("false"), valueOf("true"));
		xnor(valueOf("true"), valueOf("true"), valueOf("true"));
	}
	private void xnor(Switch i1, Switch i2, Switch result){
		assertEquals(result, i1.xnor(i2));
	}
}
