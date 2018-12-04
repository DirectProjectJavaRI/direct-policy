package org.nhindirect.policy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.nhindirect.policy.PolicyOperator;
import org.nhindirect.policy.PolicyValue;
import org.nhindirect.policy.PolicyValueFactory;

public class UnaryIntegerPolicyOperatorExecutor_constructTest
{
	@Test
	public void testContruct_validOperators_assertAttributes()
	{
		PolicyValue<Integer> op1 = PolicyValueFactory.getInstance(1);
		
		UnaryIntegerPolicyOperatorExecutor<Integer> operator = new UnaryIntegerPolicyOperatorExecutor<Integer>(op1, PolicyOperator.SIZE);
		assertNotNull(operator);
		assertEquals(op1, operator.operand);
		assertEquals(PolicyOperator.SIZE, operator.operator);
	}
	
	@Test
	public void testContruct_invalidOperator_assertExcpetion()
	{
		PolicyValue<Integer> op1 = PolicyValueFactory.getInstance(1);
		
		boolean exceptionOccured = false;
		try
		{
			new UnaryIntegerPolicyOperatorExecutor<Integer>(op1, PolicyOperator.URI_VALIDATE);
		}
		catch (IllegalArgumentException e)
		{
			exceptionOccured = true;
		}
		
		assertTrue(exceptionOccured);
	}
}
