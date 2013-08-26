package de.mein_plattenregal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class RegisterDeviceActivity extends de.mein_plattenregal.logic.Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_device);
		showSpinner(R.id.RegisterDeviceeActivitySpinner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_device, menu);
		return true;
	}

	public void startRegistration(View v) {
		final de.mein_plattenregal.RegisterDeviceActivity that = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				// check if email is filled
				EditText input = (EditText) findViewById(R.id.RegisterDeviceActivityInputEmail);
				int stringLength = input.getText().length();
				if (0 == stringLength) {
					that.showToast("Bitte erst Email eingeben!");
					return;
				}
				String email = input.getText().toString();

				// check if password is filled
				input = (EditText) findViewById(R.id.RegisterDeviceActivityInputPassword);
				stringLength = input.getText().length();
				if (0 == stringLength) {
					that.showToast("Bitte erst Passwort eingeben!");
					return;
				}
				String password = input.getText().toString();

				// check if name is filled
				input = (EditText) findViewById(R.id.RegisterDeviceActivityInputName);
				stringLength = input.getText().length();
				if (0 == stringLength) {
					that.showToast("Bitte erst Gerätenamen eingeben!");
					return;
				}
				String name = input.getText().toString();

				de.mein_plattenregal.requests.AddDevice request = new de.mein_plattenregal.requests.AddDevice(
						that);
				request.setIdent(de.mein_plattenregal.logic.Tools
						.getDeviceID(that));
				request.setName(name);
				request.setPassword(password);
				request.setEmail(email);

				de.mein_plattenregal.responses.AddDevice response = request
						.run();

				if (null == response) {
					that.showToast("Konnte Gerät nicht registrieren.");
					return;
				}
				if (true == response.hasError()) {
					that.showToast("Konnte Gerät nicht registrieren.");
					return;
				}
				that.showToast("Gerät registriert!");
				String deviceID = de.mein_plattenregal.logic.Tools
						.getDeviceID(that);
				de.mein_plattenregal.config.Handler.factory(that).put(
						de.mein_plattenregal.config.Config.KEY_APIKEY,
						response.getApiKey());
				de.mein_plattenregal.config.Handler.factory(that).put(
						de.mein_plattenregal.config.Config.KEY_IDENT, deviceID);
				startActivity(new Intent(getBaseContext(), IndexActivity.class));
				that.finish();
				Looper.loop();
			}
		}).start();
	}
}
