package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.security.cert.X509Certificate;

import org.nhindirect.policy.util.TestUtils;

public class SubjectPublicKeySizeField_injectReferenceValueTest
{
	@Test
	public void testInjectRefereneValue_rsa1024_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final SubjectPublicKeySizeField field = new SubjectPublicKeySizeField();
		
		field.injectReferenceValue(cert);
		
		final int value = field.getPolicyValue().getPolicyValue();
		
		assertEquals(1024, value);
		
	}	
	
	@Test
	public void testInjectRefereneValue_rsa2024_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("umesh.der");
		
		final SubjectPublicKeySizeField field = new SubjectPublicKeySizeField();
		
		field.injectReferenceValue(cert);
		
		final int value = field.getPolicyValue().getPolicyValue();
		
		assertEquals(2024, value);
		
	}	
	
	@Test
	public void testInjectRefereneValue_dsa1024_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("dsa1024.der");
		
		final SubjectPublicKeySizeField field = new SubjectPublicKeySizeField();
		
		field.injectReferenceValue(cert);
		
		final int value = field.getPolicyValue().getPolicyValue();
		
		assertEquals(1024, value);
		
	}	
	
	@Test
	public void testInjectRefereneValue_ecc_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("ecc.der");
		
		final SubjectPublicKeySizeField field = new SubjectPublicKeySizeField();
		
		field.injectReferenceValue(cert);
		
		final int value = field.getPolicyValue().getPolicyValue();
		
		assertEquals(0, value);
		
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
