/*
 * CalculationData.java
 * 
 * 29.07.2016
 * 
 */
package de.samk.data;

/**
 * Calculation Data class
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 29.07.2016 - created.
 */
public class CalculationData {

	private Double sum = 0D;

	public CalculationData() {
	}

	/**
	 * @changed S.Kulkarni 29.07.2016 - created.
	 */
	public Double getSum() {
		return sum;
	}

	/**
	 * @changed S.Kulkarni 29.07.2016 - created.
	 */
	public void add(Double value) {
		this.sum += value;
	}
}
