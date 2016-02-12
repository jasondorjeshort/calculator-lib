//package io.github.endreman0.calculator.token.type;
//
//import static org.junit.Assert.*;
//
//import java.util.Arrays;
//
//import org.junit.Test;
//
//import io.github.endreman0.calculator.error.CalculatorException;
//import io.github.endreman0.calculator.token.type.Matrix;
//import io.github.endreman0.calculator.token.type.MixedNumber;
//
//public class MatrixTest{
//	private final MixedNumber[][] data = {
//			{MixedNumber.valueOf(1), MixedNumber.valueOf(2, 3), MixedNumber.valueOf(-5, 1 ,3)},
//			{MixedNumber.valueOf(-3, 1, 2), MixedNumber.valueOf(-10), MixedNumber.valueOf(7)}
//	};
//	private final Matrix test = Matrix.valueOf(data);
//	@Test
//	public void testRows(){
//		assertEquals(2, test.rows());
//	}
//	@Test
//	public void testColumns(){
//		assertEquals(3, test.columns());
//	}
//	@Test
//	public void testGet(){
//		for(int r=0; r<data.length; r++)
//			for(int c=0; c<data[r].length; c++)
//				assertEquals(data[r][c], test.get(r, c));
//	}
//	@Test
//	public void testEquals(){
//		assertTrue(test.equals(test));
//		assertTrue(test.equals(test.clone()));
//		assertFalse(test.equals(null));
//		Matrix m = Matrix.valueOf(new MixedNumber[][]{{MixedNumber.valueOf(3), MixedNumber.valueOf(5)}, {MixedNumber.valueOf(4), MixedNumber.valueOf(2)}});
//		assertFalse(test.equals(m));
//	}
//	@Test
//	public void testToString(){
//		assertEquals("[1 | 2/3 | -5_1/3]\r\n[-3_1/2 | -10 | 7]", test.toDisplayString());
//		assertEquals("[1|2/3|-5_1/3][-3_1/2|-10|7]", test.toParseableString());
//		assertEquals("Matrix[2,3]", test.toDescriptorString());
//	}
//	@Test
//	public void testToArray(){
//		assertTrue(Arrays.deepEquals(data, test.toArray()));
//	}
//	@Test
//	public void testValueOfString() throws CalculatorException{
//		assertEquals(test, Matrix.valueOf(test.toString()));
//	}
//}
