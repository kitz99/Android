package com.room17.mygdxgame;

import com.badlogic.gdx.Game;
import com.room17.mygdxgame.testing.Test;

public class MyGame extends Game {
	
	@Override
	public void create() {
		this.setScreen(new Test());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
