package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
public class RDNAttributeIdentifier_getAttributesTest
{
	@Test
	public void testGetAttributes_toString()
	{
		assertEquals("CN", RDNAttributeIdentifier.COMMON_NAME.toString());
		assertEquals("C", RDNAttributeIdentifier.COUNTRY.toString());
	}
	
	public void testGetAttributes_fromName()
	{
		assertEquals(RDNAttributeIdentifier.COMMON_NAME, RDNAttributeIdentifier.fromName("CN"));
		assertNull(RDNAttributeIdentifier.fromName("CN."));
		//assertEquals("C", RDNAttributeIdentifier.COUNTRY.toString());
	}
}
