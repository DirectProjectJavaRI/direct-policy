package org.nhindirect.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.nhindirect.policy.impl.BinaryBooleanPolicyOperatorExecutor;

public class PolicyOperator_getEnumAttributesTest
{
	@Test
	public void testGetEnumAttributes_assertAttributeValues()
	{
		PolicyOperator equals = PolicyOperator.EQUALS;
		assertEquals("=", equals.getOperatorToken());
		assertEquals("equals", equals.getOperatorText());
		assertEquals(BinaryBooleanPolicyOperatorExecutor.class, equals.getExecutorClass());
		assertEquals(PolicyOperatorParamsType.BINARY, equals.getParamsType());
	}
}
