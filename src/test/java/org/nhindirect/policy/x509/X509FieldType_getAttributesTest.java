package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class X509FieldType_getAttributesTest 
{
	@Test
	public void testGetAttributes_rfcName()
	{
		assertEquals("Signature", X509FieldType.SIGNATURE.getRfcName());
		assertEquals("Algorithm", X509FieldType.SIGNATURE_ALGORITHM.getRfcName());
		assertEquals("TbsCertificate", X509FieldType.TBS.getRfcName());
	}
	
	@Test
	public void testGetAttributes_getDisplay()
	{		
		assertEquals("Signature", X509FieldType.SIGNATURE.getDisplay());
		assertEquals("Algorithm", X509FieldType.SIGNATURE_ALGORITHM.getDisplay());
		assertEquals("To Be Signed Certificate", X509FieldType.TBS.getDisplay());
		
	}
	
	@Test
	public void testGetAttributes_toString()
	{
		assertEquals("X509.Signature", X509FieldType.SIGNATURE.toString());
		assertEquals("X509.Algorithm", X509FieldType.SIGNATURE_ALGORITHM.toString());
		assertEquals("X509.TbsCertificate", X509FieldType.TBS.toString());
	}
}
