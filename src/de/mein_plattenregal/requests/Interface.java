package de.mein_plattenregal.requests;

import de.mein_plattenregal.responses.Abstract;

/**
 * main interface for requests
 * 
 * @author wolxXx
 * @version 0.1
 */
public interface Interface {
	/**
	 * run the request
	 * 
	 * @return
	 */
	public Abstract run();

	/**
	 * checks the set params
	 * 
	 * @return
	 */
	public boolean checkParams();
}
