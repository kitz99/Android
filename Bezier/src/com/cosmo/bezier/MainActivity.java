package com.cosmo.bezier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btn1;
	private Button btn2;
	private MainActivity stuff;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Calcule.preCalc();
		stuff = this;
		setContentView(R.layout.activity_main);
		btn1 = (Button) this.findViewById(R.id.button1);
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(stuff, BezierHandler.class);
				stuff.startActivity(myIntent);
			}
		});
		btn2 = (Button) this.findViewById(R.id.button2);
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Resources.newScreen();
				Intent myIntent = new Intent(stuff, BezierHandler.class);
				stuff.startActivity(myIntent);
			}
		});
		@SuppressWarnings("unused")
		Resources all = new Resources(BitmapFactory.decodeResource(
				getResources(), R.drawable.bground));
	}
}
