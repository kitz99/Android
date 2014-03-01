package com.room17.mygdxgame.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class Heart implements Disposable {
	private Animation myAnim;

	private float WIDTH = 68, HEIGHT = 68;

	float stateTime;

	float X, Y;

	Rectangle rect;

	public Heart(float x, float y) {
		TextureRegion[] myV = new TextureRegion[7];
		myV[0] = new TextureRegion(new Texture("sprites/1.png"));
		myV[1] = new TextureRegion(new Texture("sprites/2.png"));
		myV[2] = new TextureRegion(new Texture("sprites/3.png"));
		myV[3] = new TextureRegion(new Texture("sprites/4.png"));
		myV[4] = new TextureRegion(new Texture("sprites/5.png"));
		myV[5] = new TextureRegion(new Texture("sprites/6.png"));
		myV[6] = new TextureRegion(new Texture("sprites/7.png"));
		myAnim = new Animation(0.15f, myV);
		myAnim.setPlayMode(Animation.LOOP);
		stateTime = 0;
		X = x;
		Y = y;
		rect = new Rectangle(X, Y, WIDTH, HEIGHT);
	}

	public void update(float delta) {
		stateTime += delta;
	}

	public void draw(Batch batch) {
		batch.draw(myAnim.getKeyFrame(stateTime), X, Y, WIDTH, HEIGHT);
	}

	@Override
	public void dispose() {

	}

	public float getX() {
		return X;
	}

	public float getY() {
		return Y;
	}

	public boolean col(Rectangle test) {
		return rect.overlaps(test);
	}
}
