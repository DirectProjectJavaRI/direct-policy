package org.nhindirect.policy.x509;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CertificatePolicyCpsUriExtensionField_getExtensionIdentifierTest
{
	@Test
	public void testGetExtenstionIdentifier()
	{
		final CertificatePolicyCpsUriExtensionField field = new CertificatePolicyCpsUriExtensionField(true);
		assertEquals(ExtensionIdentifier.CERTIFICATE_POLICIES, field.getExtentionIdentifier());
	}
}
