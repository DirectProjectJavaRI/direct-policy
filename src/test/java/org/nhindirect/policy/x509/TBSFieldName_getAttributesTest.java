package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TBSFieldName_getAttributesTest
{
	@Test
	public void testGetAttributes_getRFCName()
	{
		assertEquals("Version", TBSFieldName.VERSION.getRfcName());
		assertEquals("SerialNumber", TBSFieldName.SERIAL_NUMBER.getRfcName());
	}
	
	@Test
	public void testGetAttributes_getDisplay()
	{
		assertEquals("Version", TBSFieldName.VERSION.getDisplay());
		assertEquals("Serial Number", TBSFieldName.SERIAL_NUMBER.getDisplay());
	}
	
	@Test
	public void testGetAttributes_toString()
	{
		assertEquals("Version", TBSFieldName.VERSION.toString());
		assertEquals("SerialNumber", TBSFieldName.SERIAL_NUMBER.toString());
	}
	
}
