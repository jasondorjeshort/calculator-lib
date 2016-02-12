//package io.github.endreman0.calculator;
//
//import static io.github.endreman0.calculator.token.type.MixedNumber.valueOf;
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//import io.github.endreman0.calculator.error.CalculatorException;
//
//public class InputTest{
//	@Test
//	public void testAdd() throws CalculatorException{
//		assertEquals(valueOf(8), Calculator.calculate("3 + 5"));
//		assertEquals(valueOf(2, 1, 5), Calculator.calculate("1_4/5 + 2/5"));
//		assertEquals(valueOf(3, 4, 15), Calculator.calculate("2_1/5 + 1_1/15"));
//	}
//	@Test
//	public void testSubtract() throws CalculatorException{
//		assertEquals(valueOf(1, 4, 21), Calculator.calculate("3_1/3 - 2_1/7"));
//		assertEquals(valueOf(0), Calculator.calculate("1/8 - 1/8"));
//	}
//	@Test
//	public void testMultiply() throws CalculatorException{
//		assertEquals(valueOf(5, 21), Calculator.calculate("5/7 * 1/3"));
//		assertEquals(valueOf(3, 7), Calculator.calculate("1/7 * 3"));
//	}
//	@Test
//	public void testDivide() throws CalculatorException{
//		assertEquals(valueOf(5, 9), Calculator.calculate("5 / 9"));
//		assertEquals(valueOf(11), Calculator.calculate("3_1/7 / 2/7"));
//	}
//	@Test
//	public void testModulus() throws CalculatorException{
//		assertEquals(valueOf(2, 9), Calculator.calculate("1_1/9 % 4/9"));
//	}
//}
