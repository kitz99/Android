package com.room17.mygdxgame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
	
	private Texture pText;

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

	private boolean isBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell(
				(int) (x / collisionLayer.getTileWidth()),
				(int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null;
				//&& cell.getTile().getProperties().containsKey("blocked");
	}

	private boolean rightCol() {
		for (float step = 0; step < getHeight(); step += 1f)
			if (isBlocked(getX() + getWidth(), getY() + step))
				return true;
		return false;
	}

	private boolean leftCol() {
		for (float step = 0; step < getHeight(); step += 1f)
			if (isBlocked(getX(), getY() + step))
				return true;
		return false;
	}

	private boolean upCol() {
		for (float step = 0; step < getWidth(); step += 1f)
			if (isBlocked(getX() + step, getY() + getHeight()))
				return true;
		return false;

	}

	private boolean downCol() {
		for (float step = 0; step < getWidth(); step += 1f)
			if (isBlocked(getX() + step, getY()))
				return true;
		return false;
	}

	public void update(float delta, float x, float y) {
		time += delta;
		velocity.y -= gravity * delta;

		if (canJump && Gdx.input.isKeyPressed(Keys.SPACE)) {
			velocity.y = speed / 1.7f;
			canJump = false;
		} 
		if (x != 0) {
			velocity.x = speed * x;
		}
		
		if (velocity.y > speed) {
			velocity.y = speed;
		} else if (velocity.y < -speed) {
			velocity.y = -speed;
		}

		float oldX = getX(), oldY = getY();
		boolean collisionX = false, collisionY = false;
		float a = velocity.x, b = velocity.y;

		setX(getX() + velocity.x * delta);
		if (velocity.x < 0) {
			collisionX = leftCol();
		} else if (velocity.x > 0) {
			collisionX = rightCol();
		}
		
		if (collisionX) {
			setX(oldX);
			velocity.x = 0;
		}

		setY(getY() + velocity.y * delta * 5f);

		if (velocity.y < 0) {
			canJump = collisionY = downCol();
		} else if (velocity.y > 0) {
			collisionY = upCol();
		}

		if (collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
		velocity.x = 0;
		
		if (a > 0 && canJump && b < 0) {
			if (stat != State.WALK || !right) {
				stat = State.WALK;
				right = true;
				time = 0;
			}
		}
		if (a < 0 && canJump && b < 0) {
			if (stat != State.WALK || right) {
				stat = State.WALK;
				right = false;
				time = 0;
			}
		}
		//if (b < 0 && !canJump) {
		//	if (stat != State.FALL) {
		//		time = 0;
		//		stat = State.FALL;
		//	}
		//	if (a != 0) {
		//		right = (a > 0);
		//	}
		//}
		if (b > 0) {
			if (stat != State.JUMP) {
				time = 0;
				stat = State.JUMP;
			}
			if (a != 0) {
				right = (a > 0);
			}
		}
		if (canJump && a == 0 && stat != State.STAND) {
			time = 0;
			stat = State.STAND;
		}
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
		TextureRegion frame = null;
		switch(stat) {
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
		}
		if(right) {
			batch.draw(frame, X, Y, WIDTH, HEIGHT);
		} else {
			batch.draw(frame, X + WIDTH, Y, -WIDTH, HEIGHT);
		}
	}
	
}
