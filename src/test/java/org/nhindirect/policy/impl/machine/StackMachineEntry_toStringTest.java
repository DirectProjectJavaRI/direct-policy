package org.nhindirect.policy.impl.machine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.nhindirect.policy.PolicyOperator;
import org.nhindirect.policy.PolicyValueFactory;

public class StackMachineEntry_toStringTest
{
	@Test
	public void testToString_operatorEntry_assertString()
	{
		
		final StackMachineEntry entry = new StackMachineEntry(PolicyOperator.EQUALS);
		
		assertTrue(entry.toString().startsWith("Entry Type"));
		assertTrue(entry.toString().contains("Operator:"));
		assertTrue(entry.toString().contains("equals"));		
	}
	
	@Test
	public void testToString_valueEntry_assertString()
	{		
		final StackMachineEntry entry = new StackMachineEntry(PolicyValueFactory.getInstance(12345));
		
		assertTrue(entry.toString().startsWith("Entry Type"));
		assertTrue(entry.toString().contains("Value:"));
		assertTrue(entry.toString().contains("12345"));		
	}	
}
