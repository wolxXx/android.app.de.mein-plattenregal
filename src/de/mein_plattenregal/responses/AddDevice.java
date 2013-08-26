package de.mein_plattenregal.responses;

/**
 * the response from the api call contains the api key in the data object
 * 
 * @author wolxXx
 * @version 1.1
 */
public class AddDevice extends de.mein_plattenregal.responses.Abstract {
	/**
	 * the api key
	 */
	protected String apiKey = "";

	/**
	 * getter for the api key
	 * 
	 * @return
	 */
	public String getApiKey() {
		return this.apiKey;
	}

	/**
	 * setter for the api key
	 * 
	 * @param apiKey
	 * @return
	 */
	public AddDevice setApikey(String apiKey) {
		this.apiKey = apiKey;
		return this;
	}
}