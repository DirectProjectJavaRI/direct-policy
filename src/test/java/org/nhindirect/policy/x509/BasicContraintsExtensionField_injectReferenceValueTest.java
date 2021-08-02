package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.security.cert.X509Certificate;

import org.nhindirect.policy.util.TestUtils;
import org.nhindirect.policy.PolicyRequiredException;

public class BasicContraintsExtensionField_injectReferenceValueTest
{
	@Test
	public void testInjectReferenceValue_basicContraintNotExists_notRequired_assertFalse() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("SESTestAccount-Signiture.der");
		
		final  BasicContraintsExtensionField field = new  BasicContraintsExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		assertFalse(field.getPolicyValue().getPolicyValue());
	}
	
	@Test
	public void testInjectReferenceValue_basicContraintNotExists_required_assertException() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("SESTestAccount-Signiture.der");
		
		final  BasicContraintsExtensionField field = new  BasicContraintsExtensionField(true);
		
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
	public void testInjectReferenceValue_basicContraintExists_assertFalse() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("cernerdemos.der");
		
		final  BasicContraintsExtensionField field = new  BasicContraintsExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		assertFalse(field.getPolicyValue().getPolicyValue());
	}

	@Test
	public void testInjectReferenceValue_basicContraintExists_assertTrue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("msanchor.der");
		
		final BasicContraintsExtensionField field = new BasicContraintsExtensionField(false);
		
		field.injectReferenceValue(cert);
		
		assertTrue(field.getPolicyValue().getPolicyValue());
		
	}	
	
	@Test
	public void testInjectReferenceValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final BasicContraintsExtensionField field = new BasicContraintsExtensionField(true);
		
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
