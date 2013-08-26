package de.mein_plattenregal.logic;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class PictureTaker {

	protected de.mein_plattenregal.logic.Activity caller;

	protected int result = 0;

	public PictureTaker(de.mein_plattenregal.logic.Activity caller) {
		this.caller = caller;
	}

	public void takeImage(int id_vinyl) throws Exception {
		String fileName = "image" + Tools.getRand() + ".jpg";
		String filePath = this.caller.getPackageName() + "/" + id_vinyl;
		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(storeFile(this.caller, fileName, filePath)));
		this.caller.startActivityForResult(intent, this.result);
	}

	protected File storeFile(Context context, String fileName, String filePath)
			throws Exception {
		final File file = new File(Environment.getExternalStorageDirectory(),
				filePath);
		if (false == file.exists()) {
			Log.d("file writer", "directory for saving image is not existant");
			if (false == file.mkdirs()) {
				Log.d("file writer", "could not create directories!!");
				throw new Exception("Could not create Directories");
			}
		}
		return new File(file, fileName);
	}
}
