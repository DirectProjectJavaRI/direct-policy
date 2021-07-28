package org.nhindirect.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyValueFactory_getInstanceTest
{
	@Test
	public void testGetInstance_assertValue()
	{
		PolicyValue<Integer> value = PolicyValueFactory.getInstance(12345);
		
		assertTrue(value.getPolicyValue() instanceof Integer);
		assertEquals(12345, value.getPolicyValue().intValue());
		assertEquals("12345", value.toString());
		assertEquals(new Integer(12345).hashCode(), value.getPolicyValue().hashCode());
		
		
		assertFalse(value.equals(null));
		assertTrue(value.getPolicyValue().equals(12345));
		assertTrue(value.equals(PolicyValueFactory.getInstance(12345)));
		
		
		assertTrue(value.getPolicyValue().equals(12345));
	}
	
}
