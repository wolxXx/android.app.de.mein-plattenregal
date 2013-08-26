package de.mein_plattenregal;

import android.os.Bundle;
import android.view.Menu;

public class AddVinylActivity extends de.mein_plattenregal.logic.Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_vinyl);
		setTitle("Neue Platte erfassen");
		showSpinner(R.id.AddActivitySpinner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_vinyl, menu);
		return true;
	}

}
