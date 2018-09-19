package org.nhindirect.policy.x509;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.cert.X509Certificate;
import java.util.Collection;

import org.nhindirect.policy.util.TestUtils;
import org.junit.Test;
import org.nhindirect.policy.PolicyRequiredException;
import org.nhindirect.policy.x509.ExtendedKeyUsageExtensionField;
import org.nhindirect.policy.x509.ExtendedKeyUsageIdentifier;

public class ExtendedKeyUsageExtensionField_injectReferenceValueTest 
{
	@Test
	public void testInjectRefereneValue_extendedKeyUsageDoesNotExist_notRequired_assertValueEmpty() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final ExtendedKeyUsageExtensionField field = new ExtendedKeyUsageExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		assertTrue(field.getPolicyValue().getPolicyValue().isEmpty());
	}
	
	@Test
	public void testInjectRefereneValue_extendedKeyUsageDoesNotExist_required_assertException() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final ExtendedKeyUsageExtensionField field = new ExtendedKeyUsageExtensionField(true);
		
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
	public void testInjectRefereneValue_keyUsageExists_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("mshost.der");
		
		final ExtendedKeyUsageExtensionField field = new ExtendedKeyUsageExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		Collection<String> usages = field.getPolicyValue().getPolicyValue();
		assertFalse(field.getPolicyValue().getPolicyValue().isEmpty());
		
		assertTrue(usages.contains(ExtendedKeyUsageIdentifier.ID_KP_EMAIL_PROTECTION.getId()));
		assertFalse(usages.contains(ExtendedKeyUsageIdentifier.ID_KP_CLIENT_AUTH.getId()));	
		
	}	
	
	@Test
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final ExtendedKeyUsageExtensionField field = new ExtendedKeyUsageExtensionField(true);
		
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
