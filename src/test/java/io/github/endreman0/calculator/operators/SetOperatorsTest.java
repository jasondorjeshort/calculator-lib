package io.github.endreman0.calculator.operators;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import io.github.endreman0.calculator.Parser;
import io.github.endreman0.calculator.expression.type.Set;

public class SetOperatorsTest{
	@Test
	public void testUnion(){
		unite(valueOf("{1, 2, 3, 4, 5}"), valueOf("{3, 4, 5, 6, 7, 8, 9, 10}"), valueOf("{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}"));
	}
	private void unite(Set i1, Set i2, Set result){
		assertEquals(result, i1.unite(i2));
	}
	@Test
	public void testIntersection(){
		intersect(valueOf("{1, 2, 3, 4, 5}"), valueOf("{3, 4, 5, 6, 7, 8, 9, 10}"), valueOf("{3, 4, 5}"));
	}
	private void intersect(Set i1, Set i2, Set result){
		assertEquals(result, i1.intersect(i2));
	}
	private static Set valueOf(String input){
		String[] tokens = Parser.parse(input);
		Queue<String> queue = new LinkedList<>(Arrays.asList(tokens));
		queue.poll();//Remove the leading "{", as the contract of @ComplexFactory says it won't be there
		return Set.valueOf(queue);
	}
}
