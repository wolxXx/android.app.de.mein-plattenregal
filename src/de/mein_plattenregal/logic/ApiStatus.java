package de.mein_plattenregal.logic;

/**
 * all status codes from the api
 * 
 * @author wolxXx
 * @version 1.1
 */
public class ApiStatus {
	public final static int success = 0;
	public final static int deviceNotFound = 1;
	public final static int deviceNotActive = 2;
	public final static int incompleteRequest = 3;
	public final static int needIdentAndApiKey = 4;
	public final static int actionNotFound = 5;
	public final static int accessNotGranted = 6;
	public final static int needIdInRequest = 7;
	public final static int vinylItemNotFound = 8;
	public final static int vinylItemNotBelongingToUser = 9;
	public final static int noImageFileRecieved = 10;
	public final static int imageCouldNotBeSaved = 11;
	public final static int initKeyExpired = 12;
	public final static int needArtistInRequest = 13;
	public final static int userNotFound = 14;
	public final static int userCredentialsWrong = 15;
	public final static int couldNotCreateDevice = 16;
	public final static int requestMethodNotExcepted = 17;
	public final static int vinylCoudNotBeSaved = 18;
	public final static int needInitKeyAndIdentInRequest = 19;
	public final static int needFieldAndSearchInRequest = 20;
	public final static int autocompletionFieldNotSupported = 21;
	public final static int needEmailNickAndPasswordInRequest = 22;
	public final static int emailSyntaxNotOk = 23;
	public final static int emailAlreadyInUse = 24;
	public final static int accountCouldNotBeGenerated = 25;
	public final static int deviceUseable = 26;
}
