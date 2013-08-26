package de.mein_plattenregal;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.mein_plattenregal.logic.PictureTaker;
import de.mein_plattenregal.responses.Vinyl;

/**
 * getting meta information for a given id
 * 
 * @author wolxXx
 * @version 0.2
 * 
 */
public class VinylActivity extends de.mein_plattenregal.logic.Activity {

	public void takeImage(View v) {
		PictureTaker t = new PictureTaker(this);
		try {
			t.takeImage(getIntent().getExtras().getInt("id_vinyl"));
		} catch (Exception e) {
			Log.d("picture taker", e.getMessage());
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vinyl);
		setTitle("Detailansicht");
		hideElement(R.id.VinylActivityButtonTakeImage);
		showSpinner(R.id.VinylActivitySpinner);
		getVinylMetadata();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vinyl, menu);
		return true;
	}

	/**
	 * retrieving id from putted extra
	 */
	protected void getVinylMetadata() {
		final de.mein_plattenregal.logic.Activity that = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				int id_vinyl = getIntent().getExtras().getInt("id_vinyl");
				de.mein_plattenregal.requests.Vinyl request = new de.mein_plattenregal.requests.Vinyl(
						that);
				request.setId(id_vinyl);
				display(request.run());
				Looper.loop();
			}
		}).start();
	}

	void display(final Vinyl response) {
		final VinylActivity that = this;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showElement(R.id.VinylActivityButtonTakeImage);
				hideElement(R.id.VinylActivitySpinner);
				that.addInfo("Interpret", response.Vinyl.artist);
				that.addInfo("Titel", response.Vinyl.title);
				that.addInfo("Jahr", response.Vinyl.year + "");
				that.addInfo("Genre", response.Vinyl.genre);
				that.addInfo("RPM", response.Vinyl.rpm + "");
				that.addInfo("Label", response.Vinyl.label);
				that.addInfo("Farbe", response.Vinyl.color);
				that.addInfo("Format", response.Vinyl.size + "\"");
				that.addInfo("Typ", response.Vinyl.type);
				that.addInfo("Info", response.Vinyl.description);
				that.setTitle(that.getTitle() + " für " + response.Vinyl.artist
						+ " - " + response.Vinyl.title);
			}
		});
	}

	public final void addInfo(String label, String value) {
		if ("" == value) {
			return;
		}
		TextView v = new TextView(this);
		v.setText(label);
		LinearLayout layout = (LinearLayout) findViewById(R.id.VinylActivityLinearLayout);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 3, 0, 0);
		v.setLayoutParams(layoutParams);
		v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		layout.addView(v);

		TextView v2 = new TextView(this);
		v2.setText(value);
		v2.setLayoutParams(layoutParams);
		v2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		layout.addView(v2);
	}

}