package org.nhindirect.policy.x509;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.cert.X509Certificate;
import java.util.Collection;

import org.nhindirect.policy.util.TestUtils;
import org.junit.Test;
import org.nhindirect.policy.PolicyRequiredException;
import org.nhindirect.policy.x509.CertificatePolicyIndentifierExtensionField;


public class CertificatePolicyIndentifierExtensionField_injectReferenceValueTest
{
	@Test
	public void testInjectRefereneValue_policyDoesNotExist_notRequired_assertValueEmpty() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final CertificatePolicyIndentifierExtensionField field = new CertificatePolicyIndentifierExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		assertTrue(field.getPolicyValue().getPolicyValue().isEmpty());
	}
	
	@Test
	public void testInjectRefereneValue_policyDoesNotExist_required_assertException() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final CertificatePolicyIndentifierExtensionField field = new CertificatePolicyIndentifierExtensionField(true);
		
		boolean exceptionOccured = false;
		
		try
		{
			field.injectReferenceValue(cert);
		}
		catch (PolicyRequiredException e)
		{
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}	
	
	@Test
	public void testInjectRefereneValue_policyExists_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("CernerDirectProviderCA.der");
		
		final CertificatePolicyIndentifierExtensionField field = new CertificatePolicyIndentifierExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		Collection<String> usages = field.getPolicyValue().getPolicyValue();
		assertFalse(field.getPolicyValue().getPolicyValue().isEmpty());
		
		assertTrue(usages.contains("2.16.840.1.113883.3.1313.0.1"));
		
	}	
	
	@Test
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final CertificatePolicyIndentifierExtensionField field = new CertificatePolicyIndentifierExtensionField(true);
		
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
