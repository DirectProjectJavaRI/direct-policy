package org.nhindirect.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.nhindirect.policy.impl.JavaSerializedObjectLexiconPolicyParser;
import org.nhindirect.policy.impl.XMLLexiconPolicyParser;

public class PolicyLexicon_getParserClassTest
{
	@Test
	public void testGetParserTestClass_assertParser()
	{
		assertEquals(JavaSerializedObjectLexiconPolicyParser.class, PolicyLexicon.JAVA_SER.getParserClass());
		
		assertEquals(XMLLexiconPolicyParser.class, PolicyLexicon.XML.getParserClass());
	}
}
