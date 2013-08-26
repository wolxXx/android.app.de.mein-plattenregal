package de.mein_plattenregal.responses.models;

public class VinylForArtist {

	public int id;
	public String title;

	public VinylForArtist(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public int getId() {
		return this.id;
	}

	public VinylForArtist setId(int id) {
		this.id = id;
		return this;
	}

	public VinylForArtist setTitle(String title) {
		this.title = title;
		return this;
	}

}
