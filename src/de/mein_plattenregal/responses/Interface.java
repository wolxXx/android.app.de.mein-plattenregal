package de.mein_plattenregal.responses;

/**
 * interface for responses 
 * 
 * @author wolxXx
 * @version 1.1
 */
public interface Interface {
	/**
	 * getter for the status code
	 * 
	 * @return integer
	 */
	public int getStatus();

	/**
	 * getter for the message string
	 * 
	 * @return String
	 */
	public String getMessage();

	/**
	 * getter for the error flag
	 * 
	 * @return boolean
	 */
	public boolean hasError();

	/**
	 * getter for the plain json string
	 * 
	 * @return String
	 */
	public String getPlainJSON();
}
