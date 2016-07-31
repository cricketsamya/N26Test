/*
 * Transaction.java
 * 
 * 29.07.2016
 * 
 */
package de.samk.data;

import javax.xml.bind.annotation.*;

/**
 * A Transaction data class
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 29.07.2016 - created.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {

	@XmlAttribute(name = "amount")
	private double amount;
	@XmlAttribute(name = "type")
	private String type;
	@XmlAttribute(name = "parent_id")
	private long parentId;

	public Transaction(double amount, String type) {
		if (type == null || type.length() == 0) {
			throw new IllegalArgumentException("type cannot be empty or null");
		}
		this.amount = amount;
		this.type = type;
	}

	public Transaction(double amount, String type, long parentId) {
		this(amount, type);
		if (parentId <= 0) {
			throw new IllegalArgumentException("parent id cannot be equal or less than zero");
		}
		this.parentId = parentId;
	}

	public Transaction() {
	}

	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", type=" + type + ", parent_id=" + parentId + "]";
	}

	public double getAmount() {
		return amount;
	}

	public String getType() {
		return type;
	}

	public long getParentId() {
		return parentId;
	}

}