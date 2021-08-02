package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SubjectPublicKeySizeField_getFieldNameTest
{
	@Test
	public void testGetFielName()
	{
		final SubjectPublicKeySizeField field = new SubjectPublicKeySizeField();
		
		assertEquals(TBSFieldName.SUBJECT_PUBLIC_KEY_INFO, field.getFieldName());
	}
}
