package org.nhindirect.policy.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class SimpleTextV1LexiconPolicyParser_parseToTokensTest
{
	@Test
	public void testParse_simpleExpression_validateTokens() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
		final InputStream stream = FileUtils.openInputStream(new File("./src/test/resources/policies/simpleLexiconSamp1.txt"));
		
		Vector<SimpleTextV1LexiconPolicyParser.TokenTypeAssociation> tokens = parser.parseToTokens(stream);
		assertEquals(11, tokens.size());
		
		stream.close();
	}
	
	@Test
	public void testParse_logicalAndOperator_validateSingleTokens() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
		final InputStream stream = FileUtils.openInputStream(new File("./src/test/resources/policies/logicalAndOperator.txt"));
		
		Vector<SimpleTextV1LexiconPolicyParser.TokenTypeAssociation> tokens = parser.parseToTokens(stream);
		assertEquals(1, tokens.size());
		assertEquals("&&", tokens.iterator().next().getToken());
		
		stream.close();
	}	
	
	@Test
	public void testParse_CertificateStruct_validateTokens() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
		final InputStream stream = FileUtils.openInputStream(new File("./src/test/resources/policies/lexiconWithCertificateStruct.txt"));
		
		Vector<SimpleTextV1LexiconPolicyParser.TokenTypeAssociation> tokens = parser.parseToTokens(stream);
		assertEquals(3, tokens.size());
		assertEquals(SimpleTextV1LexiconPolicyParser.TokenType.CERTIFICATE_REFERENCE_EXPRESSION, tokens.iterator().next().getType());
		
		stream.close();
	}	
	
	@Test
	public void testParse_literalWithSpaces_validateTokens() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
		final InputStream stream = FileUtils.openInputStream(new File("./src/test/resources/policies/literalWithSpaces.txt"));
		
		Vector<SimpleTextV1LexiconPolicyParser.TokenTypeAssociation> tokens = parser.parseToTokens(stream);
		assertEquals(3, tokens.size());
		
		stream.close();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testParse_requiredCertField_validateTokens() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
		final InputStream stream = IOUtils.toInputStream("X509.TBS.EXTENSION.SubjectKeyIdentifier+ = 1.3.2.3");
		
		Vector<SimpleTextV1LexiconPolicyParser.TokenTypeAssociation> tokens = parser.parseToTokens(stream);
		assertEquals(3, tokens.size());
		
		stream.close();
	}	
}
