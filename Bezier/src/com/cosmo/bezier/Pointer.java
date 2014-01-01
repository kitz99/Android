package com.cosmo.bezier;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Pointer {

	private float x;
	private float y;

	public Pointer(float a, float b) {
		x = a;
		y = b;
	}

	public void draw(Canvas canvas, Paint paint) {
		canvas.drawCircle(x, y, 15, paint);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
}
