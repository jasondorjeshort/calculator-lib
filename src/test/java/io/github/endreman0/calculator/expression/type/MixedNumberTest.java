package io.github.endreman0.calculator.expression.type;

import static io.github.endreman0.calculator.expression.type.MixedNumber.valueOf;
import static org.junit.Assert.*;

import org.junit.Test;

public class MixedNumberTest{
	private final MixedNumber instance = valueOf(3, 2, 3),
			integer = valueOf(5),
			fraction = valueOf(3, 7),
			negative = valueOf(-6, 1, 3),
			fractionNegative = valueOf(-2, 5);
	private final MixedNumber[] instances = {instance, integer, fraction, negative, fractionNegative};
	@Test
	public void testHashCode(){
		for(MixedNumber num : instances) assertEquals(num.numeratorImproper(), num.hashCode());
	}
	@Test
	public void testValueOf(){
		for(MixedNumber num : instances){
			assertEquals(num, MixedNumber.valueOf(num.toString()));
		}
	}
	@Test
	public void testClone(){
		for(MixedNumber num : instances) assertEquals(num, num.clone());
	}
	@Test
	public void testToString(){
		assertEquals("3_2/3", instance.toString());
		assertEquals("5", integer.toString());
		assertEquals("3/7", fraction.toString());
		assertEquals("-6_1/3", negative.toString());
		assertEquals("-2/5", fractionNegative.toString());
	}
	@Test
	public void testEquals(){
		for(MixedNumber num : instances) assertTrue(num.equals(num));
		for(int i=0; i<instances.length; i++){
			for(int j=i+1; j<instances.length; j++){
				assertFalse(instances[i].equals(instances[j]));
				assertFalse(instances[j].equals(instances[i]));
			}
		}
	}
	@Test
	public void testValue(){
		assertEquals(3 + (2D/3), instance.value(), 0);
		assertEquals(integer.whole(), integer.value(), 0);
		assertEquals(-(6 + 1D/3), negative.value(), 0);
	}
}
