/*
 * TransactionServiceTest.java
 * 
 * 30.07.2016
 * 
 */
package de.samk.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.Test;

import de.samk.api.TransactionService;
import de.samk.data.CalculationData;
import de.samk.data.ResponseState;
import de.samk.data.Transaction;
import de.samk.exception.TransactionNotFoundException;

/**
 * TransactionService Test class.
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 29.07.2016 - created.
 */
public class TransactionServiceTest {
	private TransactionService transactionService;
	private Transaction transaction1;
	private Transaction transaction2;
	private static final int TRANSACTION_ID_11 = 11;
	private static final int TRANSACTION_ID_12 = 12;

	@Before
	public void init() throws Exception {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		Context context = new InitialContext(props);
		transactionService = (TransactionService) context.lookup("TransactionServiceBeanLocalBean");
		assertNotNull(transactionService);
		transaction1 = new Transaction(10000, "cars");
		transaction2 = new Transaction(15000, "shopping", TRANSACTION_ID_11);
		transactionService.storeTransaction(TRANSACTION_ID_11, transaction1);
		transactionService.storeTransaction(TRANSACTION_ID_12, transaction2);

	}

	@Test(expected = TransactionNotFoundException.class)
	public void testGetNonExistingTransaction() throws TransactionNotFoundException {
		transactionService.getTransactionById(100);
	}

	@Test
	public void testGetNegativeTransactionId() {
		try {
			transactionService.getTransactionById(-10);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
	}

	@Test
	public void testGetZeroTransactionId() {

		try {
			transactionService.getTransactionById(0);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
	}

	@Test
	public void testStoreTransaction() {
		Transaction t3 = new Transaction(1000, "food");
		ResponseState response = transactionService.storeTransaction(10, t3);
		assertNotNull(response);
		assertEquals("OK", response.getStatus());
	}

	@Test
	public void testStoreNullTransaction() {
		ResponseState response = null;
		try {
			Transaction t3 = null;
			response = transactionService.storeTransaction(13, t3);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
		assertNull(response);
	}

	@Test
	public void testStoreZeroTransactionId() {
		ResponseState response = null;
		try {
			Transaction t3 = new Transaction(1000, "food");
			response = transactionService.storeTransaction(0, t3);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
		assertNull(response);
	}

	@Test
	public void testStoreZeroTransactionIdAndNullTransaction() {
		ResponseState response = null;
		try {
			Transaction t3 = null;
			response = transactionService.storeTransaction(0, t3);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
		assertNull(response);
	}

	@Test
	public void testGetSumOfTransactionsBasedOnParentId() {
		CalculationData response = null;
		try {
			response = transactionService.getTransactionSumByParentId(10);
			assertNotNull(response);
			assertEquals(25000D, response.getSum(), 0.0);
		} catch (TransactionNotFoundException e) {
			assertNull(response);
		}

	}

	@Test
	public void testGetSumOfTransactionsBasedOnZeroParentId() {
		CalculationData response = null;
		try {
			response = transactionService.getTransactionSumByParentId(0);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
		assertNull(response);
	}

	@Test
	public void testGetSumOfTransactionsBasedOnNegativeParentId() {
		CalculationData response = null;
		try {
			response = transactionService.getTransactionSumByParentId(-10);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
		assertNull(response);

	}

	@Test
	public void testGetListOfTransactionIdsByType() {
		List<Integer> response;
		try {
			response = transactionService.getTransactionsByType("cars");
			assertNotNull(response);
			assertEquals(1, response.size());
		} catch (Exception e) {
		}
	}

	@Test
	public void testGetListOfTransactionIdsByNullType() {
		List<Integer> response = null;
		try {
			response = transactionService.getTransactionsByType(null);

		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
			assertNull(response);
		}
	}

	@Test
	public void testGetListOfTransactionIdsByEmptyType() {
		List<Integer> response = null;
		try {
			response = transactionService.getTransactionsByType("");

		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
			assertNull(response);
		}
	}

	@Test(expected = TransactionNotFoundException.class)
	public void testGetListOfTransactionIdsByNonExistingType() throws TransactionNotFoundException {
		transactionService.getTransactionsByType("market");
	}

	@Test
	public void testGetTransactionsBasedOnId() {
		Transaction response = null;
		try {
			response = transactionService.getTransactionById(10);
			assertNotNull(response);
			assertEquals(10000D, response.getAmount(), 0.0);
			assertEquals("cars", response.getType());
			assertEquals(0L, response.getParentId(), 0.0);
		} catch (TransactionNotFoundException e) {
			assertNull(response);
		}

	}

	@Test
	public void testGetTransactionsBasedOnZeroId() {
		Transaction response = null;
		try {
			response = transactionService.getTransactionById(0);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
		assertNull(response);
	}

	@Test
	public void testGetTransactionsBasedOnNegativeId() {
		Transaction response = null;
		try {
			response = transactionService.getTransactionById(-10);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
		assertNull(response);
	}
}