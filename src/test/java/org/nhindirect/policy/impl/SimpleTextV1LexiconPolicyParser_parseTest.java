package org.nhindirect.policy.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.nhindirect.policy.PolicyExpression;
import org.nhindirect.policy.PolicyGrammarException;

public class SimpleTextV1LexiconPolicyParser_parseTest
{
	@Test
	public void testParse_simpleExpression_validateParsed() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
		final InputStream stream = FileUtils.openInputStream(new File("./src/test/resources/policies/simpleLexiconSamp1.txt"));
		
		final PolicyExpression expressions = parser.parse(stream);

		assertNotNull(expressions);
		
		stream.close();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testParse_unclosedGroup_assertGrammarException() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
		final InputStream stream = IOUtils.toInputStream("(1 = 1");
		
		boolean exceptionOccured = false;
		
		try
		{
			parser.parse(stream);
		}
		catch (PolicyGrammarException e)
		{
			exceptionOccured = true;
		}

		assertTrue(exceptionOccured);
		
	}	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testParse_noOperator_assertGrammarException() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
		final InputStream stream = IOUtils.toInputStream("1");
		
		boolean exceptionOccured = false;
		
		try
		{
			parser.parse(stream);
		}
		catch (PolicyGrammarException e)
		{
			exceptionOccured = true;
		}

		assertTrue(exceptionOccured);
		
	}	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testParse_extraniousOperator_assertGrammarException() throws Exception
	{
		final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
		final InputStream stream = IOUtils.toInputStream("1 = 1 =");
		
		boolean exceptionOccured = false;
		
		try
		{
			parser.parse(stream);
		}
		catch (PolicyGrammarException e)
		{
			exceptionOccured = true;
		}

		assertTrue(exceptionOccured);
		
	}	
}
