package de.mein_plattenregal.responses;

import java.util.ArrayList;

/**
 * response class for artists
 * 
 * @author wolxXx
 * @version 1.1
 */
public class ListArtists extends de.mein_plattenregal.responses.Abstract {
	/**
	 * the list of artist names
	 */
	protected ArrayList<String> list = new ArrayList<String>();

	/**
	 * adds an artist to the internal array
	 * 
	 * @param artist
	 */
	public void addArtist(String artist) {
		this.list.add(artist);
	}

	/**
	 * getter for the artists
	 * 
	 * @return ArrayList
	 */
	public ArrayList<String> getList() {
		return this.list;
	}
}
