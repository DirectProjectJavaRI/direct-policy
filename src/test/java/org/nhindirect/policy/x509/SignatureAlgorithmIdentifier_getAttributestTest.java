package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SignatureAlgorithmIdentifier_getAttributestTest
{
	@Test
	public void testGetAttributes()
	{
		assertEquals("1.2.840.113549.1.1.5", SignatureAlgorithmIdentifier.SHA1RSA.getId());
		assertEquals("sha1RSA", SignatureAlgorithmIdentifier.SHA1RSA.getName());
	}
}
