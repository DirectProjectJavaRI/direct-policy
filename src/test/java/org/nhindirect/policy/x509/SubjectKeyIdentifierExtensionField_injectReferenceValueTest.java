package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.security.cert.X509Certificate;

import org.nhindirect.policy.util.TestUtils;
import org.nhindirect.policy.PolicyRequiredException;

public class SubjectKeyIdentifierExtensionField_injectReferenceValueTest
{
	@Test
	public void testInjectRefereneValue_keyIdDoesNotExist_notRequired_assertValue0() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("cernerDemosCaCert.der");
		
		final SubjectKeyIdentifierExtensionField field = new SubjectKeyIdentifierExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		assertEquals("", field.getPolicyValue().getPolicyValue());
	}
	
	@Test
	public void testInjectRefereneValue_keyIdDoesNotExist_required_assertException() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("cernerDemosCaCert.der");
		
		final SubjectKeyIdentifierExtensionField field = new SubjectKeyIdentifierExtensionField(true);
		
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
	public void testInjectRefereneValue_keyIdUsageExists_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("AlAnderson@hospitalA.direct.visionshareinc.com.der");
		
		final SubjectKeyIdentifierExtensionField field = new SubjectKeyIdentifierExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		assertEquals("e0f63ccfeb5ce3eef5c04efe8084c92bc628682c", field.getPolicyValue().getPolicyValue());
		
	}	
	
	@Test
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final SubjectKeyIdentifierExtensionField field = new SubjectKeyIdentifierExtensionField(true);
		
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
