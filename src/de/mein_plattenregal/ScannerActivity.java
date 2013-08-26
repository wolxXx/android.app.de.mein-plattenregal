package de.mein_plattenregal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScannerActivity extends de.mein_plattenregal.logic.Activity {

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanResult != null) {
			lookForEanOnNet(scanResult.getContents());
			return;
		}
		Log.d("scan result", "is fuckin empty!!!!");
	}

	public void lookForEanOnNet(String ean) {
		Log.d("gescannter code", ean);
		showToast("gescannter ean code: " + ean
				+ "\nKann noch nicht nach ean suchen.\n\nbin noch zu doof :)",
				Toast.LENGTH_LONG);
	}

	public void startScanner(View v) {
		new IntentIntegrator(this).initiateScan();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanner);
		setTitle("Scanner Test!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scanner, menu);
		return true;
	}

}
