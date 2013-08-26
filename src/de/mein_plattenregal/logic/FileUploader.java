package de.mein_plattenregal.logic;

import java.io.File;
import java.io.IOException;

import android.os.Looper;
import android.util.Log;

/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 * 
 * @author www.codejava.net
 * @see http 
 *      ://www.codejava.net/java-se/networking/upload-files-by-sending-multipart
 *      -request-programmatically
 * 
 */

public class FileUploader {
	public static void run(final de.mein_plattenregal.logic.Activity activity,
			final UploadObject object) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				String urlToConnect = de.mein_plattenregal.config.Config
						.getHostName(activity)
						+ de.mein_plattenregal.config.Config.getResource();
				urlToConnect += "addImage";
				try {
					MultipartUtility util = new MultipartUtility(urlToConnect,
							"UTF-8");
					util.addFormField("id", object.id_vinyl + "");
					util.addFormField(
							"apikey",
							de.mein_plattenregal.config.Handler
									.factory(activity)
									.get(de.mein_plattenregal.config.Config.KEY_APIKEY));
					util.addFormField(
							"ident",
							de.mein_plattenregal.config.Handler
									.factory(activity)
									.get(de.mein_plattenregal.config.Config.KEY_IDENT));
					File file = new File(object.path);
					util.addFilePart("image", file);
					for (String current : util.finish()) {
						Log.d("a", current);
					}
					file.delete();
				} catch (IOException e) {
					Log.d("exception:", e.getMessage() + "");
				}

				Looper.loop();
			}
		}).start();
	}
}