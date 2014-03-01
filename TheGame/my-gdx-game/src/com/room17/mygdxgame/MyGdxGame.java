package com.room17.mygdxgame;

import com.badlogic.gdx.Game;
public class MyGdxGame extends Game {
	
	@Override
	public void create() {		
		this.setScreen(new Splash());
	}

	@Override
	public void dispose() {
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
