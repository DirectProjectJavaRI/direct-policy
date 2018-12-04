package org.nhindirect.policy.x509;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SignatureAlgorithmField_getX509FieldTypeTest
{
	@Test
	public void testGetX509FieldType()
	{
		final SignatureAlgorithmField field = new SignatureAlgorithmField();
		assertEquals(X509FieldType.SIGNATURE_ALGORITHM, field.getX509FieldType());
	}
}
