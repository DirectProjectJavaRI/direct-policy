package org.nhindirect.policy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.nhindirect.policy.PolicyOperator;
import org.nhindirect.policy.PolicyValue;
import org.nhindirect.policy.PolicyValueFactory;

public class BinaryCollectionPolicyOperatorExecutor_executeTest
{
	@Test
	public void testExecute_intersection_assertResults()
	{
		// both intersection of 1,2,3
		PolicyValue<List<String>> op1 = PolicyValueFactory.getInstance(Arrays.asList("A", "B", "C", "D"));
		PolicyValue<List<String>> op2 = PolicyValueFactory.getInstance(Arrays.asList("A", "B", "E"));
		
		BinaryCollectionPolicyOperatorExecutor<List<String>,List<String>> operator = 
				new BinaryCollectionPolicyOperatorExecutor<List<String>,List<String>>(op1, op2, PolicyOperator.INTERSECTION);
		
		Collection<?> resultList = operator.execute().getPolicyValue();
		assertEquals(2, resultList.size());
		assertTrue(resultList.contains("A"));
		assertTrue(resultList.contains("B"));
		

		op1 = PolicyValueFactory.getInstance(Arrays.asList("A", "B", "C"));
		
		op2 = PolicyValueFactory.getInstance(Arrays.asList("D", "E", "F"));
		
		operator = new BinaryCollectionPolicyOperatorExecutor<List<String>,List<String>>(op1, op2, PolicyOperator.INTERSECTION);
		assertTrue(operator.execute().getPolicyValue().isEmpty());

	}
}
