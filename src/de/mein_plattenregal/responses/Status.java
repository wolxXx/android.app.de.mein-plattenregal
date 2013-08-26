package de.mein_plattenregal.responses;

/**
 * status class for the deive
 * 
 * @author wolxXx
 * @version 1.1
 */
public class Status extends de.mein_plattenregal.responses.Abstract {
	/**
	 * flag for device registered
	 * 
	 * @return boolean
	 */
	public boolean isDeviceRegistered(){
		return de.mein_plattenregal.logic.ApiStatus.deviceNotFound != this.getStatus();
	}

	/**
	 * flag for device active
	 * 
	 * @return boolean
	 */
	public boolean isDeviceActive(){
		return de.mein_plattenregal.logic.ApiStatus.deviceNotActive != this.getStatus();
	}
	
	/**
	 * flag for device usable
	 * 
	 * @return boolean
	 */
	public boolean isDeviceUsable(){
		return de.mein_plattenregal.logic.ApiStatus.deviceUseable == this.getStatus();
	}
}
