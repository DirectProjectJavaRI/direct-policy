package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;

import org.nhindirect.policy.util.TestUtils;
import org.nhindirect.policy.PolicyRequiredException;

public class IssuerAttributeField_injectReferenceValueTest
{
	@Test
	public void testInjectRefereneValue_rdnAttributeDoesNotExist_notRequired_assertValueCollection() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final IssuerAttributeField field = new IssuerAttributeField(false, RDNAttributeIdentifier.INITIALS);
		
		field.injectReferenceValue(cert);
		
		final Collection<String> values = field.getPolicyValue().getPolicyValue();
		
		assertEquals(0, values.size());
		
	}
	
	@Test
	public void testInjectRefereneValue_rdnAttributeDoesNotExist_required_throwException() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final IssuerAttributeField field = new IssuerAttributeField(true, RDNAttributeIdentifier.INITIALS);
		
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
	public void testInjectRefereneValue_rdnSingleAttributeExists_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final IssuerAttributeField field = new IssuerAttributeField(true, RDNAttributeIdentifier.COMMON_NAME);
		
		field.injectReferenceValue(cert);
		
		final Collection<String> values = field.getPolicyValue().getPolicyValue();
		
		assertEquals(1, values.size());
		
		Iterator<String> str = values.iterator();
		assertEquals("test.email.com", str.next());
	}	
	
	@Test
	public void testInjectRefereneValue_distinguishedName_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final IssuerAttributeField field = new IssuerAttributeField(true, RDNAttributeIdentifier.DISTINGUISHED_NAME);
		
		field.injectReferenceValue(cert);
		
		final Collection<String> values = field.getPolicyValue().getPolicyValue();
		
		assertEquals(1, values.size());
		
		Iterator<String> str = values.iterator();
		assertEquals("O=Cerner,L=Kansas City,ST=MO,C=US,CN=test.email.com", str.next());
	}
	
	@Test
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final IssuerAttributeField field = new IssuerAttributeField(true, RDNAttributeIdentifier.COMMON_NAME);
		
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
