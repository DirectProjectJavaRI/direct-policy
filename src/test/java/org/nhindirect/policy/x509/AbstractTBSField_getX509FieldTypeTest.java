package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AbstractTBSField_getX509FieldTypeTest
{
	@Test
	public void testGetFieldType()
	{
		// use a concrete instance
		final SubjectPublicKeySizeField field = new SubjectPublicKeySizeField();
		assertEquals(X509FieldType.TBS, field.getX509FieldType());
	}
}
