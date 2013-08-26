package de.mein_plattenregal.logic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.UUID;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * tools collection
 * 
 * @author wolxXx
 * @version 1.1
 */
public class Tools {
	public static boolean isConnectedToNet(
			de.mein_plattenregal.logic.Activity caller) {
		String connectTo = de.mein_plattenregal.config.Config.getHostName(
				caller).replace("http://", "");
		Log.d("connect to ip: ", connectTo);
		try {
			return InetAddress.getByName(connectTo).isReachable(400);
		} catch (UnknownHostException e) {
			Log.d("exception", e.getMessage());
		} catch (IOException e) {
			Log.d("exception", e.getMessage());
		}
		return false;
	}

	public static boolean isDeviceUsable(
			de.mein_plattenregal.logic.Activity caller) {
		Log.d("connected", isConnectedToNet(caller) + ".");
		return true == isConnectedToNet(caller)
				&& true == isDeviceRegistered(caller)
				&& isDeviceActivated(caller);
	}

	public static boolean isDeviceActivated(
			de.mein_plattenregal.logic.Activity caller) {
		de.mein_plattenregal.requests.Status request = new de.mein_plattenregal.requests.Status(
				caller);
		de.mein_plattenregal.responses.Status status = request.run();
		return status.isDeviceActive();
	}

	public static boolean isDeviceRegistered(
			de.mein_plattenregal.logic.Activity caller) {
		de.mein_plattenregal.requests.Status request = new de.mein_plattenregal.requests.Status(
				caller);
		de.mein_plattenregal.responses.Status status = request.run();
		return status.isDeviceRegistered();
	}

	/**
	 * retrieves the device's id
	 * 
	 * @param activity
	 * @return String
	 */
	public static String getDeviceID(
			de.mein_plattenregal.logic.Activity activity) {
		final TelephonyManager tm = (TelephonyManager) activity
				.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						activity.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String deviceId = deviceUuid.toString();
		return deviceId;
	}

	/**
	 * retrieves a random number where max is the max int value
	 * 
	 * @return int
	 */
	public static int getRand() {
		return getRand(Integer.MAX_VALUE);
	}

	/**
	 * retrieves a random number whith explicit max int value
	 * 
	 * @param max
	 * @return int
	 */
	public static int getRand(int max) {
		Random rand = new Random();
		return rand.nextInt(max);
	}
}