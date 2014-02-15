package com.room17.mygdxgame.testing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.room17.mygdxgame.entity.Player;
import com.room17.mygdxgame.logic.Ctrl;

public class Test implements Screen {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Ctrl myCtrl;
	private Stage stage;
	private Player play;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float d = Gdx.graphics.getDeltaTime();
		camera.position.x = play.getX();
		camera.update();

		renderer.setView(camera);
		renderer.render();
		
		renderer.getSpriteBatch().begin();
		play.draw(renderer.getSpriteBatch());
		renderer.getSpriteBatch().end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		System.out.println(d);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30 * 16, 20 * 16);
		camera.update();
		myCtrl = new Ctrl();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true, renderer.getSpriteBatch());
		stage.addActor(myCtrl.getTouch());
		Gdx.input.setInputProcessor(stage);
		play = new Player(32, 32, (TiledMapTileLayer) map.getLayers().get(0));
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
		map.dispose();
		renderer.dispose();
		stage.dispose();
		myCtrl.dispose();
	}

}
