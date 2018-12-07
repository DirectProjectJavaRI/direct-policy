package org.nhindirect.policy.scenarios;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.security.cert.X509Certificate;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.nhindirect.policy.PolicyExpression;
import org.nhindirect.policy.PolicyFilter;
import org.nhindirect.policy.PolicyFilterFactory;
import org.nhindirect.policy.PolicyLexicon;
import org.nhindirect.policy.PolicyLexiconParserFactory;
import org.nhindirect.policy.util.TestUtils;

public class RealWorldScenarioPolicyTests
{
	/*
	@Test
	public void testCertificateContainsPolicy() throws Exception
	{
		final InputStream str = IOUtils.toInputStream("(X509.TBS.EXTENSION.CertificatePolicies.PolicyOIDs {?} 1.3.6.1.4.1.41179.2.1)");
		
		final PolicyExpression exp = PolicyLexiconParserFactory.getInstance(PolicyLexicon.SIMPLE_TEXT_V1).parse(str);
		final PolicyFilter filter = PolicyFilterFactory.getInstance();
		final X509Certificate cert = TestUtils.loadCertificate("SampleCert.crt");
		
		assertTrue(filter.isCompliant(cert, exp));
	}
	*/
	@Test
	public void testCertificateContainsPolicyAndKeyUsage() throws Exception
	{
		final InputStream str = FileUtils.openInputStream(new File("./src/test/resources/policies/interopTestCertPolicy.pol"));
				
		
		final PolicyExpression exp = PolicyLexiconParserFactory.getInstance(PolicyLexicon.SIMPLE_TEXT_V1).parse(str);
		final PolicyFilter filter = PolicyFilterFactory.getInstance();
		final X509Certificate cert = TestUtils.loadCertificate("SampleCert.crt");
		
		assertTrue(filter.isCompliant(cert, exp));
	}
}
