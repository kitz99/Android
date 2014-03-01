package com.room17.mygdxgame.generate;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class Maker {

	static ArrayList<Vector2> myArr;
	static int width = 100, height = 100, tile_width = 70, tile_height = 70;

	public static TiledMap generator() {
		TiledMap map = new TiledMap();
		MapLayers layers = map.getLayers();

		myArr = new ArrayList<Vector2>();

		TiledMapTileLayer layer1 = new TiledMapTileLayer(width, height,
				tile_width, tile_height);

		Texture a = new Texture("maps/boxEmpty.png");

		TextureRegion aux = new TextureRegion(a);
		int[][] mapArray = Generator.start();

		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (mapArray[i][j] == 0) {
					Cell cell = new Cell();
					cell.setTile(new StaticTiledMapTile(aux));
					layer1.setCell(i, j, cell);
				} else {
					myArr.add(new Vector2(i, j));
				}
			}
		}

		layers.add(layer1);
		return map;
	}

	public static Vector2 getPos() {
		int i = (int) (1 + (Math.random() * (myArr.size() - 1)));
		Vector2 aux = myArr.get(i);
		myArr.remove(i);
		return aux;
	}
}
