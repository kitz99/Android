package com.cosmo.bezier;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Screen extends SurfaceView implements SurfaceHolder.Callback {

	private Runner activ;

	public Screen(Context context) {
		super(context);
		this.getHolder().addCallback(this);
		activ = new Runner(this.getHolder(), this);
		this.setFocusable(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Resources.touchPoint(event.getX(), event.getY());
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			Resources.movePoint(event.getX(), event.getY());
		}
		return true;
	}

	public void render(Canvas canvas) {
		Resources.print(canvas);
	}

	public void stahpIt() {
		activ.setRunning(false);
		((Activity) getContext()).finish();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		activ.setRunning(true);
		activ.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		while (true) {
			try {
				activ.join();
				break;
			} catch (Exception ex) {
				continue;
			}
		}
	}
}