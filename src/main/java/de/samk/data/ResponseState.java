/*
 * ResponseState.java
 * 
 * 29.07.2016
 * 
 */
package de.samk.data;

import javax.xml.bind.annotation.*;

/**
 * Response status POJO
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 29.07.2016 - created.
 */
@XmlRootElement
public class ResponseState {

	private String status;

	public ResponseState() {
		super();
	}

	public ResponseState(String status) {
		if (status == null || status.length() == 0) {
			throw new IllegalArgumentException("status cannot be empty or null");
		}
		this.status = status;
	}

	/**
	 * @changed S.Kulkarni 29.07.2016 - created.
	 */
	public String getStatus() {
		return status;
	}

}
