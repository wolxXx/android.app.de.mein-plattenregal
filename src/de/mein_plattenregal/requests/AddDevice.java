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
public class AddDevice extends de.mein_plattenregal.requests.Abstract {
	/**
	 * constructor
	 * 
	 * @param caller
	 */
	public AddDevice(de.mein_plattenregal.logic.Activity caller) {
		super(caller);
	}

	/**
	 * the device identifier
	 */
	protected String ident = null;

	/**
	 * user's email
	 */
	protected String email = null;

	/**
	 * user's password
	 */
	protected String password = null;

	/**
	 * name of the device
	 */
	protected String name = null;

	/**
	 * setter for the device identifying string
	 * 
	 * @param ident
	 */
	public AddDevice setIdent(String ident) {
		this.ident = ident;
		return this;
	}

	/**
	 * setter for the email
	 * 
	 * @param ident
	 */
	public AddDevice setEmail(String email) {
		this.email = email;
		return this;
	}

	/**
	 * setter for the password
	 * 
	 * @param ident
	 */
	public AddDevice setPassword(String password) {
		this.password = password;
		return this;
	}

	/**
	 * setter for the name of the device
	 * 
	 * @param ident
	 */
	public AddDevice setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public boolean checkParams() {
		boolean isIdentOk = null != this.ident && "" == this.ident;
		boolean isEmailOk = null != this.email && "" == this.email;
		boolean isPasswordOk = null != this.password && "" == this.password;
		boolean isNameOk = null != this.name && "" == this.name;
		if (false == (isIdentOk && isEmailOk && isPasswordOk && isNameOk)) {
			Toast.makeText(this.caller,
					"need ident, email, password and name!!", Toast.LENGTH_LONG)
					.show();
			return false;
		}
		return true;
	}

	/**
	 * runs the request returns null if something went wrong!
	 */
	@Override
	public de.mein_plattenregal.responses.AddDevice run() {
		this.requestor.addQueryStringParam("ident", this.ident);
		this.requestor.addQueryStringParam("email", this.email);
		this.requestor.addQueryStringParam("password", this.password);
		this.requestor.addQueryStringParam("name", this.name);

		String responseText = requestor
				.run(de.mein_plattenregal.config.Config.REQUEST_URI_ADD_DEVICE);
		if (null == responseText) {
			return null;
		}
		try {
			de.mein_plattenregal.responses.AddDevice responseObject = new de.mein_plattenregal.responses.AddDevice();

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