package org.nhindirect.policy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class SimpleTextV1LexiconPolicyParser_quotedEscapesTest
{
    @Test
    public void testQuotedLiteral_containsParenthesesAndOperators_isSingleLiteral() throws Exception
    {
        final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
        final InputStream stream = IOUtils.toInputStream("\"(this && that)\"", Charset.defaultCharset());

        final java.util.Vector<SimpleTextV1LexiconPolicyParser.TokenTypeAssociation> tokens = parser.parseToTokens(stream);
        assertEquals(1, tokens.size());
        assertEquals(SimpleTextV1LexiconPolicyParser.TokenType.LITERAL_EXPRESSION, tokens.get(0).getType());
        assertEquals("(this && that)", tokens.get(0).getToken());

        stream.close();
    }

    @Test
    public void testQuotedLiteral_escapedQuote_insideLiteral() throws Exception
    {
        final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
        final InputStream stream = IOUtils.toInputStream("\"He said \\\"Hello\\\" to me\"", Charset.defaultCharset());

        final java.util.Vector<SimpleTextV1LexiconPolicyParser.TokenTypeAssociation> tokens = parser.parseToTokens(stream);
        assertEquals(1, tokens.size());
        assertEquals("He said \"Hello\" to me", tokens.get(0).getToken());

        stream.close();
    }

    @Test
    public void testQuotedLiteral_escapedBackslash_insideLiteral() throws Exception
    {
        final SimpleTextV1LexiconPolicyParser parser = new SimpleTextV1LexiconPolicyParser();
        final InputStream stream = IOUtils.toInputStream("\"C:\\\\path\\to\\file\"", Charset.defaultCharset());

        final java.util.Vector<SimpleTextV1LexiconPolicyParser.TokenTypeAssociation> tokens = parser.parseToTokens(stream);
        assertEquals(1, tokens.size());
        assertEquals("C:\\path\\to\\file", tokens.get(0).getToken());

        stream.close();
    }
}

