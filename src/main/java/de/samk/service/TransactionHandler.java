/*
 * TransactionHandler.java
 * 
 * 29.07.2016
 * 
 */
package de.samk.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.samk.api.TransactionService;
import de.samk.data.CalculationData;
import de.samk.data.ResponseState;
import de.samk.data.Transaction;
import de.samk.exception.TransactionNotFoundException;

/**
 * A request Handler class which handles GET, PUT requests. The class defines
 * which HTTP methods should be handled.
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 29.07.2016 - created.
 */
@Stateless
@Path("/transactionservice")
public class TransactionHandler {

	private Logger LOGGER = Logger.getLogger(getClass().getName());

	@EJB
	private TransactionService transactionService;

	/**
	 * A method to store transaction based on id and transation data. URI :
	 * /rest/transactionservice/transaction/{id}{"amount":20000,"type":"cars",
	 * "parent_id",10}
	 * 
	 * @param id
	 *            id to be stored
	 * @param transaction
	 *            transaction to be stored
	 * @return returns JSON with Status data
	 * 
	 * @changed S.Kulkarni 29.07.2016 - created.
	 */
	@PUT
	@Path("/transaction/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTransaction(@PathParam("id") int id, Transaction transaction) {
		LOGGER.log(Level.INFO, "addTransaction called");
		ResponseState response = transactionService.storeTransaction(id, transaction);
		return Response.status(Status.OK).entity(response).build();
	}

	/**
	 * A method to retrieve transaction based on id. URI :
	 * /rest/transactionservice/transaction/{id}
	 * 
	 * @param id
	 *            id to be searched
	 * @return returns JSON with Transaction data, else response string with
	 *         error
	 * 
	 * @changed S.Kulkarni 29.07.2016 - created.
	 */
	@GET
	@Path("/transaction/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransactionById(@PathParam("id") int id) {
		LOGGER.log(Level.INFO, "getTransactionById called");
		Transaction transac;
		try {
			transac = transactionService.getTransactionById(id);
			return Response.status(Status.OK).entity(transac).build();
		} catch (TransactionNotFoundException e) {
			LOGGER.log(Level.WARNING, "TransactionNotFoundException", e);
		}
		ResponseState response = new ResponseState("Transaction not found");
		return Response.status(Status.OK).entity(response).build();
	}

	/**
	 * A method to retrieve transactions based on types. URI :
	 * /rest/transactionservice/types/{type}
	 * 
	 * @param type
	 *            type to be searched
	 * @return returns JSON with List of Transaction ids, else response string
	 *         with error
	 * 
	 * @changed S.Kulkarni 29.07.2016 - created.
	 */
	@GET
	@Path("/types/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransactionsByType(@PathParam("type") String type) {
		LOGGER.log(Level.INFO, "getTransactionsByType called");
		List<Integer> transactions;
		try {
			transactions = transactionService.getTransactionsByType(type);
			return Response.status(Status.OK).entity(transactions).build();
		} catch (TransactionNotFoundException e) {
			LOGGER.log(Level.WARNING, "TransactionNotFoundException", e);
		}
		ResponseState response = new ResponseState("Type not found");
		return Response.status(Status.OK).entity(response).build();
	}

	/**
	 * A method to retrieve transactions sum based on parent Id. URI :
	 * /rest/transactionservice/sum/{id}
	 * 
	 * @param id
	 *            id to be searched
	 * @return returns JSON with SUM, else response string with error
	 * 
	 * @changed S.Kulkarni 29.07.2016 - created.
	 */
	@GET
	@Path("/sum/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransactionsSum(@PathParam("id") int id) {
		LOGGER.log(Level.INFO, "getTransactionsSum called");
		try {
			CalculationData sum = transactionService.getTransactionSumByParentId(id);
			return Response.status(Status.OK).entity(sum).build();
		} catch (TransactionNotFoundException e) {
			LOGGER.log(Level.WARNING, "TransactionNotFoundException", e);
		}
		ResponseState response = new ResponseState("Transaction not found");
		return Response.status(Status.OK).entity(response).build();
	}
}