package com.room17.mygdxgame.testing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.room17.mygdxgame.entity.Player;
import com.room17.mygdxgame.logic.Ctrl;

public class Test implements Screen {
	private SpriteBatch batch;
	private Texture bground;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Ctrl myCtrl;
	private Stage stage;
	private Player play;

	private Button btn1;
	private Button btn2;
	

	@Override
	public void render(float delta) {
		float d = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.disableBlending();
		batch.draw(bground, -play.getX(), 0);
		batch.enableBlending();
		batch.end();
		
		play.update(d, myCtrl.getX(), myCtrl.getY(), btn1.isPressed(),
				btn2.isPressed());
		
		camera.position.x = play.getX();
		//camera.position.y = play.getY();
		camera.update();
		

		renderer.setView(camera);
		renderer.render();

		renderer.getSpriteBatch().begin();
		play.draw(renderer.getSpriteBatch());
		renderer.getSpriteBatch().end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		bground = new Texture("maps/background.png");
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
		play = new Player(32, 64, (TiledMapTileLayer) map.getLayers().get(0));

		Skin a = new Skin();
		a.add("a", new Texture("sprites/A.png"));
		a.add("b", new Texture("sprites/B.png"));
		btn1 = new Button(a.getDrawable("a"), a.getDrawable("b"));

		btn2 = new Button(a.getDrawable("b"), a.getDrawable("a"));
		btn1.setPosition(600, 25);
		btn2.setPosition(700, 25);
		stage.addActor(btn1);
		stage.addActor(btn2);
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
