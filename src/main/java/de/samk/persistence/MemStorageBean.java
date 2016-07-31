/*
 * MemStorage.java
 * 
 * 29.07.2016
 * 
 */
package de.samk.persistence;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;

import de.samk.data.Transaction;

/**
 * A singleton class used to store transaction, this class can be replaced with
 * persistence layer if required.
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 30.07.2016 - created.
 */
@Singleton
public class MemStorageBean {

	private Map<Integer, Transaction> transactions = new LinkedHashMap<Integer, Transaction>();

	/**
	 * Method to store transaction into a Map.
	 * 
	 * @param id
	 *            id of transaction
	 * @param transaction
	 *            transaction dataa
	 * 
	 * @changed S.Kulkarni 30.07.2016 - created.
	 */
	public void store(int id, Transaction transaction) {
		if (id <= 0) {
			throw new IllegalArgumentException("id can not be less that or equal to zero");
		}
		if (transaction == null) {
			throw new IllegalArgumentException("transaction can not be null");
		}
		transactions.put(id, transaction);
	}

	/**
	 * A method to retreive transaction by id
	 * 
	 * @param id
	 *            id of transaction
	 * @return transaction data, if found. NULL otherwise.
	 * 
	 * @changed S.Kulkarni 30.07.2016 - created.
	 */
	public Transaction getTransactionById(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("id can not be less that or equal to zero");
		}
		return transactions.get(id);
	}

	/**
	 * A method to retrieve all transaction associated by Parent id.
	 * 
	 * @param id
	 *            id to be searched
	 * @return List of Transactions, empty list otherwise.
	 * 
	 * @changed S.Kulkarni 30.07.2016 - created.
	 * 
	 */
	public List<Transaction> getAllTransactionsWithParentId(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("id can not be less that or equal to zero");
		}
		List<Transaction> listOfTransactions = new ArrayList<Transaction>();
		for (Integer transactionId : transactions.keySet()) {
			Transaction transaction = transactions.get(transactionId);
			if (transaction.getParentId() == id) {
				listOfTransactions.add(transaction);
			}
		}
		return listOfTransactions;
	}

	/**
	 * A method to retrieve all transaction ids by type
	 * 
	 * @param type
	 *            type to be searched
	 * @return List of transaction ids, empty list otherwise
	 * 
	 * @changed S.Kulkarni 30.07.2016 - created.
	 */
	public List<Integer> getTransactionsByType(String type) {
		if (type == null || type.length() < 0) {
			throw new IllegalArgumentException("type can not be null or empty");
		}
		List<Integer> listOfTransactions = new ArrayList<Integer>();
		for (Integer transactionId : transactions.keySet()) {
			Transaction transaction = transactions.get(transactionId);
			if (transaction.getType().equals(type)) {
				listOfTransactions.add(transactionId);
			}
		}
		return listOfTransactions;
	}
}
