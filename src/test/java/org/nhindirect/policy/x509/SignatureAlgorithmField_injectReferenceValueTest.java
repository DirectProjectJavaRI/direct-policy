package org.nhindirect.policy.x509;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.cert.X509Certificate;

import org.junit.Test;
import org.nhindirect.policy.util.TestUtils;
import org.nhindirect.policy.x509.SignatureAlgorithmField;
import org.nhindirect.policy.x509.SignatureAlgorithmIdentifier;

public class SignatureAlgorithmField_injectReferenceValueTest
{
	@Test
	public void testInjectReferenceValue_sha1RSA_assertAlgorithm() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final SignatureAlgorithmField field = new SignatureAlgorithmField();
		
		field.injectReferenceValue(cert);
		
		assertEquals(SignatureAlgorithmIdentifier.SHA1RSA.getId(), field.getPolicyValue().getPolicyValue());
	}
	
	@Test
	public void testInjectReferenceValue_sha256RSA_assertAlgorithm() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("CernerDirect DevCert Provider CA.der");
		
		final SignatureAlgorithmField field = new SignatureAlgorithmField();
		
		field.injectReferenceValue(cert);
		
		assertEquals(SignatureAlgorithmIdentifier.SHA256RSA.getId(), field.getPolicyValue().getPolicyValue());
	}	
	
	@Test
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final SignatureAlgorithmField field = new SignatureAlgorithmField();
		
		boolean exceptionOccured = false;
		
		try
		{
			field.getPolicyValue();
		}
		catch (IllegalStateException e)
		{
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}		
}
