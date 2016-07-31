/*
 * TransactionTest.java
 * 
 * 31.07.2016
 * 
 */
package de.samk.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * A test class for Transaction Data
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 31.07.2016 - created.
 */
public class TransactionTest {

	@Test
	public void testNormalTransaction() {
		Transaction t = new Transaction();
		assertNotNull(t);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testZeroTransactionParentId() {
		new Transaction(200, "test", 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeTransactionParentId() {
		new Transaction(200, "test", -9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullTypeNegativeTransactionParentId() {
		new Transaction(200, null, -9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyTypeNegativeTransactionParentId() {
		new Transaction(200, "", -9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullTypeZeroTransactionParentId() {
		new Transaction(200, null, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyTypeZeroTransactionParentId() {
		new Transaction(200, "", 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyTransactionType() {
		new Transaction(200, "", 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullTransactionType() {
		new Transaction(200, null, 10);
	}

	@Test
	public void testNegativeAmout() {
		Transaction t = new Transaction(-200, "test", 10);
		assertNotNull(t);

	}

	@Test
	public void testNormalTransactionWithValues() {
		Transaction t = new Transaction(50, "test", 10);
		assertNotNull(t);
		assertEquals(50D, t.getAmount(), 0.001);
		assertEquals(10, t.getParentId());
		assertEquals("test", t.getType());

	}

}
