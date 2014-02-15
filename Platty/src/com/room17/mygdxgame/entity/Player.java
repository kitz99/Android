package com.room17.mygdxgame.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Player implements Disposable {
	
	private float HEIGHT = 32f, WIDTH = 32f;
	private float speed = 60 * 2, gravity = 60 * 1.8f;
	private float X, Y;
	boolean canJump, right;
	private Vector2 velocity;
	private State stat;
	
	private Animation idle;
	private Animation walk;
	private Animation jump;
	private Animation fall;
	
	private float time;
	
	private TiledMapTileLayer collisionLayer;
	
	Texture pText;

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
		for(int i = 0; i < 4; i++) {
			myV[i] = myArr[0][i];
		}
		idle = new Animation(rate, myV);
		idle.setPlayMode(Animation.LOOP);
		
		myV = new TextureRegion[6];
		for(int i = 0; i < 6; i++) {
			myV[i] = myArr[1][i];
		}
		walk = new Animation(rate, myV);
		walk.setPlayMode(Animation.LOOP);
		
		myV = new TextureRegion[3];
		for(int i = 0; i < 3; i++) {
			myV[i] = myArr[2][i];
		}
		jump = new Animation(rate, myV);
		jump.setPlayMode(Animation.LOOP);
		
		myV = new TextureRegion[3];
		for(int i = 0; i < 3; i++) {
			myV[i] = myArr[3][i];
		}
		fall = new Animation(rate, myV);
		fall.setPlayMode(Animation.LOOP);
		
		canJump = true;
		right = true;
		
		velocity = new Vector2();
		stat = State.STAND;
		time = 0;
	}

	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell(
				(int) (x / collisionLayer.getTileWidth()),
				(int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null;
				//&& cell.getTile().getProperties().containsKey("blocked");
	}

	private boolean collidesRight() {
		for (float step = 0; step < getHeight(); step += 1f)
			//step += collisionLayer.getTileHeight() / 2)
			if (isCellBlocked(getX() + getWidth(), getY() + step))
				return true;
		return false;
	}

	private boolean collidesLeft() {
		for (float step = 0; step < getHeight(); step += 1f)
			//step += collisionLayer.getTileHeight() / 2)
			if (isCellBlocked(getX(), getY() + step))
				return true;
		return false;
	}

	private boolean collidesTop() {
		for (float step = 0; step < getWidth(); step += 1f)
			//step += collisionLayer.getTileWidth() / 2)
			if (isCellBlocked(getX() + step, getY() + getHeight()))
				return true;
		return false;

	}

	private boolean collidesBottom() {
		for (float step = 0; step < getWidth(); step += 1f)
			//step += collisionLayer.getTileWidth() / 2)
			if (isCellBlocked(getX() + step, getY()))
				return true;
		return false;
	}

	public void update(float delta, float x, float y) {
		time += delta;
		velocity.y -= gravity * delta;

		//if (canJump) {
			//velocity.y = speed / 1.7f;
			//canJump = false;
		//} 
		if (x != 0) {
			velocity.x = speed * x;
		}
		
		// clamp velocity
		if (velocity.y > speed) {
			velocity.y = speed;
		} else if (velocity.y < -speed) {
			velocity.y = -speed;
		}
		// save old position
		float oldX = getX(), oldY = getY();
		boolean collisionX = false, collisionY = false;

		// move on x
		setX(getX() + velocity.x * delta);
		if (velocity.x < 0) {// going left
			collisionX = collidesLeft();
			right = false;
		} else if (velocity.x > 0) {// going right
			collisionX = collidesRight();
			right = true;
		} else {
			stat = State.STAND;
		}

		// react to x collision
		if (collisionX) {
			setX(oldX);
			velocity.x = 0;
		}

		// move on y
		setY(getY() + velocity.y * delta * 5f);

		if (velocity.y < 0) {// going down
			canJump = collisionY = collidesBottom();
			if (!canJump) {
				stat = State.FALL;
			}
		} else if (velocity.y > 0) {// going up
			collisionY = collidesTop();
			stat = State.JUMP;
		}
		// react to y collision
		if (collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
		velocity.x = 0;
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
	
	private float getY() {
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
		batch.draw(idle.getKeyFrame(time), X, Y, WIDTH, HEIGHT);
	}
	
}
