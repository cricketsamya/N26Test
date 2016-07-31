package de.samk.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ResponseStateTest {

	@Test
	public void testNormalResponseState() {
		ResponseState r = new ResponseState();
		assertNotNull(r);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullStatus() {
		new ResponseState(null);
	}

	@Test
	public void testNormalStatus() {
		ResponseState r = new ResponseState("OK");
		assertNotNull(r);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyStatus() {
		new ResponseState("");
	}

	@Test
	public void testGetStatus() {
		ResponseState r = new ResponseState("OK");
		assertNotNull(r);
		assertEquals("OK", r.getStatus());
	}
}
