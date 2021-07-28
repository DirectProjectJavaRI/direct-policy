package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.security.cert.X509Certificate;

import org.nhindirect.policy.PolicyProcessException;
import org.nhindirect.policy.util.TestUtils;

public class AbstractX509Field_testGetObjectTest
{
	@Test
	public void testGetObject_validObjectEncoding() throws Exception
	{
		// load cert
		final X509Certificate cert = TestUtils.loadCertificate("CernerDirect DevCert Provider CA.der");
		
		// use a concreate class and check for key usage
		final SignatureAlgorithmField field = new SignatureAlgorithmField();
		assertNotNull(field.getObject(cert.getExtensionValue(ExtensionIdentifier.KEY_USAGE.getId())));
	}
	
	@Test
	public void testGetObject_invalidObjectEncoding() throws Exception
	{
		
		// use a concreate class
		final SignatureAlgorithmField field = new SignatureAlgorithmField();
		boolean exceptionOccured = false;
		
		try
		{
			assertNotNull(field.getObject(new byte[]{1,2,3}));
		}
		catch(PolicyProcessException e)
		{
			exceptionOccured = true;
		}
		
		assertTrue(exceptionOccured);
	}	
}
