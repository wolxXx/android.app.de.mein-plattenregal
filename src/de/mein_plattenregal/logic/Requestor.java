package de.mein_plattenregal.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.util.Log;

/**
 * 
 * @author wolxXx
 * @version 1.0
 */
public class Requestor {
	/**
	 * the base URL
	 */
	protected String baseURL;

	/**
	 * the resource that shall be called
	 */
	protected String resource;

	/**
	 * the calling activity
	 */
	protected de.mein_plattenregal.logic.Activity caller;

	/**
	 * the query string that will be wrapped to post request
	 */
	protected String queryString = "";

	/**
	 * adds a key value pair to the query string
	 * 
	 * @param key
	 * @param value
	 */
	public void addQueryStringParam(String key, String value) {
		String queryStringSave = this.queryString;
		try {
			String prefix = "" == queryStringSave ? "" : "&";
			value = URLEncoder.encode(value, "UTF-8");
			this.queryString += prefix + key + "=" + value;
		} catch (Exception e) {
			this.queryString = queryStringSave;
		}
	}

	/**
	 * constructor
	 * 
	 * @param caller
	 */
	public Requestor(de.mein_plattenregal.logic.Activity caller) {
		this.caller = caller;
		this.init();
	}

	/**
	 * Initializes this class
	 */
	protected void init() {
		this.setBaseUrl(de.mein_plattenregal.config.Handler
				.factory(this.caller).get(
						de.mein_plattenregal.config.Config.KEY_URL,
						de.mein_plattenregal.config.Config
								.getHostName(this.caller)));
		this.setResource(de.mein_plattenregal.config.Config.getResource());
	}

	/**
	 * setter for the base URL
	 * 
	 * @param url
	 */
	public void setBaseUrl(String url) {
		this.baseURL = url;
	}

	/**
	 * setter for the resource
	 * 
	 * @param resource
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	/**
	 * runs the request. returns the whole plain JSON response text. returns
	 * null if the request as a HTTP request failed.
	 * 
	 * @param request
	 * @return String
	 */
	public String run(String request) {
		try {
			String urlString = this.baseURL + this.resource + request;
			Log.d("CALLING URL", urlString);
			URL url = new URL(urlString);
			Log.d("alive", "1");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(150);
			Log.d("alive", "2");
			connection.setRequestMethod("POST");
			Log.d("alive", "3");
			connection.setDoInput(true);
			Log.d("alive", "4");
			connection.setDoOutput(true);
			Log.d("alive", "5");
			connection.setUseCaches(false);
			Log.d("alive", "6");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			Log.d("alive", "7");
			String body = this.queryString;
			connection.setRequestProperty("Content-Length",
					String.valueOf(body.length()));
			Log.d("alive", "8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			Log.d("alive", "9");
			writer.write(body);
			Log.d("alive", "10");
			writer.flush();
			Log.d("alive", "11");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			Log.d("alive", "12");
			String response = "";
			Log.d("alive", "13");
			for (String line; (line = reader.readLine()) != null;) {
				Log.d("alive", "14");
				response += line;
				Log.d("alive", "15");
				Log.d("current json response line", response);
			}
			Log.d("json text", response);
			Log.d("alive", "16");
			writer.close();
			Log.d("alive", "17");
			reader.close();
			Log.d("alive", "18");
			return response;
		} catch (IOException e) {
			Log.d("run exception", e.getMessage() + ":");
		}
		return null;
	}
}
