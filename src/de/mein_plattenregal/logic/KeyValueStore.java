package de.mein_plattenregal.logic;

import java.util.HashMap;
import java.util.Map;

public class KeyValueStore {
	protected Map<String, String> storage;

	public KeyValueStore() {
		this.storage = new HashMap<String, String>();
	}

	public KeyValueStore set(String key, String value) {
		this.storage.put(key, value);
		return this;
	}

	public String get(String key) {
		return this.storage.get(key);
	}
}
