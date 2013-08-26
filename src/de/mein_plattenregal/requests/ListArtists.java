package de.mein_plattenregal.requests;

import org.json.simple.parser.JSONParser;

import android.util.Log;
import android.widget.Toast;

/**
 * listing artists
 * 
 * @author wolxXx
 * @version 1.1
 */
public class ListArtists extends de.mein_plattenregal.requests.Abstract {
	/**
	 * constructor
	 * 
	 * @param caller
	 */
	public ListArtists(de.mein_plattenregal.logic.Activity caller) {
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
	public de.mein_plattenregal.responses.ListArtists run() {
		this.setIdentAndApiKey();

		String responseText = this
				.callApi(de.mein_plattenregal.config.Config.REQUEST_URI_GET_ALL_ARTISTS);

		if (null == responseText) {
			return null;
		}

		try {
			de.mein_plattenregal.responses.ListArtists responseObject = new de.mein_plattenregal.responses.ListArtists();

			responseObject.setPlainJSON(responseText);
			responseObject.initFromJSON();

			JSONParser parser = new org.json.simple.parser.JSONParser();
			org.json.simple.JSONObject object = (org.json.simple.JSONObject) parser
					.parse(responseText);
			org.json.simple.JSONArray data = (org.json.simple.JSONArray) parser
					.parse(object.get("data").toString());
			for (int i = 0; i < data.size(); i++) {
				try {
					String current = data.get(i).toString();
					object = (org.json.simple.JSONObject) parser.parse(current);
					String artist = object.get("artist").toString();
					responseObject.addArtist(artist);
				} catch (Exception e) {
				}
			}
			return responseObject;
		} catch (Exception e) {
			Log.d("Exception", e.getMessage() + "");
			Toast.makeText(this.caller, "Fehlgeschlagen! (Level 2)",
					Toast.LENGTH_LONG).show();
			return null;
		}
	}
}
