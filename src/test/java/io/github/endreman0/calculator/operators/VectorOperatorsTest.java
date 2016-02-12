//package io.github.endreman0.calculator.operators;
//
//import static io.github.endreman0.calculator.token.type.Vector.valueOf;
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//import io.github.endreman0.calculator.error.CalculatorException;
//import io.github.endreman0.calculator.token.type.MixedNumber;
//import io.github.endreman0.calculator.token.type.Vector;
//
//public class VectorOperatorsTest{
//	@Test
//	public void testAdd() throws CalculatorException{
//		add(valueOf("<3|4|5|6>"), valueOf("<1_4/5|3|-1/3|3_1/10>"), valueOf("<4_4/5|7|4_2/3|9_1/10>"));
//	}
//	private void add(Vector i1, Vector i2, Vector result){
//		assertEquals(result, i1.add(i2));
//	}
//	@Test
//	public void testSubtract() throws CalculatorException{
//		subtract(valueOf("<1|2|3>"), valueOf("<0|1|2>"), valueOf("<1|1|1>"));
//	}
//	private void subtract(Vector i1, Vector i2, Vector result){
//		assertEquals(result, i1.subtract(i2));
//	}
//	@Test
//	public void testMultiply() throws CalculatorException{
//		multiply(valueOf(MixedNumber.valueOf(1), MixedNumber.valueOf(-10), MixedNumber.valueOf(3, 1, 3)), MixedNumber.valueOf(-2), valueOf(MixedNumber.valueOf(-2), MixedNumber.valueOf(20), MixedNumber.valueOf(-6, 2, 3)));
//	}
//	private void multiply(Vector i1, MixedNumber i2, Vector result){
//		assertEquals(result, i1.multiply(i2));
//	}
//	@Test
//	public void testDivide() throws CalculatorException{
//		divide(valueOf(MixedNumber.valueOf(7, 10), MixedNumber.valueOf(-3, 1, 5)), MixedNumber.valueOf(3), valueOf(MixedNumber.valueOf(7, 30), MixedNumber.valueOf(-1, 1, 15)));
//	}
//	private void divide(Vector i1, MixedNumber i2, Vector result){
//		assertEquals(result, i1.divide(i2));
//	}
//}
