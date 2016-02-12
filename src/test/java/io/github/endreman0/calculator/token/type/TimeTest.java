//package io.github.endreman0.calculator.token.type;
//
//import static io.github.endreman0.calculator.token.type.Time.valueOf;
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//import io.github.endreman0.calculator.token.type.Time;
//
//public class TimeTest{
//	private final Time[] instances = {valueOf(3, 15), valueOf(1, 17), valueOf(0, 18), valueOf(5, 15, 30), valueOf(17, 35)};
//	@Test
//	public void testHashCode(){
//		for(Time num : instances)
//			for(Time num2 : instances)
//				if(num.equals(num2)) assertEquals(num.hashCode(), num2.hashCode());//Assert that all equal instances have the same hash code
//	}
//	@Test
//	public void testValueOf(){
//		for(Time num : instances)
//			assertEquals(num, valueOf(num.toParseableString()));
//	}
//	@Test
//	public void testClone(){
//		for(Time num : instances) assertEquals(num, num.clone());
//	}
//	@Test
//	public void testToString(){
//		assertEquals("3:15", instances[0].toParseableString());
//		assertEquals("1:17", instances[1].toParseableString());
//		assertEquals("0:18", instances[2].toParseableString());
//		assertEquals("5:15:30", instances[3].toParseableString());
//		assertEquals("17:35", instances[4].toParseableString());
//		
//		assertEquals("3:15AM", instances[0].toDisplayString());
//		assertEquals("1:17AM", instances[1].toDisplayString());
//		assertEquals("12:18AM", instances[2].toDisplayString());
//		assertEquals("5:15:30AM", instances[3].toDisplayString());
//		assertEquals("5:35PM", instances[4].toDisplayString());
//	}
//	@Test
//	public void testEquals(){
//		for(Time num : instances) assertTrue(num.equals(num));
//		for(int i=0; i<instances.length; i++){
//			for(int j=i+1; j<instances.length; j++){
//				assertFalse(instances[i].equals(instances[j]));
//				assertFalse(instances[j].equals(instances[i]));
//			}
//		}
//	}
//}
