package de.mein_plattenregal.exceptions;

public abstract class DefaultException  extends Exception {
	private static final long serialVersionUID = -5369789991615238589L;
	
	public DefaultException(String message) {
		super(message);
	}
	
	public DefaultException(){
		super();
	}

}
