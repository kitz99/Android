package com.room17.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MainMenu implements Screen {

	private Texture bck;
	private Texture play;
	private Sprite background;
	private Stage stage;
	private ImageButton playButton;
	// private OrthographicCamera camera;
	private SpriteBatch batch;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (playButton.isPressed()) {
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new GameScreen());
		}
		batch.begin();
		background.draw(batch);
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		bck = new Texture(Gdx.files.internal("menu/background.png"));
		background = new Sprite(bck, 0, 0, bck.getWidth(),
				Gdx.graphics.getHeight());
		play = new Texture(Gdx.files.internal("menu/playButton.png"));
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true, batch);
		// camera = new OrthographicCamera();
		// camera.setToOrtho(false, 30 * 16, 20 * 16);
		// camera.update();
		Skin a = new Skin();
		a.add("A", play);
		a.add("a", play);
		playButton = new ImageButton(a.getDrawable("A"), a.getDrawable("a"));
		playButton.setPosition(Gdx.graphics.getWidth() / 2 - play.getWidth()
				/ 2, Gdx.graphics.getHeight() / 2 - play.getHeight() / 2);
		stage.addActor(playButton);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		bck.dispose();
		play.dispose();
		batch.dispose();
	}

}
