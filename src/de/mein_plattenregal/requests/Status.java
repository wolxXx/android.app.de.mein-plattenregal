package de.mein_plattenregal.requests;

import android.util.Log;
import android.widget.Toast;

/**
 * listing artists
 * 
 * @author wolxXx
 * @version 1.1
 */
public class Status extends de.mein_plattenregal.requests.Abstract {
	/**
	 * constructor
	 * 
	 * @param caller
	 */
	public Status(de.mein_plattenregal.logic.Activity caller) {
		super(caller);
	}

	@Override
	public boolean checkParams() {
		return true;
	}

	/**
	 * runs the request returns null if something went wrong!
	 */
	@Override
	public de.mein_plattenregal.responses.Status run() {
		this.setIdentAndApiKey();

		String responseText = this
				.callApi(de.mein_plattenregal.config.Config.REQUEST_URI_CHECK_DEVICE);

		if (null == responseText) {
			return null;
		}

		try {
			de.mein_plattenregal.responses.Status responseObject = new de.mein_plattenregal.responses.Status();

			responseObject.setPlainJSON(responseText);
			responseObject.initFromJSON();

			return responseObject;
		} catch (Exception e) {
			Log.d("Exception", e.getMessage() + "");
			Toast.makeText(this.caller, "Fehlgeschlagen! (Level 2)",
					Toast.LENGTH_LONG).show();
			return null;
		}
	}
}
