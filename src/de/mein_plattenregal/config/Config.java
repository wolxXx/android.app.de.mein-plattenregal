package de.mein_plattenregal.config;

import android.app.Activity;

/**
 * main configuration class
 * 
 * @author wolxXx
 * @version 0.1
 */
public class Config {
	public static final String KEY_APIKEY = "apiKey";
	public static final String KEY_IDENT = "ident";
	public static final String KEY_URL = "url";
	public static final String REQUEST_URI_ADD_DEVICE = "addDevice";
	public static final String REQUEST_URI_ACTIVATE_DEVICE = "activateDevice";
	public static final String REQUEST_URI_GET_ALL_ARTISTS = "getAllArtists";
	public static final String REQUEST_URI_CHECK_DEVICE = "checkDevice";
	public static final String REQUEST_URI_GET_META_INFORMATIOM_FOR_VINYL = "getMetaInformationForVinyl";
	public static final String REQUEST_URI_GET_VINYLS_FOR_ARTIST = "getVinylsForArtist";

	/**
	 * retrieves the configuration directory
	 * 
	 * @return String
	 */
	public static String getConfigurationsDirectory() {
		return ".";
	}

	/**
	 * retrieves the config file name
	 * 
	 * @return String
	 */
	public static String getConfigurationFileName() {
		return "conf";
	}

	/**
	 * retrieves the host name
	 * 
	 * @return String
	 */
	public static String getHostName(de.mein_plattenregal.logic.Activity caller) {
		String host = "";
		host = "http://192.168.1.107";
		host = "http://192.168.1.101";
		host = "http://192.168.1.122";
		host = "http://mein-plattenregal.de";
		return Handler.factory(caller).get(Config.KEY_URL, host);
	}

	/**
	 * retrieves the api resource
	 * 
	 * @return String
	 */
	public static String getResource() {
		String resource = "";
		resource = "/api/";
		return resource;
	}

	/**
	 * retrieves the delaying time on start screen
	 * 
	 * @return int
	 */
	public static int getStartDelayTime() {
		int val = 4321;
		val = 1000;
		return val;
	}

	/**
	 * retrieves the api key returns null if none is found!
	 * 
	 * @return
	 */
	public static String getApiKey(Activity a) {
		return Handler.factory(a).get(Config.KEY_APIKEY, null);
	}
}
