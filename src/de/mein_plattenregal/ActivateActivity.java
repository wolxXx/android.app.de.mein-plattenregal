package de.mein_plattenregal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * activity for activating the user's device
 * 
 * @author wolxXx
 * @version 0.3
 */
public class ActivateActivity extends de.mein_plattenregal.logic.Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activate);
		setTitle("Gerät aktivieren");
		showSpinner(R.id.ActivateActivitySpinner);
		hideSpinner(R.id.ActivateActivitySpinner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activate, menu);
		return true;
	}

	protected void setButtonEnabled(final boolean state) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				findViewById(R.id.ActivateActivityButtonStartActivation)
						.setEnabled(state);
			}
		});
	}

	protected void enableButton() {
		setButtonEnabled(true);
	}

	protected void disableButton() {
		setButtonEnabled(false);
	}

	public void activateButtonClicked(View v) {
		disableButton();
		final de.mein_plattenregal.ActivateActivity that = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				EditText input = (EditText) findViewById(R.id.ActivateActivityInputInitKey);
				int initKeyLength = input.getText().length();
				if (0 == initKeyLength) {
					that.showToast("Bitte erst Code eingeben!");
					enableButton();
					return;
				}
				showSpinner(R.id.ActivateActivitySpinner);
				String deviceID = de.mein_plattenregal.logic.Tools
						.getDeviceID(that);
				de.mein_plattenregal.requests.ActivateDevice request = new de.mein_plattenregal.requests.ActivateDevice(
						that);
				request.setIdent(deviceID);
				request.setInitkey(input.getText().toString());
				de.mein_plattenregal.responses.ActivateDevice response = request
						.run();
				if (null == response) {
					that.showToast("Konnte Gerät nicht aktivieren.");
					enableButton();
					hideSpinner(R.id.ActivateActivitySpinner);
					return;
				}
				if (true == response.hasError()) {
					that.showToast("Konnte Gerät nicht aktivieren.");
					enableButton();
					hideSpinner(R.id.ActivateActivitySpinner);
					return;
				}
				that.showToast("Gerät aktiviert!");
				de.mein_plattenregal.config.Handler.factory(that).put(
						de.mein_plattenregal.config.Config.KEY_APIKEY,
						response.getApiKey());
				de.mein_plattenregal.config.Handler.factory(that).put(
						de.mein_plattenregal.config.Config.KEY_IDENT, deviceID);
				hideSpinner(R.id.ActivateActivitySpinner);
				startActivity(new Intent(getBaseContext(), IndexActivity.class));
				that.finish();
				Looper.loop();
			}
		}).start();
	}
}
