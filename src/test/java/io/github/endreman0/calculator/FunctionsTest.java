package io.github.endreman0.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.endreman0.calculator.expression.type.Decimal;
import io.github.endreman0.calculator.expression.type.Matrix;
import io.github.endreman0.calculator.expression.type.MixedNumber;
import io.github.endreman0.calculator.expression.type.Type;
import io.github.endreman0.calculator.expression.type.Vector;

public class FunctionsTest extends BaseTest{
	@Test
	public void testMixedNumber(){
		test("1_1/3.reciprocal()", fraction(3, 4));
		test("abs(2/3)", fraction(2, 3));
		test("abs(-3_1/2 - 3/14)", mixed(3, 5, 7));
		test("1/2.reciprocal() + 1/3", mixed(2, 1, 3));
		test("1/2.whole()", integer(0));
		test("1/2.numerator()", integer(1));
		test("1/2.denominator()", integer(2));
		test("(3 + 2) * 5", integer(25));
	}
	@Test
	public void testMatrix(){
		test("abs([1, -2][-3, 4])", Matrix.valueOf(new MixedNumber[][]{{MixedNumber.valueOf(1), MixedNumber.valueOf(2)}, {MixedNumber.valueOf(3), MixedNumber.valueOf(4)}}));
		test("([1, 2] + [3, 4]) * [5][6]", Matrix.valueOf(new MixedNumber[][]{{MixedNumber.valueOf(56)}}));
	}
	@Test
	public void testVector(){
		test("<4, 3>.magnitude()", Decimal.valueOf(5));
		
		test("<5, 0>.direction()", Decimal.valueOf(0));
		test("<5, 5>.direction()", Decimal.valueOf(45));
		test("<0, 5>.direction()", Decimal.valueOf(90));
		test("<0, -5>.direction()", Decimal.valueOf(-90));
		test("<-5, -5>.direction()", Decimal.valueOf(-135));
		test("(<5, 0> + <0, 5>).direction()", Decimal.valueOf(45));
		test("<-5, -5>.direction()", Decimal.valueOf(-135));
		
		test("<1, -2, 3_1/3>.get(2)", MixedNumber.valueOf(3, 1, 3));
		test("<1, -2, 3_1/3>.size()", MixedNumber.valueOf(3));
		
		test("<4, 3_1/2, 7/10>.opposite()", Vector.valueOf(MixedNumber.valueOf(-4), MixedNumber.valueOf(-3, 1, 2), MixedNumber.valueOf(-7, 10)));
		test("<5, 5>.quadrant()", MixedNumber.valueOf(1));
		test("<-5, 5>.quadrant()", MixedNumber.valueOf(2));
		test("<-5, -5>.quadrant()", MixedNumber.valueOf(3));
		test("<5, -5>.quadrant()", MixedNumber.valueOf(4));
		test("<-3, 5>.toQuadrant(4)", Vector.valueOf(MixedNumber.valueOf(3), MixedNumber.valueOf(-5)));
	}
//	@Test
//	public void testSet(){
//		test("{1, -3_1/3, 2, 4_1/5}.get(0)", MixedNumber.valueOf(1));
//		test("{1, -3_1/3, 2, 4_1/5}.size()", MixedNumber.valueOf(4));
//		test("{1, -3_1/3, 2, 4_1/5}.contains(-3_1/3)", Switch.valueOf(true));
//		test("{1, -3_1/3, 2, 4_1/5}.contains(-1)", Switch.valueOf(false));
//	}
	@Test
	public void testDecimal(){
		test("sin(90.0)", decimal(1));
		test("cos(180.0)", decimal(-1));
		test("asin(-1.0)", decimal(-90));
		test("acos(0.0)", decimal(90));
	}
	@Test
	public void testSwitch(){
		test("not(true)", bool(false));
		test("not(false)", bool(true));
		
		test("true.and(true)", bool(true));
		test("false.and(true)", bool(false));
		test("true.and(false)", bool(false));
		test("false.and(false)", bool(false));
		
		test("true.or(true)", bool(true));
		test("false.or(true)", bool(true));
		test("true.or(false)", bool(true));
		test("false.or(false)", bool(false));
		
		test("true.xor(true)", bool(false));
		test("false.xor(true)", bool(true));
		test("true.xor(false)", bool(true));
		test("false.xor(false)", bool(false));
		
		test("true.xnor(true)", bool(true));
		test("false.xnor(true)", bool(false));
		test("true.xnor(false)", bool(false));
		test("false.xnor(false)", bool(true));
	}
	@Test
	public void testBasedNumber(){
		test("36xZZ.value()", MixedNumber.valueOf(1295));
		test("2x10101010.base()", integer(2));
	}
	@Test
	public void testTime(){
		test("15:24.hours()", integer(15));
		test("3:52.minutes()", integer(52));
		test("1:52:17.seconds()", integer(17));
		
		test("3:50.partialHours()", mixed(3, 5, 6));
		test("1:30:15.partialMinutes()", mixed(90, 1, 4));
		test("1:01:17.partialSeconds()", integer(3600 + 60 + 17));
		
		test("0:00.isAM()", bool(true));
		test("3:17.isAM()", bool(true));
		test("11:59.isAM()", bool(true));
		test("12:00.isAM()", bool(false));
		test("15:45.isAM()", bool(false));
		test("23:59.isAM()", bool(false));
		
		test("0:00.isPM()", bool(false));
		test("3:17.isPM()", bool(false));
		test("11:59.isPM()", bool(false));
		test("12:00.isPM()", bool(true));
		test("15:45.isPM()", bool(true));
		test("23:59.isPM()", bool(true));
	}
	private void test(String input, Type output){
		assertEquals(output, calculate(input));
	}
}
