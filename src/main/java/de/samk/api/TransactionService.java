/*
 * TransactionService.java
 * 
 * 29.07.2016
 * 
 */
package de.samk.api;

import java.util.List;

import javax.ejb.Local;

import de.samk.data.CalculationData;
import de.samk.data.ResponseState;
import de.samk.data.Transaction;
import de.samk.exception.TransactionNotFoundException;

/**
 * A Transaction Service API which provides methods to work on transactions.
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 29.07.2016 - created.
 */
@Local
public interface TransactionService {

	/**
	 * 
	 * Method to store transaction
	 * 
	 * @changed S.Kulkarni 29.07.2016 - created.
	 * 
	 * @param id
	 *            to be stored
	 * @param transaction
	 *            object containing information about transaction
	 * @return ResponseState "OK" if stored
	 */
	public ResponseState storeTransaction(int id, Transaction transaction);

	/**
	 * Method to retrieve transaction by id
	 * 
	 * @changed S.Kulkarni 29.07.2016 - created.
	 * 
	 * @param id
	 *            needs to be searched
	 * @return Transaction object if found
	 * @throws TransactionNotFoundException
	 *             if transaction not found
	 */
	public Transaction getTransactionById(int id) throws TransactionNotFoundException;

	/**
	 * Method to get the sum of transactions by parent id
	 * 
	 * @changed S.Kulkarni 29.07.2016 - created.
	 * 
	 * @param id
	 *            needs to be searched
	 * @return CalculationData object if found which stores the calculation
	 * @throws TransactionNotFoundException
	 *             if transaction not found
	 */
	public CalculationData getTransactionSumByParentId(int id) throws TransactionNotFoundException;

	/**
	 * Method to retrieve all transaction ids by types
	 * 
	 * @changed S.Kulkarni 29.07.2016 - created.
	 * 
	 * @param type
	 *            needs to be searched
	 * @return List<Integer> list of transaction ids if found
	 * @throws TransactionNotFoundException
	 *             if transactions not found
	 */
	public List<Integer> getTransactionsByType(String type) throws TransactionNotFoundException;
}
