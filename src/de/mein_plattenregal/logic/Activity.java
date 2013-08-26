package de.mein_plattenregal.logic;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity extends android.app.Activity {

	public final void showToast(String message) {
		this.showToast(message, Toast.LENGTH_SHORT);
	}

	public final void showToast(String message, int duration) {
		duration = duration == Toast.LENGTH_SHORT ? Toast.LENGTH_SHORT
				: Toast.LENGTH_LONG;
		Toast.makeText(this, message, duration).show();
	}

	public final void hideElement(int idElement) {
		findViewById(idElement).setVisibility(View.GONE);
	}

	public final void showElement(int idElement) {
		findViewById(idElement).setVisibility(View.VISIBLE);
	}

	public final void toggleElement(int idElement) {
		findViewById(idElement)
				.setVisibility(
						View.GONE == findViewById(idElement).getVisibility() ? View.VISIBLE
								: View.GONE);
	}

	public final void showSpinner(final int idSpinner) {

		final de.mein_plattenregal.logic.Activity that = this;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.d("spinner", "showing");
					InputStream is = null;
					is = that.getResources().getAssets().open("loading.gif");
					Bitmap image = BitmapFactory.decodeStream(is);

					ImageView ib2 = (ImageView) findViewById(idSpinner);
					ib2.setImageBitmap(image);
				} catch (Exception e) {
					Log.d("spinner switching excepition", e.getMessage() + ".");
				}
			}
		});

	}

	public final void hideSpinner(final int idSpinner) {
		final de.mein_plattenregal.logic.Activity that = this;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.d("spinner", "hiding");
					that.hideElement(idSpinner);
				} catch (Exception x) {
					Log.d("oO", x.getMessage() + "");
				}
			}
		});
	}
}
