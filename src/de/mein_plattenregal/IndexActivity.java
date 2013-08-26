package de.mein_plattenregal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * activity for the index page
 * 
 * @author wolxXx
 * @version 0.2
 */
public class IndexActivity extends de.mein_plattenregal.logic.Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		setTitle("Mein-Plattenregal.de");
		showSpinner(R.id.IndexActivitySpinner);
		init();
	}

	protected boolean isButtonVisible(int buttonId) {
		return Button.GONE != findViewById(buttonId).getVisibility();
	}

	protected IndexActivity toggleButtonVisibility(int buttonId) {
		return this.setButtonVisibility(buttonId,
				isButtonVisible(buttonId) ? Button.GONE : Button.VISIBLE);
	}

	protected IndexActivity setButtonVisibility(final int buttonId,
			final int state) {
		final de.mein_plattenregal.logic.Activity that = this;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				that.findViewById(buttonId).setVisibility(state);
			}
		});
		return this;
	}

	protected IndexActivity hideButton(int buttonId) {
		return this.setButtonVisibility(buttonId, Button.GONE);
	}

	protected IndexActivity showButton(int buttonId) {
		return this.setButtonVisibility(buttonId, Button.VISIBLE);
	}

	protected void init() {
		final de.mein_plattenregal.IndexActivity that = this;
		that.hideButton(R.id.IndexActivityButtonStartUpload)
				.hideButton(R.id.IndexActivityButtonStartAddVinyl)
				.hideButton(R.id.IndexActivityButtonStartListing)
				.hideButton(R.id.IndexActivityButtonStartActivation)
				.hideButton(R.id.IndexActivityButtonStartSettings)
				.hideButton(R.id.IndexActivityButtonStartReinit)
				.hideButton(R.id.IndexActivityButtonStartRegisterDevice)
				.hideButton(R.id.IndexActivityButtonStartScanner);
		new Thread(new Runnable() {

			@Override
			public void run() {
				Looper.prepare();
				String apiKey = de.mein_plattenregal.config.Config
						.getApiKey(that);
				/*
				 * apikey set in conf? nope: display no buttons but activation
				 * and settings device usable? yes: display all buttons but
				 * activation no:
				 */

				if (null == apiKey || "" == apiKey || "null" == apiKey) {
					that.hideButton(R.id.IndexActivityButtonStartUpload)
							.hideButton(R.id.IndexActivityButtonStartAddVinyl)
							.hideButton(R.id.IndexActivityButtonStartListing)
							.hideButton(R.id.IndexActivityButtonStartScanner)
							.showButton(R.id.IndexActivityButtonStartActivation)
							.showButton(R.id.IndexActivityButtonStartSettings)
							.showButton(R.id.IndexActivityButtonStartReinit)
							.showButton(
									R.id.IndexActivityButtonStartRegisterDevice)
							.showToast(
									"ApiKey nicht gesetzt. Bitte Gerät aktivieren!");
					return;
				}

				if (false == de.mein_plattenregal.logic.Tools
						.isDeviceUsable(that)) {
					if (false == de.mein_plattenregal.logic.Tools
							.isConnectedToNet(that)) {
						that.showToast("Kann Server nicht ansprechen. Offline?");
					} else if (false == de.mein_plattenregal.logic.Tools
							.isDeviceRegistered(that)) {
						that.showToast("Gerät nicht registriert auf Server!");
					} else if (false == de.mein_plattenregal.logic.Tools
							.isDeviceActivated(that)) {
						that.showToast("Gerät ist nicht aktiviert!");
					}
					that.hideButton(R.id.IndexActivityButtonStartUpload)
							.hideButton(R.id.IndexActivityButtonStartAddVinyl)
							.hideButton(R.id.IndexActivityButtonStartListing)
							.hideButton(R.id.IndexActivityButtonStartActivation)
							.hideButton(R.id.IndexActivityButtonStartScanner)
							.hideButton(
									R.id.IndexActivityButtonStartRegisterDevice)
							.showButton(R.id.IndexActivityButtonStartSettings)
							.showButton(R.id.IndexActivityButtonStartReinit);
					return;
				}

				that.showButton(R.id.IndexActivityButtonStartUpload)
						.hideButton(R.id.IndexActivityButtonStartAddVinyl)
						.showButton(R.id.IndexActivityButtonStartListing)
						.hideButton(R.id.IndexActivityButtonStartActivation)
						.hideButton(R.id.IndexActivityButtonStartRegisterDevice)
						.showButton(R.id.IndexActivityButtonStartScanner)
						.showButton(R.id.IndexActivityButtonStartSettings)
						.hideButton(R.id.IndexActivityButtonStartReinit);
				hideSpinner(R.id.IndexActivitySpinner);
				Looper.loop();
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

	public void launchActivateDevice(View v) {
		startActivity(new Intent(v.getContext(), ActivateActivity.class));
	}

	public void launchRegisterDevice(View v) {
		startActivity(new Intent(v.getContext(), RegisterDeviceActivity.class));
	}

	public void launchShowVinyls(View v) {
		startActivity(new Intent(v.getContext(), ShowVinylsActivity.class));
	}

	public void launchNewVinyl(View v) {
		startActivity(new Intent(v.getContext(), AddVinylActivity.class));
	}

	public void launchSettings(View v) {
		startActivity(new Intent(v.getContext(), SettingsActivity.class));
	}

	public void launchUploader(View v) {
		startActivity(new Intent(v.getContext(), UploadActivity.class));
	}

	public void launchScanner(View v) {
		startActivity(new Intent(v.getContext(), ScannerActivity.class));
	}

	public void reinit(View v) {
		this.init();
	}

}
