package de.mein_plattenregal.requests;

import org.json.simple.parser.JSONParser;

import android.util.Log;
import android.widget.Toast;

/**
 * activating devices needs ident and init key for sending request!
 * 
 * @author wolxXx
 * @version 0.1
 */
public class ActivateDevice extends de.mein_plattenregal.requests.Abstract {
	/**
	 * constructor
	 * 
	 * @param caller
	 */
	public ActivateDevice(de.mein_plattenregal.logic.Activity caller) {
		super(caller);
	}

	/**
	 * the device identifier
	 */
	protected String ident = null;

	/**
	 * the set up / init key
	 */
	protected String initkey = null;

	/**
	 * setter for the device identifying string
	 * 
	 * @param ident
	 */
	public ActivateDevice setIdent(String ident) {
		this.ident = ident;
		return this;
	}

	/**
	 * setter for the set up / init string
	 * 
	 * @param initkey
	 * @return
	 */
	public ActivateDevice setInitkey(String initkey) {
		this.initkey = initkey;
		return this;
	}

	@Override
	public boolean checkParams() {
		if (null == this.ident || null == this.initkey || "" == this.ident
				|| "" == this.initkey) {
			Toast.makeText(this.caller, "need init key and ident!!",
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	/**
	 * runs the request returns null if something went wrong!
	 */
	@Override
	public de.mein_plattenregal.responses.ActivateDevice run() {
		this.requestor.addQueryStringParam("initkey", this.initkey);
		this.requestor.addQueryStringParam("ident", this.ident);

		String responseText = this
				.callApi(de.mein_plattenregal.config.Config.REQUEST_URI_ACTIVATE_DEVICE);
		if (null == responseText) {
			return null;
		}
		try {
			de.mein_plattenregal.responses.ActivateDevice responseObject = new de.mein_plattenregal.responses.ActivateDevice();

			responseObject.setPlainJSON(responseText);
			responseObject.initFromJSON();

			JSONParser parser = new org.json.simple.parser.JSONParser();
			org.json.simple.JSONObject object = (org.json.simple.JSONObject) parser
					.parse(responseText);
			org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser
					.parse(object.get("data").toString());
			responseObject.setApikey(data.get("apikey").toString());
			return responseObject;
		} catch (Exception e) {
			Log.d("Exception", e.getMessage() + "");
			Toast.makeText(this.caller, "Fehlgeschlagen! (Level 2)",
					Toast.LENGTH_LONG).show();
			return null;
		}
	}
}