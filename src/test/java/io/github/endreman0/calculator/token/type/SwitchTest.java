package io.github.endreman0.calculator.token.type;

import static io.github.endreman0.calculator.token.type.Switch.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.github.endreman0.calculator.token.type.Switch;

public class SwitchTest{
	private Switch vTrue = valueOf(true), vFalse = valueOf(false);
	@Test
	public void testHashCode(){
		assertEquals(vTrue.hashCode(), vTrue.clone().hashCode());
		assertEquals(vFalse.hashCode(), vFalse.clone().hashCode());
		assertNotEquals(vTrue.hashCode(), vFalse.hashCode());
	}
	@Test
	public void testEqualsObject(){
		assertTrue(vTrue.equals(vTrue));
		assertTrue(vTrue.equals(vTrue.clone()));
		assertTrue(vFalse.equals(vFalse));
		assertTrue(vFalse.equals(vFalse.clone()));
	}
	@Test
	public void testValueOfBoolean(){
		assertEquals(vTrue, Switch.valueOf(vTrue.value()));
		assertEquals(vFalse, Switch.valueOf(vFalse.value()));
	}
	@Test
	public void testValueOfString(){
		assertEquals(vTrue, Switch.valueOf(vTrue.toParseableString()));
		assertEquals(vFalse, Switch.valueOf(vFalse.toParseableString()));
	}
	@Test
	public void testToString(){
		assertEquals("true", vTrue.toParseableString());
		assertEquals("false", vFalse.toParseableString());
		assertEquals("true", vTrue.toDisplayString());
		assertEquals("false", vFalse.toDisplayString());
		assertEquals("Switch[true]", vTrue.toDescriptorString());
		assertEquals("Switch[false]", vFalse.toDescriptorString());
	}

}
