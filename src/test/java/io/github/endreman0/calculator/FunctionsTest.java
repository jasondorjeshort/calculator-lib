package io.github.endreman0.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.endreman0.calculator.expression.type.Decimal;
import io.github.endreman0.calculator.expression.type.MixedNumber;
import io.github.endreman0.calculator.expression.type.Switch;
import io.github.endreman0.calculator.expression.type.Type;

public class FunctionsTest{
	@Test
	public void testMixedNumber(){
		test("1_1/3.reciprocal()", MixedNumber.valueOf(3, 4));
		test("abs(2/3)", MixedNumber.valueOf(2, 3));
		test("abs(-3_1/2 - 3/14)", MixedNumber.valueOf(3, 5, 7));
		test("1/2.reciprocal() + 1/3", MixedNumber.valueOf(2, 1, 3));
		test("1/2.whole()", MixedNumber.valueOf(0));
		test("1/2.numerator()", MixedNumber.valueOf(1));
		test("1/2.denominator()", MixedNumber.valueOf(2));
		test("(3 + 2) * 5", MixedNumber.valueOf(25));
	}
//	@Test
//	public void testMatrix(){
//		test("abs([1|-2][-3|4])", Matrix.valueOf(new MixedNumber[][]{{MixedNumber.valueOf(1), MixedNumber.valueOf(2)}, {MixedNumber.valueOf(3), MixedNumber.valueOf(4)}}));
//		test("([1|2] + [3|4]) * [5][6]", Matrix.valueOf(new MixedNumber[][]{{MixedNumber.valueOf(56)}}));
//	}
//	@Test
//	public void testVector(){
//		test("<4|3>.magnitude()", Decimal.valueOf(5));
//		
//		test("<5|0>.direction()", Decimal.valueOf(0));
//		test("<5|5>.direction()", Decimal.valueOf(45));
//		test("<0|5>.direction()", Decimal.valueOf(90));
//		test("<0|-5>.direction()", Decimal.valueOf(-90));
//		test("<-5|-5>.direction()", Decimal.valueOf(-135));
//		test("(<5|0> + <0|5>).direction()", Decimal.valueOf(45));
//		test("<-5|-5>.direction()", Decimal.valueOf(-135));
//		
//		test("<1|-2|3_1/3>.get(2)", MixedNumber.valueOf(3, 1, 3));
//		test("<1|-2|3_1/3>.size()", MixedNumber.valueOf(3));
//		
//		test("<4|3_1/2|7/10>.opposite()", Vector.valueOf(MixedNumber.valueOf(-4), MixedNumber.valueOf(-3, 1, 2), MixedNumber.valueOf(-7, 10)));
//		test("<5|5>.quadrant()", MixedNumber.valueOf(1));
//		test("<-5|5>.quadrant()", MixedNumber.valueOf(2));
//		test("<-5|-5>.quadrant()", MixedNumber.valueOf(3));
//		test("<5|-5>.quadrant()", MixedNumber.valueOf(4));
//		test("<-3|5>.toQuadrant(4)", Vector.valueOf(MixedNumber.valueOf(3), MixedNumber.valueOf(-5)));
//	}
//	@Test
//	public void testSet(){
//		test("{1|-3_1/3|2|4_1/5}.get(0)", MixedNumber.valueOf(1));
//		test("{1|-3_1/3|2|4_1/5}.size()", MixedNumber.valueOf(4));
//		test("{1|-3_1/3|2|4_1/5}.contains(-3_1/3)", Switch.valueOf(true));
//		test("{1|-3_1/3|2|4_1/5}.contains(-1)", Switch.valueOf(false));
//	}
	@Test
	public void testDecimal(){
		test("sin(90.0)", Decimal.valueOf(1));
		test("cos(180.0)", Decimal.valueOf(-1));
		test("asin(-1.0)", Decimal.valueOf(-90));
		test("acos(0.0)", Decimal.valueOf(90));
	}
	@Test
	public void testSwitch(){
		test("not(true)", Switch.valueOf(false));
		test("not(false)", Switch.valueOf(true));
		
		test("true.and(true)", Switch.valueOf(true));
		test("false.and(true)", Switch.valueOf(false));
		test("true.and(false)", Switch.valueOf(false));
		test("false.and(false)", Switch.valueOf(false));
		
		test("true.or(true)", Switch.valueOf(true));
		test("false.or(true)", Switch.valueOf(true));
		test("true.or(false)", Switch.valueOf(true));
		test("false.or(false)", Switch.valueOf(false));
		
		test("true.xor(true)", Switch.valueOf(false));
		test("false.xor(true)", Switch.valueOf(true));
		test("true.xor(false)", Switch.valueOf(true));
		test("false.xor(false)", Switch.valueOf(false));
		
		test("true.xnor(true)", Switch.valueOf(true));
		test("false.xnor(true)", Switch.valueOf(false));
		test("true.xnor(false)", Switch.valueOf(false));
		test("false.xnor(false)", Switch.valueOf(true));
	}
	@Test
	public void testBasedNumber(){
		test("36xZZ.value()", MixedNumber.valueOf(1295));
		test("2x10101010.base()", MixedNumber.valueOf(2));
	}
	@Test
	public void testTime(){
		test("15:24.hours()", MixedNumber.valueOf(15));
		test("3:52.minutes()", MixedNumber.valueOf(52));
		test("1:52:17.seconds()", MixedNumber.valueOf(17));
		
		test("3:50.partialHours()", MixedNumber.valueOf(3, 5, 6));
		test("1:30:15.partialMinutes()", MixedNumber.valueOf(90, 1, 4));
		test("1:01:17.partialSeconds()", MixedNumber.valueOf(3600 + 60 + 17));
		
		test("0:00.isAM()", Switch.valueOf(true));
		test("3:17.isAM()", Switch.valueOf(true));
		test("11:59.isAM()", Switch.valueOf(true));
		test("12:00.isAM()", Switch.valueOf(false));
		test("15:45.isAM()", Switch.valueOf(false));
		test("23:59.isAM()", Switch.valueOf(false));
		
		test("0:00.isPM()", Switch.valueOf(false));
		test("3:17.isPM()", Switch.valueOf(false));
		test("11:59.isPM()", Switch.valueOf(false));
		test("12:00.isPM()", Switch.valueOf(true));
		test("15:45.isPM()", Switch.valueOf(true));
		test("23:59.isPM()", Switch.valueOf(true));
	}
	private void test(String input, Type output){
		assertEquals(output, Calculator.calculate(input));
	}
}
