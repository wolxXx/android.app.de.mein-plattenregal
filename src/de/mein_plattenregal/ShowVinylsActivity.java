package de.mein_plattenregal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ShowVinylsActivity extends de.mein_plattenregal.logic.Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_vinyls);
		setTitle("Alle meine Platten");
		showSpinner(R.id.ShowVinylsActivitySpinner);
		listArtists();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.show_vinyls, menu);
		return true;
	}

	public void display(
			final de.mein_plattenregal.responses.ListArtists response) {
		final Activity that = this;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				for (final String name : response.getList()) {
					LinearLayout layout = (LinearLayout) findViewById(R.id.ShowVinylsActivityLinearLayout);
					Button button = new Button(that);
					button.setText(name);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					layoutParams.setMargins(0, 3, 0, 0);
					button.setLayoutParams(layoutParams);
					layout.addView(button);
					button.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(v.getContext(),
									ArtistActivity.class);
							intent.putExtra("artist", name);
							startActivity(intent);
						}
					});
				}
				hideSpinner(R.id.ShowVinylsActivitySpinner);
			}
		});
	}

	protected void listArtists() {
		final de.mein_plattenregal.logic.Activity that = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				de.mein_plattenregal.requests.ListArtists request = new de.mein_plattenregal.requests.ListArtists(
						that);
				display(request.run());
				Looper.loop();
			}
		}).start();
	}

}
