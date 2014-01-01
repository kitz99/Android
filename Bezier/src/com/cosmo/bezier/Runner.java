package com.cosmo.bezier;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class Runner extends Thread {

	private SurfaceHolder sH;
	private Screen screen;
	private boolean bOk;

	public Runner(SurfaceHolder s, Screen a) {
		super();
		sH = s;
		screen = a;
	}

	@Override
	public void run() {
		Canvas canvas;
		while (bOk) {
			canvas = null;
			try {
				canvas = sH.lockCanvas();
				synchronized (sH) {
					screen.render(canvas);
				}
			} catch (Exception e) {

			} finally {
				if (canvas != null) {
					sH.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	public void setRunning(boolean run) {
		this.bOk = run;
	}

}
