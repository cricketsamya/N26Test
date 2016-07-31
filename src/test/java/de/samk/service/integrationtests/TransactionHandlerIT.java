/*
 * TransactionHandlerIT.java
 * 
 * 29.07.2016
 * 
 */
package de.samk.service.integrationtests;

import static com.jayway.restassured.RestAssured.expect;
import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import de.samk.data.Transaction;

/**
 * Integration test for Transaction Handler
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 29.07.2016 - created.
 */
public class TransactionHandlerIT {
	@Before
	public void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8989;
		RestAssured.basePath = "/SimpleRESTService";
	}

	@Test
	public void testStoreTransaction() {
		Response response = expect().statusCode(200).given().contentType("application/json")
				.body(new Transaction(10000, "cars")).when().put("/rest/transactionservice/transaction/10");
		assertEquals("{\"status\":\"OK\"}", response.getBody().asString().trim());
	}

	@Test
	public void testStoreParentIdTransaction() {
		Response response = expect().statusCode(200).given().contentType("application/json")
				.body(new Transaction(15000, "shopping", 10)).when().put("/rest/transactionservice/transaction/11");
		assertEquals("{\"status\":\"OK\"}", response.getBody().asString().trim());
	}

	@Test
	public void testGetTransaction() {
		Response response = expect().statusCode(200).given().contentType("application/json")
				.body(new Transaction(10000, "cars")).when().put("/rest/transactionservice/transaction/10");
		assertEquals(response.getBody().asString().trim(), "{\"status\":\"OK\"}");
		response = expect().statusCode(200).contentType(ContentType.JSON).when()
				.get("/rest/transactionservice/transaction/10");
		assertEquals("{\"amount\":10000.0,\"type\":\"cars\",\"parent_id\":0}", response.getBody().asString().trim());
	}

	@Test
	public void testGetNonExistingTransaction() {
		Response response = expect().statusCode(200).contentType(ContentType.JSON).when()
				.get("/rest/transactionservice/transaction/199");
		assertEquals("{\"status\":\"Transaction not found\"}", response.getBody().asString().trim());
	}

	@Test
	public void testGetTransactionsSum() throws JSONException {
		Response response = expect().statusCode(200).given().contentType("application/json")
				.body(new Transaction(10000, "cars")).when().put("/rest/transactionservice/transaction/10");
		assertEquals(response.getBody().asString().trim(), "{\"status\":\"OK\"}");
		response = expect().statusCode(200).given().contentType("application/json")
				.body(new Transaction(15000, "shopping", 10)).when().put("/rest/transactionservice/transaction/11");
		assertEquals(response.getBody().asString().trim(), "{\"status\":\"OK\"}");
		response = expect().statusCode(200).contentType(ContentType.JSON).when().get("/rest/transactionservice/sum/10");
		JSONAssert.assertEquals("{\"sum\":25000}", response.asString(), false);
	}

	@Test
	public void testGetTransactionsByType() throws JSONException {
		Response response = expect().statusCode(200).given().contentType("application/json")
				.body(new Transaction(10000, "cars")).when().put("/rest/transactionservice/transaction/10");
		assertEquals(response.getBody().asString().trim(), "{\"status\":\"OK\"}");
		response = expect().statusCode(200).given().contentType("application/json")
				.body(new Transaction(15000, "shopping", 10)).when().put("/rest/transactionservice/transaction/11");
		assertEquals(response.getBody().asString().trim(), "{\"status\":\"OK\"}");
		response = expect().statusCode(200).contentType(ContentType.JSON).when()
				.get("/rest/transactionservice/types/cars");
		JSONAssert.assertEquals("[10]", response.asString(), false);
	}
}