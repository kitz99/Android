package com.room17.mygdxgame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Player implements Disposable {

	Rectangle play;

	private float HEIGHT = 66f, WIDTH = 66f;
	private float speed = 60 * 3, gravity = 60 * 2.4f;
	private float X, Y;
	boolean canJump, right, punching, isJumping;
	private Vector2 velocity;
	private State stat;

	private Animation idle;
	private Animation walk;
	private Animation jump;
	private Animation fall;
	private Animation punch;

	private float time, increment;
	private TiledMapTileLayer collisionLayer;

	private Texture pText;
	
	private float olderX, olderY;

	public Player(float a, float b, TiledMapTileLayer aux) {
		int row = 9, col = 6;
		float rate = 0.15f;
		X = a;
		Y = b;
		collisionLayer = aux;
		pText = new Texture("sprites/sumoHulk.png");
		TextureRegion[][] myArr = TextureRegion.split(pText, pText.getWidth()
				/ col, pText.getHeight() / row);

		TextureRegion[] myV = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			myV[i] = myArr[0][i];
		}
		idle = new Animation(rate, myV);
		idle.setPlayMode(Animation.LOOP);

		myV = new TextureRegion[6];
		for (int i = 0; i < 6; i++) {
			myV[i] = myArr[1][i];
		}
		walk = new Animation(rate, myV);
		walk.setPlayMode(Animation.LOOP);

		myV = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			myV[i] = myArr[2][i];
		}
		jump = new Animation(rate, myV);
		jump.setPlayMode(Animation.LOOP);

		myV = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			myV[i] = myArr[3][i];
		}
		fall = new Animation(rate, myV);
		fall.setPlayMode(Animation.LOOP);

		myV = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			myV[i] = myArr[6][i];
		}
		punch = new Animation(0.10f, myV);
		punch.setPlayMode(Animation.NORMAL);

		right = true;

		velocity = new Vector2();
		stat = State.STAND;
		time = 0;
		isJumping = false;
	}

	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell(
				(int) (x / collisionLayer.getTileWidth()),
				(int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null;
	}

	public boolean collidesRight() {
		for (float step = 0; step <= getHeight(); step += increment)
			if (isCellBlocked(getX() + getWidth(), getY() + step))
				return true;
		return false;
	}

	public boolean collidesLeft() {
		for (float step = 0; step <= getHeight(); step += increment)
			if (isCellBlocked(getX(), getY() + step))
				return true;
		return false;
	}

	public boolean collidesTop() {
		for (float step = 0; step <= getWidth(); step += increment)
			if (isCellBlocked(getX() + step, getY() + getHeight()))
				return true;
		return false;

	}

	public boolean collidesBottom() {
		for (float step = 0; step <= getWidth(); step += increment)
			if (isCellBlocked(getX() + step, getY()))
				return true;
		return false;
	}

	public void update(float delta, float x, float y, boolean aPress) {
		time += delta;
		velocity.y -= gravity * delta;

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			x = -1;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			x = 1;
		}

		if (!isJumping && canJump && (Gdx.input.isKeyPressed(Keys.SPACE) || aPress)) {
			velocity.y = speed / 1.7f;
			isJumping = true;
		}
		if(!aPress) {
			isJumping = false;
		}

		velocity.x = speed * x;

		if (velocity.y > speed) {
			velocity.y = speed;
		} else if (velocity.y < -speed) {
			velocity.y = -speed;
		}

		setOlderX(getX());
		olderY = getY();
		boolean collisionX = false, collisionY = false;
		float a = velocity.x, b = velocity.y;

		setX(getX() + velocity.x * delta);
		increment = collisionLayer.getTileWidth();
		increment = getWidth() < increment ? getWidth() / 2 : increment / 2;
		if (velocity.x < 0) { // going left
			collisionX = collidesLeft();
		} else if (velocity.x > 0) {// going right
			collisionX = collidesRight();
		}
		if (collisionX) {
			setX(getOlderX());
			velocity.x = 0;
		}

		setY(getY() + velocity.y * delta * 5f);
		increment = collisionLayer.getTileHeight();
		increment = getHeight() < increment ? getHeight() / 2 : increment / 2;
		if (velocity.y < 0) { // going down
			canJump = collisionY = collidesBottom();
		} else if (velocity.y > 0) {// going up
			collisionY = collidesTop();
			if(collisionY) {
				canJump = false;
			}
		}
		if (collisionY) {
			setY(olderY);
			velocity.y = 0;
		}
		
		if(b > 0 && stat != State.JUMP) {
			stat = State.JUMP;
			time = 0;
		}
		if(velocity.y == 0 && a != 0 && stat != State.WALK) {
			stat = State.WALK;
			time = 0;
		}
		if(velocity.y == 0 && a == 0 && stat != State.STAND) {
			stat = State.STAND;
			time = 0;
		}
		if(a != 0) {
			right = (a > 0);
		}
		play = new Rectangle(X, Y, WIDTH, HEIGHT);
	}

	private void setY(float f) {
		Y = f;
	}

	private void setX(float f) {
		X = f;
	}

	public float getX() {
		return X;
	}

	public float getY() {
		return Y;
	}

	private float getWidth() {
		return WIDTH;
	}

	private float getHeight() {
		return HEIGHT;
	}

	@Override
	public void dispose() {
		pText.dispose();
	}

	public void draw(Batch batch) {
		TextureRegion frame = null;
		switch (stat) {
		case STAND:
			frame = idle.getKeyFrame(time);
			break;
		case FALL:
			frame = fall.getKeyFrame(time);
			break;
		case WALK:
			frame = walk.getKeyFrame(time);
			break;
		case JUMP:
			frame = jump.getKeyFrame(time);
			break;
		case PUNCH:
			frame = punch.getKeyFrame(time);
			if (punch.isAnimationFinished(time)) {
				punching = false;
			}
			break;
		}
		if (right) {
			batch.draw(frame, X, Y, WIDTH, HEIGHT);
		} else {
			batch.draw(frame, X + WIDTH, Y, -WIDTH, HEIGHT);
		}
	}

	public Rectangle getRect() {
		return play;
	}

	public float getOlderX() {
		return olderX;
	}

	public void setOlderX(float olderX) {
		this.olderX = olderX;
	}
}
