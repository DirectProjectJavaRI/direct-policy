package org.nhindirect.policy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import org.nhindirect.policy.PolicyOperator;
import org.nhindirect.policy.PolicyValue;
import org.nhindirect.policy.PolicyValueFactory;

public class BinaryCollectionPolicyOperatorExecutor_constructTest
{
	@Test
	public void testContruct_validOperators_assertAttributes()
	{
		PolicyValue<List<Integer>> op1 = PolicyValueFactory.getInstance(Arrays.asList(1,2,3,4,5));
		PolicyValue<List<Integer>> op2 = PolicyValueFactory.getInstance(Arrays.asList(1,2,3,6,8));
		
		BinaryCollectionPolicyOperatorExecutor<List<Integer>,List<Integer>> operator = 
				new BinaryCollectionPolicyOperatorExecutor<List<Integer>,List<Integer>>(op1, op2, PolicyOperator.INTERSECTION);
		assertNotNull(operator);
		assertEquals(op1, operator.operand1);
		assertEquals(op2, operator.operand2);
		assertEquals(PolicyOperator.INTERSECTION, operator.operator);
	}
	
	@Test
	public void testContruct_invalidOperator_assertExcpetion()
	{
		PolicyValue<List<Integer>> op1 = PolicyValueFactory.getInstance(Arrays.asList(1,2,3,4,5));
		PolicyValue<List<Integer>> op2 = PolicyValueFactory.getInstance(Arrays.asList(1,2,3,6,8));
		
		boolean exceptionOccured = false;
		try
		{
			new BinaryCollectionPolicyOperatorExecutor<List<Integer>,List<Integer>>(op1, op2, PolicyOperator.EMPTY);
		}
		catch (IllegalArgumentException e)
		{
			exceptionOccured = true;
		}
		
		assertTrue(exceptionOccured);
	}
}
