package com.cosmo.bezier;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BezierHandler extends Activity {

	private Screen a;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		a = new Screen(this);
		setContentView(a);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		a.stahpIt();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		this.onStop();
	}

	@Override
	protected void onStop() {
		a.stahpIt();
		super.onStop();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			Resources.popPoint();
			return true;
		case R.id.item2:
			Resources.closeCurve();
			return true;
		case R.id.item3:
			Resources.repeatPoint();
			return true;
		case R.id.item4:
			Resources.noRepeats();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
