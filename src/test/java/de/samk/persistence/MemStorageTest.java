/*
 * MemStorageTest.java
 * 
 * 31.07.2016
 * 
 */
package de.samk.persistence;

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
import de.samk.data.Transaction;
import de.samk.exception.TransactionNotFoundException;

/**
 * A test class for storage bean
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 31.07.2016 - created.
 */
public class MemStorageTest {

	private MemStorageBean memStorageBean;
	private Transaction transaction1;
	private Transaction transaction2;
	private static final int TRANSACTION_ID_11 = 11;
	private static final int TRANSACTION_ID_12 = 12;

	@Before
	public void init() throws Exception {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		Context context = new InitialContext(props);
		memStorageBean = (MemStorageBean) context.lookup("MemStorageBeanLocalBean");
		assertNotNull(memStorageBean);
		transaction1 = new Transaction(10000, "cars");
		transaction2 = new Transaction(15000, "shopping", TRANSACTION_ID_11);
		memStorageBean.store(TRANSACTION_ID_11, transaction1);
		memStorageBean.store(TRANSACTION_ID_12, transaction2);

	}

	@Test
	public void testNormalStoreTransaction() {
		Transaction transaction3 = new Transaction(10000, "food");
		memStorageBean.store(13, transaction3);
	}

	@Test
	public void testStoreNullTransaction() {
		try {
			memStorageBean.store(13, null);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
	}

	@Test
	public void testStoreZeroTransactionId() {
		try {
			Transaction transaction3 = new Transaction(10000, "food");
			memStorageBean.store(0, transaction3);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
	}

	@Test
	public void testStoreNegativeTransactionId() {
		try {
			Transaction transaction3 = new Transaction(10000, "food");
			memStorageBean.store(-10, transaction3);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
	}

	@Test
	public void testGetAllOfTransactionsBasedOnParentId() {
		List<Transaction> response = null;
		response = memStorageBean.getAllTransactionsWithParentId(11);
		assertNotNull(response);
		assertEquals(1, response.size());
	}

	@Test
	public void testGetAllTransactionsBasedOnZeroParentId() {
		List<Transaction> response = null;
		try {
			response = memStorageBean.getAllTransactionsWithParentId(0);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
		assertNull(response);
	}

	@Test
	public void testGetAllTransactionsBasedOnNegativeParentId() {
		List<Transaction> response = null;
		try {
			response = memStorageBean.getAllTransactionsWithParentId(-10);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
		assertNull(response);

	}

	@Test
	public void testGetListOfTransactionIdsByType() {
		List<Integer> response;
		response = memStorageBean.getTransactionsByType("cars");
		assertNotNull(response);
		assertEquals(1, response.size());
	}

	@Test
	public void testGetListOfTransactionIdsByNullType() {
		List<Integer> response = null;
		try {
			response = memStorageBean.getTransactionsByType(null);

		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
			assertNull(response);
		}
	}

	@Test
	public void testGetListOfTransactionIdsByEmptyType() {
		List<Integer> response = null;
		try {
			response = memStorageBean.getTransactionsByType("");

		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
			assertNull(response);
		}
	}

	@Test
	public void testGetListOfTransactionIdsByNonExistingType() throws TransactionNotFoundException {
		List<Integer> list = memStorageBean.getTransactionsByType("market");
		assertNotNull(list);
		assertEquals(0, list.size());
	}

	@Test
	public void testGetTransactionsBasedOnId() {
		Transaction response = null;
		response = memStorageBean.getTransactionById(11);
		assertNotNull(response);
		assertEquals(10000D, response.getAmount(), 0.0);
		assertEquals("cars", response.getType());
		assertEquals(0L, response.getParentId(), 0.0);

	}

	@Test
	public void testGetTransactionsBasedOnZeroId() {
		Transaction response = null;
		try {
			response = memStorageBean.getTransactionById(0);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
		assertNull(response);
	}

	@Test
	public void testGetTransactionsBasedOnNegativeId() {
		Transaction response = null;
		try {
			response = memStorageBean.getTransactionById(-10);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
		assertNull(response);
	}
}
