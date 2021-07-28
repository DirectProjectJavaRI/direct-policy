package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.security.cert.X509Certificate;
import java.util.Collection;

import org.nhindirect.policy.PolicyRequiredException;
import org.nhindirect.policy.util.TestUtils;

public class SubjectAltNameExtensionField_injectReferenceValueTest
{
	@Test
	public void testInjectRefereneValue_subjectAltNameDoesNotExist_notRequired_assertEmptyCollection() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("dsa1024.der");
		
		final SubjectAltNameExtensionField field = new SubjectAltNameExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		assertEquals(0, field.getPolicyValue().getPolicyValue().size());
	}
	
	@Test
	public void testInjectRefereneValue_subjectAltNameDoesNotExist_required_assertException() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("dsa1024.der");
		
		final SubjectAltNameExtensionField field = new SubjectAltNameExtensionField(true);
		
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
	public void testInjectRefereneValue_subjectAltNameExists_rfc822Name_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("AlAnderson@hospitalA.direct.visionshareinc.com.der");
		
		final SubjectAltNameExtensionField field = new SubjectAltNameExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		Collection<String> names = field.getPolicyValue().getPolicyValue();
		
		assertEquals(1, names.size());
		assertEquals("rfc822:AlAnderson@hospitalA.direct.visionshareinc.com", names.iterator().next());
		
	}
	
	@Test
	public void testInjectRefereneValue_subjectAltNameExists_dnsName_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("cernerdemos.der");
		
		final SubjectAltNameExtensionField field = new SubjectAltNameExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		Collection<String> names = field.getPolicyValue().getPolicyValue();
		
		assertEquals(1, names.size());
		assertEquals("dns:messaging.cernerdemos.com", names.iterator().next());
		
	}
	
	@Test
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final SubjectAltNameExtensionField field = new SubjectAltNameExtensionField(true);
		
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
