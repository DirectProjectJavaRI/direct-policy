package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.security.cert.X509Certificate;
import java.util.Collection;

import org.nhindirect.policy.PolicyRequiredException;
import org.nhindirect.policy.util.TestUtils;

public class AuthorityInfoAccessExtentionField_injectReferenceValueTest
{
	@Test
	public void testInjectRefereneValue_aiaDoesNotExist_notRequired_assertValueEmpty() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final AuthorityInfoAccessExtentionField field = new AuthorityInfoAccessExtentionField(false);
		
		field.injectReferenceValue(cert);
		
		assertTrue(field.getPolicyValue().getPolicyValue().isEmpty());
	}
	
	@Test
	public void testInjectRefereneValue_aiaDoesNotExist_required_assertException() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final AuthorityInfoAccessExtentionField field = new AuthorityInfoAccessExtentionField(true);
		
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
		
		final AuthorityInfoAccessExtentionField field = new AuthorityInfoAccessExtentionField(false);
		
		field.injectReferenceValue(cert);
		
		Collection<String> usages = field.getPolicyValue().getPolicyValue();
		assertFalse(field.getPolicyValue().getPolicyValue().isEmpty());
		
		assertTrue(usages.contains(AuthorityInfoAccessMethodIdentifier.OCSP.getName() + ":" + "http://ca.cerner.com/OCSP"));
		assertTrue(usages.contains(AuthorityInfoAccessMethodIdentifier.CA_ISSUERS.getName() + ":" + "http://ca.cerner.com/public/root.der"));
		
	}	
	
	@Test
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final AuthorityInfoAccessExtentionField field = new AuthorityInfoAccessExtentionField(true);
		
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
