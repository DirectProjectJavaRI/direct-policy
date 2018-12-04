package org.nhindirect.policy.x509;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AbstractExtensionField_getFieldNameTest
{
	@Test
	public void testGetFieldName()
	{
		// use a concrete instance
		final CertificatePolicyIndentifierExtensionField field = new CertificatePolicyIndentifierExtensionField(true);
	
		assertEquals(TBSFieldName.EXTENSIONS, field.getFieldName());
	}
	
}
