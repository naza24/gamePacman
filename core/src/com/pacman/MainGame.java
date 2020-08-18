package com.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class MainGame extends Game {
// Game se usa para poder hacer juegos de pantallas multiples, que extiende a AplicationAdapter

	// instancia principal a la pantalla de juego
	private GameScreen gameScreen;

	// creamos un administrador de recursos para cargar las texturas y demas assets

	private AssetManager manager;
	@Override
	public void create () {
		this.gameScreen = new GameScreen(this);
		this.manager = new AssetManager();

		// cargamos los recursos
//		manager.load("datos/pacman32x32.png",Texture.class);
		manager.load("datos/pac_man_0.png",Texture.class);
		manager.load("datos/pac_man_1.png",Texture.class);
		manager.load("datos/pac_man_2.png",Texture.class);
		manager.load("datos/pac_man_muerte0.png",Texture.class);
		manager.load("datos/pac_man_muerte1.png",Texture.class);
		manager.load("datos/flechaArribaBlanca.png",Texture.class);
		manager.load("datos/flechaIzquierdaBlanca.png",Texture.class);
		manager.load("datos/flechaDerechaBlanca.png",Texture.class);
		manager.load("datos/flechaAbajoBlanca.png",Texture.class);
		manager.load("datos/red ghost/red_ghost_derecha_0.png",Texture.class);
		manager.load("datos/red ghost/red_ghost_derecha_1.png",Texture.class);

		// cargamos los recursos
		manager.finishLoading();

//		while(!this.getAssetManager().update()){

//		}
		setScreen(gameScreen);

	}

	//Metodos observadores
	public AssetManager getAssetManager() {
		return manager;
	}
}
