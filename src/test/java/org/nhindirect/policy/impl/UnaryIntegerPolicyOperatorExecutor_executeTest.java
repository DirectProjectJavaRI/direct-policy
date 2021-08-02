package org.nhindirect.policy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import org.nhindirect.policy.PolicyOperator;
import org.nhindirect.policy.PolicyValue;
import org.nhindirect.policy.PolicyValueFactory;

public class UnaryIntegerPolicyOperatorExecutor_executeTest
{
	@Test
	public void testExecute_size_assertResults()
	{
		// size 1
		PolicyValue<List<String>> op1 = PolicyValueFactory.getInstance(Arrays.asList("A"));
		
		UnaryIntegerPolicyOperatorExecutor<List<String>> operator = new UnaryIntegerPolicyOperatorExecutor<List<String>>(op1, PolicyOperator.SIZE);
		assertEquals(1, operator.execute().getPolicyValue().intValue());
		
		// size 0
		op1 = PolicyValueFactory.getInstance(Arrays.asList(new String[] {}));
		
		operator = new UnaryIntegerPolicyOperatorExecutor<List<String>>(op1, PolicyOperator.SIZE);
		assertEquals(0, operator.execute().getPolicyValue().intValue());
	}
}
