package org.nhindirect.policy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LiteralPolicyExpressionFactory_getInstanceTest
{
	@Test
	public void testGetInstance_policyValueT()
	{
		LiteralPolicyExpression<Integer> value = 
				LiteralPolicyExpressionFactory.getInstance(PolicyValueFactory.getInstance(1234));
		
		assertNotNull(value);
		assertEquals(1234, (int)value.getPolicyValue().getPolicyValue());
		assertEquals(PolicyExpressionType.LITERAL, value.getExpressionType());
	}
	
	@Test
	public void testGetInstance_valueT()
	{
		LiteralPolicyExpression<Integer> value = 
				LiteralPolicyExpressionFactory.getInstance(1234);
		
		assertNotNull(value);
		assertEquals(1234, (int)value.getPolicyValue().getPolicyValue());
		assertEquals(PolicyExpressionType.LITERAL, value.getExpressionType());
	}
}
