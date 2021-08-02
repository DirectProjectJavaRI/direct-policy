package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SignatureAlgorithmField_getX509FieldTypeTest
{
	@Test
	public void testGetX509FieldType()
	{
		final SignatureAlgorithmField field = new SignatureAlgorithmField();
		assertEquals(X509FieldType.SIGNATURE_ALGORITHM, field.getX509FieldType());
	}
}
