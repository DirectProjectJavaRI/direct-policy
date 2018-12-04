package org.nhindirect.policy.x509;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.cert.X509Certificate;
import java.util.Collection;

import org.nhindirect.policy.util.TestUtils;
import org.junit.Test;
import org.nhindirect.policy.PolicyRequiredException;
import org.nhindirect.policy.x509.CRLDistributionPointNameExtentionField;

public class CRLDistributionPointNameExtentionField_injectReferenceValueTest
{
	@Test
	public void testInjectRefereneValue_crlPointDoesNotExist_notRequired_assertValueEmpty() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final CRLDistributionPointNameExtentionField field = new CRLDistributionPointNameExtentionField(false);
		
		field.injectReferenceValue(cert);
		
		assertTrue(field.getPolicyValue().getPolicyValue().isEmpty());
	}
	
	@Test
	public void testInjectRefereneValue_crlPointDoesNotExist_required_assertException() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final CRLDistributionPointNameExtentionField field = new CRLDistributionPointNameExtentionField(true);
		
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
	public void testInjectRefereneValue_crlPointExists_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("CernerDirectProviderCA.der");
		
		final CRLDistributionPointNameExtentionField field = new CRLDistributionPointNameExtentionField(false);
		
		field.injectReferenceValue(cert);
		
		Collection<String> usages = field.getPolicyValue().getPolicyValue();
		assertFalse(field.getPolicyValue().getPolicyValue().isEmpty());
		
		assertTrue(usages.contains("http://ca.cerner.com/CRL/CERNER-ROOTCA00.crl"));
		
	}	
	
	@Test
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final CRLDistributionPointNameExtentionField field = new CRLDistributionPointNameExtentionField(true);
		
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
