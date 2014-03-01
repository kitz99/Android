package com.room17.mygdxgame;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.room17.shakey.ShakeDetector;
import com.room17.shakey.Shaker;

public class MainActivity extends AndroidApplication {

	private Shaker shakey;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		shakey = new Shaker(this.getContext());
		shakey.addListener(new ShakeDetector() {
			@Override
			public void shakeDetected() {
				vibeShake();
			}
		});

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = true;

		initialize(new MyGdxGame(), cfg);
	}

	public void vibeShake() {
		Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(200);
	}

	@Override
	protected void onResume() {
		super.onResume();
		shakey.start();
		getWindow().addFlags(
				android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	protected void onPause() {
		getWindow().clearFlags(
				android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		shakey.stop();
		super.onPause();
	}
}