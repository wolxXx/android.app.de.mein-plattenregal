package de.mein_plattenregal.responses;

import org.json.simple.parser.*;

/**
 * mother class for responses
 * 
 * @author wolxXx
 * @version 1.1
 */
public abstract class Abstract implements de.mein_plattenregal.responses.Interface {
	/**
	 * the returned status
	 */
	protected int status;

	/**
	 * the returned message
	 */
	protected String message;

	/**
	 * has an error occurred?
	 */
	protected boolean error;

	/**
	 * the plain JSON that was sent
	 */
	protected String plainJSON;

	/**
	 * collection of data
	 */
	protected Object data;

	/**
	 * Initializes this class via the returned JSON
	 * 
	 * @return Abstract
	 * @throws ParseException
	 */
	public final Abstract initFromJSON() throws ParseException {
		JSONParser parser = new org.json.simple.parser.JSONParser();
		org.json.simple.JSONObject object = (org.json.simple.JSONObject) parser
				.parse(this.plainJSON);
		this.setMessage(object.get("message").toString());
		this.setStatus(Integer.parseInt(object.get("status").toString()));
		this.setHasError("true" == object.get("error").toString());
		return this;
	}

	/**
	 * getter for the status
	 * 
	 * @return int
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * setter for the status
	 * 
	 * @param status
	 * @return Abstract
	 */
	public Abstract setStatus(int status) {
		this.status = status;
		return this;
	}

	/**
	 * getter for the message
	 * 
	 * @return String
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * setter for the message
	 * 
	 * @param message
	 * @return Abstract
	 */
	public Abstract setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * determines if the request had an error
	 * 
	 * @return boolean
	 */
	public boolean hasError() {
		return this.error;
	}

	/**
	 * setter for the error flag
	 * 
	 * @param error
	 * @return Abstract
	 */
	public Abstract setHasError(boolean error) {
		this.error = error;
		return this;
	}

	/**
	 * setter for the plain json string
	 * 
	 * @param plainJSON
	 * @return Abstract
	 */
	public Abstract setPlainJSON(String plainJSON) {
		this.plainJSON = plainJSON;
		return this;
	}

	/**
	 * getter for the plain JSON
	 * 
	 * @return Strings
	 */
	public String getPlainJSON() {
		return this.plainJSON;
	}
}
