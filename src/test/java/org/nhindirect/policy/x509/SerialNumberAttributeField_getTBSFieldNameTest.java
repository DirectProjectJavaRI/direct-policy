package org.nhindirect.policy.x509;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SerialNumberAttributeField_getTBSFieldNameTest 
{
	@Test
	public void testGetFieldName_assertName()
	{
		final SerialNumberAttributeField field = new SerialNumberAttributeField();
		assertEquals(TBSFieldName.SERIAL_NUMBER, field.getFieldName());
	}
}
