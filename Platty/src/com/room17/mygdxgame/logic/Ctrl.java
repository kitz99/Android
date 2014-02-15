package com.room17.mygdxgame.logic;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;

public class Ctrl implements Disposable {
	private Touchpad touchpad;
	private TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
	private Drawable touchBackground;
	private Drawable touchKnob;

	private ArrayList<Disposable> myDestr;

	public Ctrl() {
		myDestr = new ArrayList<Disposable>();
		touchpadSkin = new Skin();
		Texture a = new Texture("data/touchBackground.png"), b = new Texture(
				"data/touchKnob.png");
		myDestr.add(a);
		myDestr.add(b);
		touchpadSkin.add("touchBackground", a);
		touchpadSkin.add("touchKnob", b);
		touchpadStyle = new TouchpadStyle();
		touchBackground = touchpadSkin.getDrawable("touchBackground");
		touchKnob = touchpadSkin.getDrawable("touchKnob");
		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;
		touchpad = new Touchpad(10, touchpadStyle);
		touchpad.setBounds(15, 15, 200, 200);
	}

	public Touchpad getTouch() {
		return touchpad;
	}

	public float getX() {
		return touchpad.getKnobPercentX();
	}

	public float getY() {
		return touchpad.getKnobPercentY();
	}

	@Override
	public void dispose() {
		for(Disposable each : myDestr) {
			each.dispose();
		}
	}
}
