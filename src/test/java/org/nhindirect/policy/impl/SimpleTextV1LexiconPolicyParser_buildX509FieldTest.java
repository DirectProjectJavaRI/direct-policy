package org.nhindirect.policy.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.nhindirect.policy.PolicyParseException;
import org.nhindirect.policy.x509.SignatureAlgorithmField;
import org.nhindirect.policy.x509.X509Field;

public class SimpleTextV1LexiconPolicyParser_buildX509FieldTest
{
	@Test
	public void testBuildX509Field_signatureAlgorithm_assertBuilt() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
		
		final X509Field<?> field = X509Field.class.cast(parser.buildX509Field("X509.Algorithm"));
		assertNotNull(field);
		assertTrue(field instanceof SignatureAlgorithmField);
	}
	
	@Test
	public void testBuildX509Field_signature_assertFieldNotImplemented() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();

		boolean exceptionOccured = false;
		
		try
		{
			X509Field.class.cast(parser.buildX509Field("X509.Signature"));
		}
		catch (PolicyParseException e)
		{
			exceptionOccured = true;
		}
		
		assertTrue(exceptionOccured);
	}
	
	@Test
	public void testBuildX509Field_unknownField_assertNull() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
		
		final X509Field<?> field = X509Field.class.cast(parser.buildX509Field("X509.Bogus"));
		assertNull(field);
	}
}
