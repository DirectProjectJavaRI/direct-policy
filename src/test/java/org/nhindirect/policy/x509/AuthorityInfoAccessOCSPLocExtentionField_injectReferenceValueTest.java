package org.nhindirect.policy.x509;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.cert.X509Certificate;
import java.util.Collection;

import org.nhindirect.policy.util.TestUtils;
import org.junit.Test;
import org.nhindirect.policy.PolicyRequiredException;
import org.nhindirect.policy.x509.AuthorityInfoAccessOCSPLocExtentionField;

public class AuthorityInfoAccessOCSPLocExtentionField_injectReferenceValueTest
{
	@Test
	public void testInjectRefereneValue_aiaDoesNotExist_notRequired_assertValueEmpty() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final AuthorityInfoAccessOCSPLocExtentionField field = new AuthorityInfoAccessOCSPLocExtentionField(false);
		
		field.injectReferenceValue(cert);
		
		assertTrue(field.getPolicyValue().getPolicyValue().isEmpty());
	}
	
	@Test
	public void testInjectRefereneValue_aiaDoesNotExist_required_assertException() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final AuthorityInfoAccessOCSPLocExtentionField field = new AuthorityInfoAccessOCSPLocExtentionField(true);
		
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
	public void testInjectRefereneValue_aiaExists_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("CernerDirectProviderCA.der");
		
		final AuthorityInfoAccessOCSPLocExtentionField field = new AuthorityInfoAccessOCSPLocExtentionField(false);
		
		field.injectReferenceValue(cert);
		
		Collection<String> usages = field.getPolicyValue().getPolicyValue();
		assertFalse(field.getPolicyValue().getPolicyValue().isEmpty());
		
		assertTrue(usages.contains("http://ca.cerner.com/OCSP"));
		
	}	
	
	@Test
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final AuthorityInfoAccessOCSPLocExtentionField field = new AuthorityInfoAccessOCSPLocExtentionField(true);
		
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
