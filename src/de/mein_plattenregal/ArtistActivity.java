package de.mein_plattenregal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * activity for listing all vinyls for an artist
 * 
 * @author wolxXx
 * @version 0.2
 */
public class ArtistActivity extends de.mein_plattenregal.logic.Activity {

	public void display(
			final de.mein_plattenregal.responses.VinylsForArtist response) {
		final de.mein_plattenregal.logic.Activity that = this;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				for (final de.mein_plattenregal.responses.models.VinylForArtist vinylForArtist : response
						.getList()) {
					LinearLayout layout = (LinearLayout) findViewById(R.id.ArtistActivityLinearLayout);
					Button button = new Button(that);
					button.setText(vinylForArtist.getTitle());
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					layoutParams.setMargins(0, 3, 0, 0);
					button.setLayoutParams(layoutParams);
					button.getLineCount();
					layout.addView(button);
					button.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(v.getContext(),
									VinylActivity.class);
							intent.putExtra("id_vinyl", vinylForArtist.getId());
							startActivity(intent);
						}
					});
				}
				hideSpinner(R.id.ArtistActivitySpinner);
			}
		});

	}

	protected void listVinyls() {
		final de.mein_plattenregal.logic.Activity that = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				String searchForArtist = getIntent().getExtras().getString(
						"artist");
				de.mein_plattenregal.requests.VinylsForArtist request = new de.mein_plattenregal.requests.VinylsForArtist(
						that);
				request.setArtist(searchForArtist);
				display(request.run());
				Looper.loop();
			}
		}).start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist);
		showSpinner(R.id.ArtistActivitySpinner);
		setTitle("Alle Platten für "
				+ getIntent().getExtras().getString("artist"));
		listVinyls();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.artist, menu);
		return true;
	}

}
