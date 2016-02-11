package io.github.endreman0.calculator.operators;

import static io.github.endreman0.calculator.token.type.Set.valueOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.endreman0.calculator.error.CalculatorException;
import io.github.endreman0.calculator.token.type.Set;

public class SetOperatorsTest{
	@Test
	public void testUnion() throws CalculatorException{
		unite(valueOf("{1|2|3|4|5}"), valueOf("{3|4|5|6|7|8|9|10}"), valueOf("{1|2|3|4|5|6|7|8|9|10}"));
	}
	private void unite(Set i1, Set i2, Set result){
		assertEquals(result, i1.unite(i2));
	}
	@Test
	public void testIntersection() throws CalculatorException{
		intersect(valueOf("{1|2|3|4|5}"), valueOf("{3|4|5|6|7|8|9|10}"), valueOf("{3|4|5}"));
	}
	private void intersect(Set i1, Set i2, Set result){
		assertEquals(result, i1.intersect(i2));
	}
}
