package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CertificatePolicyCpsUriExtensionField_getExtensionIdentifierTest
{
	@Test
	public void testGetExtenstionIdentifier()
	{
		final CertificatePolicyCpsUriExtensionField field = new CertificatePolicyCpsUriExtensionField(true);
		assertEquals(ExtensionIdentifier.CERTIFICATE_POLICIES, field.getExtentionIdentifier());
	}
}
