package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class KeyBitUsage_getAttributesTest
{
	@Test
	public void testGetAttributes_assertAttributes()
	{
		assertEquals((1 << 7), KeyUsageBit.DIGITAL_SIGNATURE.getUsageBit());
		assertEquals("digitalSignature", KeyUsageBit.DIGITAL_SIGNATURE.getName());
	}
}
