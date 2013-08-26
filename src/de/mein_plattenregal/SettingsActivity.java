package de.mein_plattenregal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * activity for settings
 * 
 * @author wolxXx
 * @version 0.1
 */
public class SettingsActivity extends de.mein_plattenregal.logic.Activity {
	/**
	 * writes the configuration value for the given what field
	 * 
	 * @param what
	 * @param value
	 */
	protected void writeConfig(String what, String value) {
		if ("" == value || null == value) {
			value = null;
		} else {
			value = value.trim();
		}
		Log.d("writing conf",
				String.format("what = %s | value = [%s]", what, value));
		de.mein_plattenregal.config.Handler.factory(this).put(what, value);
	}

	/**
	 * writes the configuration for the api key
	 * 
	 * @param apiKey
	 */
	protected void writeApiKey(String apiKey) {
		this.writeConfig(de.mein_plattenregal.config.Config.KEY_APIKEY, apiKey);
	}

	/**
	 * writes the configuration for the url
	 * 
	 * @param url
	 */
	protected void writeUrl(String url) {
		this.writeConfig(de.mein_plattenregal.config.Config.KEY_URL, url);
	}

	/**
	 * writes the configuration for the device identifier
	 * 
	 * @param ident
	 */
	protected void writeIdent(String ident) {
		this.writeConfig(de.mein_plattenregal.config.Config.KEY_IDENT, ident);
	}

	/**
	 * writes the default configuration properties
	 */
	protected void writeDefaults() {
		try {
			de.mein_plattenregal.config.Handler.factory(this).clearAll();
		} catch (Exception e) {

		}

		writeUrl(de.mein_plattenregal.config.Config.getHostName(this));
		writeApiKey(null);
		writeIdent(de.mein_plattenregal.logic.Tools.getDeviceID(this));
	}

	/**
	 * resets the settings to the defaults if the dialogue box is successfully
	 * submitted
	 * 
	 * @param v
	 */
	public void resetSettings(final View v) {
		final SettingsActivity that = this;
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(that);
		alertDialog2.setTitle("oO Wirklich löschen?");
		alertDialog2
				.setMessage("Bist du dir sicher, dass du die Einstellungen löschen möchtest?");
		alertDialog2.setIcon(R.drawable.ic_launcher);
		alertDialog2.setPositiveButton("Ja",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						writeDefaults();
						Toast.makeText(v.getContext(),
								"Einstellungen zurück gesetzt",
								Toast.LENGTH_SHORT).show();
						try {
							Thread.sleep(456);
						} catch (InterruptedException e) {
						}
						startActivity(new Intent(v.getContext(),
								IndexActivity.class));
						that.finish();
					}
				});
		alertDialog2.setNegativeButton("Nein",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(),
								"Ich bin beruhigt, dude!", Toast.LENGTH_SHORT)
								.show();
						dialog.cancel();
					}
				});
		alertDialog2.show();
	}

	/**
	 * saves the settings
	 * 
	 * @param v
	 */
	public void saveSettings(View v) {
		writeUrl(((EditText) findViewById(R.id.SettingsActivityInputURL))
				.getText().toString().trim());
		Toast.makeText(v.getContext(), "Einstellungen gespeichert",
				Toast.LENGTH_SHORT).show();
		try {
			Thread.sleep(1234);
		} catch (InterruptedException e) {
		}
		startActivity(new Intent(v.getContext(), IndexActivity.class));
		this.finish();
	}

	/**
	 * lists the available options
	 */
	protected void listOptions() {
		de.mein_plattenregal.config.Handler handler = de.mein_plattenregal.config.Handler
				.factory(this);
		String defaultValue = "";

		defaultValue = handler.get(de.mein_plattenregal.config.Config.KEY_URL,
				de.mein_plattenregal.config.Config.getHostName(this));
		((TextView) findViewById(R.id.SettingsActivityInputURL))
				.setText(defaultValue);

		defaultValue = handler.get(
				de.mein_plattenregal.config.Config.KEY_APIKEY, null);
		((TextView) findViewById(R.id.SettingsActivityInputApiKey))
				.setText(defaultValue);

		defaultValue = handler.get(
				de.mein_plattenregal.config.Config.KEY_IDENT,
				de.mein_plattenregal.logic.Tools.getDeviceID(this));
		((TextView) findViewById(R.id.SettingsActivityInputIdent))
				.setText(defaultValue);
		hideSpinner(R.id.SettingsActivitySpinner);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setTitle("Einstellungen bearbeiten");
		showSpinner(R.id.SettingsActivitySpinner);
		listOptions();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
