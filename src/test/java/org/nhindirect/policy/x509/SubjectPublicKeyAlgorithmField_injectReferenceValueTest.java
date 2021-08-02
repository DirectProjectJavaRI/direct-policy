package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.security.cert.X509Certificate;

import org.nhindirect.policy.util.TestUtils;

public class SubjectPublicKeyAlgorithmField_injectReferenceValueTest
{		
	@Test
	public void testInjectRefereneValue_rsaAlg_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final SubjectPublicKeyAlgorithmField field = new SubjectPublicKeyAlgorithmField();
		
		field.injectReferenceValue(cert);
		
		final String value = field.getPolicyValue().getPolicyValue();
		
		assertEquals(PublicKeyAlgorithmIdentifier.RSA.getId(), value);
		
	}	
	
	@Test
	public void testInjectRefereneValue_dsaAlg_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("dsa1024.der");
		
		final SubjectPublicKeyAlgorithmField field = new SubjectPublicKeyAlgorithmField();
		
		field.injectReferenceValue(cert);
		
		final String value = field.getPolicyValue().getPolicyValue();
		
		assertEquals(PublicKeyAlgorithmIdentifier.DSA.getId(), value);
		
	}		
	
	@Test
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final SubjectPublicKeyAlgorithmField field = new SubjectPublicKeyAlgorithmField();
		
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
