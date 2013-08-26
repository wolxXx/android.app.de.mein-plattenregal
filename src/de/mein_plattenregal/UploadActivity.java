package de.mein_plattenregal;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class UploadActivity extends de.mein_plattenregal.logic.Activity {

	protected TextView text = null;
	protected int counter = 0;
	protected ProgressBar bar;
	protected ArrayList<de.mein_plattenregal.logic.UploadObject> list = new ArrayList<de.mein_plattenregal.logic.UploadObject>();
	protected Button button;

	protected void init() {
		this.bar = (ProgressBar) findViewById(R.id.UploadActivityProgressBar);
		this.bar.setVisibility(View.INVISIBLE);
		this.button = (Button) findViewById(R.id.UploadActivityButtonStart);
		this.text = (TextView) findViewById(R.id.UploadActivityLabel);
		this.text.setText("");
		this.getAvailableImages();
		hideSpinner(R.id.UploadActivitySpinner);
	}

	public void startUpload(View v) {
		if (0 == this.list.size()) {
			return;
		}
		button.setEnabled(false);
		button.setText("upload gestartet!");
		this.bar.setVisibility(View.VISIBLE);
		this.bar.setProgress(0);
		final ProgressBar bar = this.bar;
		final de.mein_plattenregal.logic.Activity that = this;
		final ArrayList<de.mein_plattenregal.logic.UploadObject> list = this.list;
		new Thread(new Runnable() {

			@Override
			public void run() {
				de.mein_plattenregal.logic.UploadObject current;
				int items = list.size();
				bar.setProgress(0);
				for (int i = 0; i < items; i++) {
					current = list.get(i);
					Log.d("current vinyl id", current.id_vinyl + "");
					Log.d("current image path", current.path);
					de.mein_plattenregal.logic.FileUploader.run(that, current);
					bar.setProgress((items / (i + 1)) * 100);
				}
				new Thread(new Runnable() {
					@Override
					public void run() {
						Looper.prepare();
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
						}
						Toast.makeText(that.getBaseContext(), "Fertig!",
								Toast.LENGTH_SHORT).show();
						startActivity(new Intent(that.getBaseContext(),
								UploadActivity.class));
						that.finish();
						Looper.loop();
					}
				}).start();
			}
		}).start();
	}

	protected void addText(String text) {
		this.text.setText(this.text.getText().toString() + "\n" + text);
	}

	protected void listFilesForFolder(File file) {
		for (final File fileEntry : file.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				de.mein_plattenregal.logic.UploadObject current = new de.mein_plattenregal.logic.UploadObject();
				current.fileName = fileEntry.getName();
				current.id_vinyl = Integer.parseInt(fileEntry.getParentFile()
						.getName());
				current.path = fileEntry.getAbsolutePath();
				this.list.add(current);
			}
		}
	}

	protected void getAvailableImages() {
		final File file = new File(Environment.getExternalStorageDirectory(),
				this.getPackageName());
		this.listFilesForFolder(file);
		this.addText(this.list.size() + " Foto"
				+ (1 == this.list.size() ? "" : "s") + " zum Hochladen bereit");
		if (0 == this.list.size()) {
			this.button.setEnabled(false);
			this.button.setText("Keine Fotos zum Hochladen!");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		setTitle("Alle Fotos hochladen");
		showSpinner(R.id.UploadActivitySpinner);
		init();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upload, menu);
		return true;
	}

}
