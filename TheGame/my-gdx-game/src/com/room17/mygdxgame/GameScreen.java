package com.room17.mygdxgame;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.room17.mygdxgame.entity.Heart;
import com.room17.mygdxgame.entity.Player;
import com.room17.mygdxgame.generate.Maker;
import com.room17.mygdxgame.logic.Ctrl;

public class GameScreen implements Screen {

	private int score;
	private String yourScoreName;
	BitmapFont font;
	Label text;
	LabelStyle textStyle;
	private SpriteBatch batch;
	private Texture bground;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Ctrl myCtrl;
	private Stage stage;

	private Button btn1;
	// private Button btn2;

	private float animeTime;
	private Sprite sprite;

	private Player play;
	private ArrayList<Heart> myV;

	@Override
	public void render(float delta) {
		float d = Gdx.graphics.getDeltaTime();
		animeTime += 0.0014f;
		if (animeTime > 1f)
			animeTime = 0.0f;
		Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		play.update(d, myCtrl.getX(), myCtrl.getY(), btn1.isPressed());
		batch.begin();
		batch.disableBlending();
		sprite.setU(animeTime);
		sprite.setU2(animeTime + 1);
		sprite.draw(batch);
		batch.enableBlending();
		batch.end();
		Iterator<Heart> it = myV.iterator();
		while (it.hasNext()) {
			Heart aux = it.next();
			aux.update(delta);
			if (aux.col(play.getRect())) {
				it.remove();
				score++;
				yourScoreName = "score: " + score;
				text.setText(yourScoreName);
			}
		}
		camera.position.x = play.getX();
		camera.position.y = play.getY();
		camera.update();

		renderer.setView(camera);
		renderer.render();

		renderer.getSpriteBatch().begin();
		play.draw(renderer.getSpriteBatch());
		it = myV.iterator();
		while (it.hasNext()) {
			Heart aux = it.next();
			aux.draw(renderer.getSpriteBatch());
		}
		renderer.getSpriteBatch().end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		font = new BitmapFont(Gdx.files.internal("menu/arialblack.fnt"),
				Gdx.files.internal("menu/arialblack.png"), false);
		score = 0;
		yourScoreName = "score: 0";
		textStyle = new LabelStyle();
		textStyle.font = font;
		text = new Label(yourScoreName, textStyle);
		text.setBounds(10, Gdx.graphics.getHeight() - 20, 0, 0);
		text.setColor(Color.DARK_GRAY);
		batch = new SpriteBatch();
		bground = new Texture("maps/background.png");
		bground.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		map = Maker.generator();
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 20 * 70, 10 * 70);
		camera.update();
		sprite = new Sprite(bground, 0, 0, bground.getWidth(),
				Gdx.graphics.getHeight());
		myCtrl = new Ctrl();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true, renderer.getSpriteBatch());
		stage.addActor(myCtrl.getTouch());
		stage.addActor(text);
		Gdx.input.setInputProcessor(stage);
		Vector2 loc = Maker.getPos();
		play = new Player(loc.x * 70, loc.y * 70, (TiledMapTileLayer) map
				.getLayers().get(0));

		myV = new ArrayList<Heart>();
		for (int i = 0; i < 50; i++) {
			loc = Maker.getPos();
			myV.add(new Heart(loc.x * 70, loc.y * 70));
		}
		Skin a = new Skin();
		a.add("a", new Texture("sprites/A.png"));
		// a.add("b", new Texture("sprites/B.png"));
		btn1 = new Button(a.getDrawable("a"));

		// btn2 = new Button(a.getDrawable("b"), a.getDrawable("a"));
		btn1.setPosition(Gdx.graphics.getWidth() - 100, 25);
		// btn2.setPosition(700, 25);
		stage.addActor(btn1);
		// stage.addActor(btn2);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		stage.dispose();
		myCtrl.dispose();
	}

}
