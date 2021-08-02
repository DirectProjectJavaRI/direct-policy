package org.nhindirect.policy.util;


import java.io.File;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


import org.apache.commons.io.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class TestUtils 
{
	static
	{
		Security.addProvider(new BouncyCastleProvider());
	}	
	
	// base directory for test certificates
	private static final String certBasePath = "src/test/resources/certs/"; 
	
	
	public static X509Certificate loadCertificate(String certFileName) throws Exception
	{
		File fl = new File(certBasePath + certFileName);
		
		X509Certificate retVal = (X509Certificate) CertificateFactory.getInstance("X.509", "BC").generateCertificate(FileUtils.openInputStream(fl));     
		
		return retVal;
	}	
	
}
