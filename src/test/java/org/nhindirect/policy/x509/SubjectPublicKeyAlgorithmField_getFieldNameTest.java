package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SubjectPublicKeyAlgorithmField_getFieldNameTest
{
	@Test
	public void testGetFieldName()
	{
		final SubjectPublicKeyAlgorithmField field = new SubjectPublicKeyAlgorithmField();
		assertEquals(X509FieldType.TBS, field.getX509FieldType());
		assertEquals(TBSFieldName.SUBJECT_PUBLIC_KEY_INFO, field.getFieldName());
	}
}
