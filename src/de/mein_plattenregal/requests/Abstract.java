package de.mein_plattenregal.requests;

import android.widget.Toast;

/**
 * the abstract class that contains all needed stuff
 * 
 * @author wolxXx
 * @version 0.1
 */
public abstract class Abstract implements
		de.mein_plattenregal.requests.Interface {
	/**
	 * the calling activity
	 */
	public de.mein_plattenregal.logic.Activity caller = null;

	/**
	 * requestor instance
	 */
	public de.mein_plattenregal.logic.Requestor requestor = null;

	/**
	 * constructor
	 * 
	 * @param caller
	 */
	public Abstract(de.mein_plattenregal.logic.Activity caller) {
		this.caller = caller;
		this.requestor = new de.mein_plattenregal.logic.Requestor(this.caller);
	}

	protected final String callApi(String request) {
		if (false == this.checkParams()) {
			return null;
		}
		String responseText = requestor.run(request);
		if (null == responseText | "" == responseText) {
			Toast.makeText(this.caller, "Fehlgeschlagen! (Level 1)",
					Toast.LENGTH_LONG).show();
			return null;
		}
		return responseText;
	}

	/**
	 * sets the needed ident and api key
	 * 
	 * @param requestor
	 */
	public void setIdentAndApiKey() {
		String key = "apikey";
		String value = de.mein_plattenregal.config.Handler.factory(this.caller)
				.get(de.mein_plattenregal.config.Config.KEY_APIKEY);
		requestor.addQueryStringParam(key, value);

		key = "ident";
		value = de.mein_plattenregal.config.Handler.factory(this.caller).get(
				de.mein_plattenregal.config.Config.KEY_IDENT);
		requestor.addQueryStringParam(key, value);
	}
}