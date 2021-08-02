package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.security.cert.X509Certificate;

import org.nhindirect.policy.util.TestUtils;

public class SerialNumberAttributeField_injectReferenceValueTest
{
	@Test
	public void testInjectRefereneValue_assertId() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("umesh.der");
		
		final SerialNumberAttributeField field = new SerialNumberAttributeField();
		
		field.injectReferenceValue(cert);
		
		assertEquals("f74f1c4fe4e1762e", field.getPolicyValue().getPolicyValue());
		
	}	
	
	@Test
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final SerialNumberAttributeField field = new SerialNumberAttributeField();
		
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
