package org.nhindirect.policy.impl.machine;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.nhindirect.policy.PolicyOperator;
import org.nhindirect.policy.PolicyProcessException;
import org.nhindirect.policy.PolicyValueFactory;
import org.nhindirect.policy.impl.BinaryBooleanPolicyOperatorExecutor;
import org.nhindirect.policy.impl.BinaryIntegerPolicyOperatorExecutor;


public class StackMachine_createOperatorExecutorTest
{
	@Test
	public void testCreateEqualsOperator() throws Exception
	{	
		StackMachine stMachine = new StackMachine();
		
		BinaryBooleanPolicyOperatorExecutor<?,?> executor = 
				BinaryBooleanPolicyOperatorExecutor.class.cast(
						stMachine.createOperatorExecutor(PolicyOperator.EQUALS, PolicyValueFactory.getInstance("12345"), PolicyValueFactory.getInstance("12345")));
		
		assertNotNull(executor);
	}
	
	@Test
	public void testCreateNotEqualsOperator() throws Exception
	{
		StackMachine stMachine = new StackMachine();
		
		BinaryBooleanPolicyOperatorExecutor<?,?> executor = 
				BinaryBooleanPolicyOperatorExecutor.class.cast(
						stMachine.createOperatorExecutor(PolicyOperator.NOT_EQUALS, PolicyValueFactory.getInstance("12345"), PolicyValueFactory.getInstance("12345")));
		
		assertNotNull(executor);
	}
	
	@Test
	public void testCreateRegExOperator() throws Exception
	{
		StackMachine stMachine = new StackMachine();
		
		BinaryBooleanPolicyOperatorExecutor<?,?> executor = 
				BinaryBooleanPolicyOperatorExecutor.class.cast(
						stMachine.createOperatorExecutor(PolicyOperator.REG_EX, PolicyValueFactory.getInstance("12345"), PolicyValueFactory.getInstance("12345")));
		
		assertNotNull(executor);
	}
	
	@Test
	public void testCreateLogicalOrOperator() throws Exception
	{
		StackMachine stMachine = new StackMachine();
		
		BinaryBooleanPolicyOperatorExecutor<?,?> executor = 
				BinaryBooleanPolicyOperatorExecutor.class.cast(
						stMachine.createOperatorExecutor(PolicyOperator.LOGICAL_OR, PolicyValueFactory.getInstance("12345"), PolicyValueFactory.getInstance("12345")));
		
		assertNotNull(executor);
	}
	
	@Test
	public void testCreateLogicalAndOperator() throws Exception
	{
		StackMachine stMachine = new StackMachine();
		
		BinaryBooleanPolicyOperatorExecutor<?,?> executor = 
				BinaryBooleanPolicyOperatorExecutor.class.cast(
						stMachine.createOperatorExecutor(PolicyOperator.LOGICAL_AND, PolicyValueFactory.getInstance("12345"), PolicyValueFactory.getInstance("12345")));
		
		assertNotNull(executor);
	}	
	
	@Test
	public void testCreateBitwiseOrOperator() throws Exception
	{		
		StackMachine stMachine = new StackMachine();
		
		BinaryIntegerPolicyOperatorExecutor<?,?> executor = 
				BinaryIntegerPolicyOperatorExecutor.class.cast(
						stMachine.createOperatorExecutor(PolicyOperator.BITWISE_OR, PolicyValueFactory.getInstance(12345), PolicyValueFactory.getInstance(12345)));
		
		assertNotNull(executor);
	}	
	
	@Test
	public void testCreateBitwiseAndOperator() throws Exception
	{		
		StackMachine stMachine = new StackMachine();
		
		BinaryIntegerPolicyOperatorExecutor<?,?> executor = 
				BinaryIntegerPolicyOperatorExecutor.class.cast(
						stMachine.createOperatorExecutor(PolicyOperator.BITWISE_AND, PolicyValueFactory.getInstance(12345), PolicyValueFactory.getInstance(12345)));
		
		assertNotNull(executor);
	}	
	
	@Test
	public void testCreateBinaryOperator_tooFewArguments_assertExecption() throws Exception
	{		
		StackMachine stMachine = new StackMachine();
		
		boolean exceptionOccured = false;
		
		try
		{
			stMachine.createOperatorExecutor(PolicyOperator.LOGICAL_AND, PolicyValueFactory.getInstance(12345));
		}
		catch (PolicyProcessException e)
		{
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}
	
	@Test
	public void testCreateUnaryOperator_tooFewArguments_assertExecption() throws Exception
	{		
		StackMachine stMachine = new StackMachine();
		
		boolean exceptionOccured = false;
		
		try
		{
			stMachine.createOperatorExecutor(PolicyOperator.LOGICAL_NOT);
		}
		catch (PolicyProcessException e)
		{
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}	
		
}
