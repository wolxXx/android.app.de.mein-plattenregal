package de.mein_plattenregal.requests;

import org.json.simple.parser.JSONParser;

import android.util.Log;
import android.widget.Toast;

/**
 * retrieves the vinyl meta data
 * 
 * @author wolxXx
 * @version 1.1
 */
public class Vinyl extends de.mein_plattenregal.requests.Abstract {
	/**
	 * constructor
	 * 
	 * @param caller
	 */
	public Vinyl(de.mein_plattenregal.logic.Activity caller) {
		super(caller);
	}

	/**
	 * the id of the vinyl item
	 */
	protected int id = 0;

	/**
	 * setter for the vinyl id
	 * 
	 * @param id
	 * @return Vinyl
	 */
	public Vinyl setId(int id) {
		this.id = id;
		return this;
	}

	@Override
	public boolean checkParams() {
		if (0 == this.id) {
			Toast.makeText(this.caller, "need id of the vinyl!!",
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	/**
	 * runs the request returns null if something went wrong!
	 */
	@Override
	public de.mein_plattenregal.responses.Vinyl run() {
		this.setIdentAndApiKey();
		requestor.addQueryStringParam("id", this.id + "");

		String responseText = this
				.callApi(de.mein_plattenregal.config.Config.REQUEST_URI_GET_META_INFORMATIOM_FOR_VINYL);
		if (null == responseText) {
			return null;
		}

		try {
			de.mein_plattenregal.responses.Vinyl responseObject = new de.mein_plattenregal.responses.Vinyl();

			responseObject.setPlainJSON(responseText);
			responseObject.initFromJSON();

			JSONParser parser = new org.json.simple.parser.JSONParser();
			org.json.simple.JSONObject object = (org.json.simple.JSONObject) parser
					.parse(responseText);

			org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser
					.parse(object.get("data").toString());
			responseObject.Vinyl = new de.mein_plattenregal.responses.models.Vinyl();
			responseObject.Vinyl.id = Integer.parseInt(data.get("id")
					.toString());
			responseObject.Vinyl.created = data.get("created").toString();
			responseObject.Vinyl.year = Integer.parseInt(data.get("year")
					.toString());
			responseObject.Vinyl.artist = data.get("artist").toString();
			responseObject.Vinyl.title = data.get("title").toString();
			responseObject.Vinyl.color = data.get("color").toString();
			responseObject.Vinyl.label = data.get("label").toString();
			responseObject.Vinyl.genre = data.get("genre").toString();
			responseObject.Vinyl.description = data.get("description")
					.toString();
			responseObject.Vinyl.pieces = Integer.parseInt(data.get("pieces")
					.toString());
			responseObject.Vinyl.size = Integer.parseInt(data.get("size")
					.toString());
			responseObject.Vinyl.type = data.get("type").toString();
			responseObject.Vinyl.rpm = Integer.parseInt(data.get("rpm")
					.toString());
			return responseObject;
		} catch (Exception e) {
			Log.d("Exception", e.getMessage() + "");
			Toast.makeText(this.caller, "Fehlgeschlagen! (Level 2)",
					Toast.LENGTH_LONG).show();
			return null;
		}
	}
}
