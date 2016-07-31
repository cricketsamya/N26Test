/*
 * TransactionServiceBean.java
 * 
 * 29.07.2016
 * 
 */
package de.samk.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response.Status;

import de.samk.api.TransactionService;
import de.samk.data.CalculationData;
import de.samk.data.ResponseState;
import de.samk.data.Transaction;
import de.samk.exception.TransactionNotFoundException;
import de.samk.persistence.MemStorageBean;

/**
 * A business class which communicates to the persistence layer.
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 29.07.2016 - created.
 */
@LocalBean
@Stateless
public class TransactionServiceBean implements TransactionService {

	@EJB
	private MemStorageBean ms;

	@Override
	public ResponseState storeTransaction(int id, Transaction transaction) {
		if (id <= 0) {
			throw new IllegalArgumentException("id can not be less that or equal to zero");
		}
		if (transaction == null) {
			throw new IllegalArgumentException("transaction can not be null");
		}
		ms.store(id, transaction);
		return new ResponseState(Status.OK.name());
	}

	@Override
	public Transaction getTransactionById(int id) throws TransactionNotFoundException {
		if (id <= 0) {
			throw new IllegalArgumentException("id can not be less that or equal to zero");
		}
		Transaction transaction = ms.getTransactionById(id);
		if (transaction != null) {
			return transaction;
		}
		throw new TransactionNotFoundException();
	}

	@Override
	public CalculationData getTransactionSumByParentId(int id) throws TransactionNotFoundException {
		if (id <= 0) {
			throw new IllegalArgumentException("id can not be less that or equal to zero");
		}
		CalculationData data = new CalculationData();
		Transaction mainTransaction = getTransactionById(id);
		if (mainTransaction != null) {
			data.add(mainTransaction.getAmount());
			List<Transaction> listOfTransactions = ms.getAllTransactionsWithParentId(id);
			if (listOfTransactions != null && listOfTransactions.size() > 0) {
				for (Transaction transaction : listOfTransactions) {
					data.add(transaction.getAmount());
				}
			}
			return data;
		}
		throw new TransactionNotFoundException();

	}

	@Override
	public List<Integer> getTransactionsByType(String type) throws TransactionNotFoundException {
		if (type == null || type.length() < 0) {
			throw new IllegalArgumentException("type can not be null or empty");
		}
		List<Integer> listOfTransactions = ms.getTransactionsByType(type);
		if (listOfTransactions != null && listOfTransactions.size() > 0) {
			return listOfTransactions;
		}
		throw new TransactionNotFoundException();
	}
}
