package de.mein_plattenregal.responses;

import java.util.ArrayList;

public class VinylsForArtist extends de.mein_plattenregal.responses.Abstract {

	protected ArrayList<de.mein_plattenregal.responses.models.VinylForArtist> list = new ArrayList<de.mein_plattenregal.responses.models.VinylForArtist>();

	public VinylsForArtist addVinylForArtist(de.mein_plattenregal.responses.models.VinylForArtist vinylForArtist) {
		this.list.add(vinylForArtist);
		return this;
	}

	public ArrayList<de.mein_plattenregal.responses.models.VinylForArtist> getList() {
		return this.list;
	}
}
