package de.mein_plattenregal.requests;

import org.json.simple.parser.JSONParser;

import android.util.Log;
import android.widget.Toast;

/**
 * listing artists
 * 
 * @author wolxXx
 * @version 0.1
 */
public class VinylsForArtist extends de.mein_plattenregal.requests.Abstract {
	/**
	 * constructor
	 * 
	 * @param caller
	 */
	public VinylsForArtist(de.mein_plattenregal.logic.Activity caller) {
		super(caller);
	}

	/**
	 * the artist to search for
	 */
	protected String artist = null;

	/**
	 * setter for the artist
	 * 
	 * @param artist
	 * @return
	 */
	public VinylsForArtist setArtist(String artist) {
		this.artist = artist;
		return this;
	}

	@Override
	public boolean checkParams() {
		if (null == this.artist) {
			Toast.makeText(this.caller, "need artist name!!", Toast.LENGTH_LONG)
					.show();
			return false;
		}
		return true;
	}

	/**
	 * runs the request returns null if something went wrong!
	 */
	@Override
	public de.mein_plattenregal.responses.VinylsForArtist run() {
		this.setIdentAndApiKey();

		requestor.addQueryStringParam("artist", this.artist);

		String responseText = requestor
				.run(de.mein_plattenregal.config.Config.REQUEST_URI_GET_VINYLS_FOR_ARTIST);

		if (null == responseText) {
			return null;
		}

		try {
			de.mein_plattenregal.responses.VinylsForArtist responseObject = new de.mein_plattenregal.responses.VinylsForArtist();

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
					de.mein_plattenregal.responses.models.VinylForArtist vinylForArtist = new de.mein_plattenregal.responses.models.VinylForArtist(
							Integer.parseInt(object.get("id").toString()),
							object.get("title").toString());
					responseObject.addVinylForArtist(vinylForArtist);
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
