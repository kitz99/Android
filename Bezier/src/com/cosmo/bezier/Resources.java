package com.cosmo.bezier;

import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Resources {

	private static Vector<Pointer> myArr;
	private static int lSelect;
	private static Bitmap myBground;
	private static Paint myPaint;

	private static void drawCurve(Canvas canvas) {
		if (myArr.size() > 2) {
			myPaint.setStrokeWidth(6f);
			myPaint.setColor(Color.GREEN);
			double currX, currY;
			double oldX = myArr.get(0).getX(), oldY = myArr.get(0).getY();
			for (int t = 0; t <= Calcule.getF(); t++) {
				currX = currY = 0.0;
				for (int i = 0; i < myArr.size(); i++) {
					currX += myArr.get(i).getX()
							* Calcule.getBernie(t, myArr.size() - 1, i);
					currY += myArr.get(i).getY()
							* Calcule.getBernie(t, myArr.size() - 1, i);
				}
				canvas.drawLine((float) oldX, (float) oldY, (float) currX,
						(float) currY, myPaint);
				oldX = currX;
				oldY = currY;
			}
		}
	}

	private static void insertPoint(float x, float y) {
		if (myArr.size() < Calcule.getS()) {
			myArr.add(new Pointer(x, y));
			lSelect = myArr.size() - 1;
		} else {
			lSelect = -1;
		}
	}

	private static void drawGrid(Canvas canvas) {
		if (myArr.size() >= 2) {
			myPaint.setStrokeWidth(3f);
			myPaint.setColor(Color.YELLOW);
			float x = myArr.get(0).getX(), y = myArr.get(0).getY();
			for (int i = 1; i < myArr.size(); i++) {
				canvas.drawLine(x, y, myArr.get(i).getX(), myArr.get(i).getY(),
						myPaint);
				x = myArr.get(i).getX();
				y = myArr.get(i).getY();
			}
		}
	}

	private static void drawPoints(Canvas canvas) {
		myPaint.setStrokeWidth(6f);
		myPaint.setColor(Color.BLUE);
		for (int i = 0; i < myArr.size(); i++) {
			if (i == lSelect) {
				continue;
			}
			myArr.get(i).draw(canvas, myPaint);
		}
		if (lSelect > -1) {
			myPaint.setColor(Color.RED);
			myArr.get(lSelect).draw(canvas, myPaint);
		}
	}

	synchronized public static void noRepeats() {
		if (myArr.size() == 0) {
			return;
		}
		Vector<Pointer> newArr = new Vector<Pointer>();
		newArr.add(myArr.get(0));
		for (int i = 1; i < myArr.size(); i++) {
			if (myArr.get(i - 1).getX() != myArr.get(i).getX()
					|| myArr.get(i - 1).getY() != myArr.get(i).getY()) {
				newArr.add(myArr.get(i));
			}
		}
		myArr = newArr;
		lSelect = myArr.size() - 1;
	}

	synchronized public static void repeatPoint() {
		if (lSelect > -1 && myArr.size() < Calcule.getS()) {
			myArr.add(lSelect + 1, new Pointer(myArr.get(lSelect).getX(), myArr
					.get(lSelect).getY()));
			lSelect++;
		}
	}

	synchronized public static void closeCurve() {
		if (myArr.size() >= 2) {
			insertPoint(myArr.get(0).getX(), myArr.get(0).getY());
		}
	}

	synchronized public static void movePoint(float x, float y) {
		if (lSelect > -1) {
			myArr.get(lSelect).setX(x);
			myArr.get(lSelect).setY(y);
		}
	}

	synchronized public static void newScreen() {
		myArr = new Vector<Pointer>();
		lSelect = -1;
	}

	synchronized public static void popPoint() {
		if (lSelect > -1) {
			myArr.remove(lSelect);
		}
		lSelect = myArr.size() - 1;
	}

	synchronized public static void print(Canvas canvas) {
		canvas.drawBitmap(myBground, 0, 0, null);
		drawGrid(canvas);
		drawCurve(canvas);
		drawPoints(canvas);
	}

	synchronized public static void touchPoint(float x, float y) {
		lSelect = -1;
		for (int i = 0; i < myArr.size(); i++) {
			if (x <= myArr.get(i).getX() + 20 && x >= myArr.get(i).getX() - 20) {
				if (y <= myArr.get(i).getY() + 20
						&& y >= myArr.get(i).getY() - 20) {
					lSelect = i;
				}
			}
		}
		if (lSelect > -1) {
			return;
		}
		insertPoint(x, y);
	}

	public Resources(Bitmap a) {
		myArr = new Vector<Pointer>();
		lSelect = -1;
		myBground = a;
		myPaint = new Paint();
		myPaint.setAntiAlias(true);
		myPaint.setStyle(Paint.Style.STROKE);
		myPaint.setStrokeJoin(Paint.Join.ROUND);
	}

}
