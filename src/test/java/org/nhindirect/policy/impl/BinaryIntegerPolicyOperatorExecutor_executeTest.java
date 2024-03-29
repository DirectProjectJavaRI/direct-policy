package org.nhindirect.policy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import org.nhindirect.policy.PolicyOperator;
import org.nhindirect.policy.PolicyValue;
import org.nhindirect.policy.PolicyValueFactory;

public class BinaryIntegerPolicyOperatorExecutor_executeTest
{
	@Test
	public void testExecute_bitwiseAnd_assertResults()
	{	
		// equals bitwise and
		PolicyValue<Integer> opInt1 = PolicyValueFactory.getInstance(2);
		PolicyValue<Integer> opInt2 = PolicyValueFactory.getInstance(4);
		
		BinaryIntegerPolicyOperatorExecutor<Integer, Integer> intOperator = new BinaryIntegerPolicyOperatorExecutor<Integer, Integer>(opInt1, opInt2, PolicyOperator.BITWISE_AND);
		assertEquals((2 & 4), intOperator.execute().getPolicyValue().intValue());

		// not equals bitwise and
		opInt1 = PolicyValueFactory.getInstance(2);
		opInt2 = PolicyValueFactory.getInstance(4);
		
		intOperator = new BinaryIntegerPolicyOperatorExecutor<Integer, Integer>(opInt1, opInt2, PolicyOperator.BITWISE_AND);
		assertFalse((2 & 2) == intOperator.execute().getPolicyValue().intValue());
	}	
	
	@Test
	public void testExecute_bitwiseAnd_stringOperatnd_assertResults()
	{	
		// equals bitwise and
		PolicyValue<Integer> opInt1 = PolicyValueFactory.getInstance(2);
		PolicyValue<String> opInt2 = PolicyValueFactory.getInstance("4");
		
		BinaryIntegerPolicyOperatorExecutor<Integer, String> intOperator = new BinaryIntegerPolicyOperatorExecutor<Integer, String>(opInt1, opInt2, PolicyOperator.BITWISE_AND);
		assertEquals((2 & 4), intOperator.execute().getPolicyValue().intValue());

		// not equals bitwise and
		opInt1 = PolicyValueFactory.getInstance(2);
		opInt2 = PolicyValueFactory.getInstance("4");
		
		intOperator = new BinaryIntegerPolicyOperatorExecutor<Integer, String>(opInt1, opInt2, PolicyOperator.BITWISE_AND);
		assertFalse((2 & 2) == intOperator.execute().getPolicyValue().intValue());
	}	
	
	@Test
	public void testExecute_bitwiseOr_assertResults()
	{	
		// equals bitwise or
		PolicyValue<Integer> opInt1 = PolicyValueFactory.getInstance(2);
		PolicyValue<Integer> opInt2 = PolicyValueFactory.getInstance(4);
		
		BinaryIntegerPolicyOperatorExecutor<Integer, Integer> intOperator = new BinaryIntegerPolicyOperatorExecutor<Integer, Integer>(opInt1, opInt2, PolicyOperator.BITWISE_OR);
		assertEquals((2 | 4), intOperator.execute().getPolicyValue().intValue());

		// not equals bitwise or
		opInt1 = PolicyValueFactory.getInstance(2);
		opInt2 = PolicyValueFactory.getInstance(4);
		
		intOperator = new BinaryIntegerPolicyOperatorExecutor<Integer, Integer>(opInt1, opInt2, PolicyOperator.BITWISE_OR);
		assertFalse((2 | 5) == intOperator.execute().getPolicyValue().intValue());
	}	
}
