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
	private LoadingScreen loadingScreen;

	// creamos un administrador de recursos para cargar las texturas y demas assets

	private AssetManager manager;
	@Override
	public void create () {
		this.gameScreen = new GameScreen(this);
		this.manager = new AssetManager();

		// cargamos los recursos

		// Textura de pacman
		manager.load("datos/pac_man_0.png",Texture.class);
		manager.load("datos/pac_man_1.png",Texture.class);
		manager.load("datos/pac_man_2.png",Texture.class);
		manager.load("datos/pac_man_muerte0.png",Texture.class);
		manager.load("datos/pac_man_muerte1.png",Texture.class);

		// Texturas de los controles
		manager.load("datos/flechaArribaBlanca.png",Texture.class);
		manager.load("datos/flechaIzquierdaBlanca.png",Texture.class);
		manager.load("datos/flechaDerechaBlanca.png",Texture.class);
		manager.load("datos/flechaAbajoBlanca.png",Texture.class);

		// textura de los fantasmas
		manager.load("datos/red ghost/red_ghost_derecha_0.png",Texture.class);
		manager.load("datos/red ghost/red_ghost_derecha_1.png",Texture.class);
		manager.load("datos/red ghost/red_ghost_abajo_0.png",Texture.class);
		manager.load("datos/red ghost/red_ghost_abajo_1.png",Texture.class);
		manager.load("datos/red ghost/red_ghost_izquierda_0.png",Texture.class);
		manager.load("datos/red ghost/red_ghost_izquierda_1.png",Texture.class);
		manager.load("datos/red ghost/red_ghost_arriba_0.png",Texture.class);
		manager.load("datos/red ghost/red_ghost_arriba_1.png",Texture.class);

		manager.load("datos/blue ghost/blue_ghost_derecha_0.png",Texture.class);
		manager.load("datos/blue ghost/blue_ghost_derecha_1.png",Texture.class);
		manager.load("datos/blue ghost/blue_ghost_abajo_0.png",Texture.class);
		manager.load("datos/blue ghost/blue_ghost_abajo_1.png",Texture.class);
		manager.load("datos/blue ghost/blue_ghost_izquierda_0.png",Texture.class);
		manager.load("datos/blue ghost/blue_ghost_izquierda_1.png",Texture.class);
		manager.load("datos/blue ghost/blue_ghost_arriba_0.png",Texture.class);
		manager.load("datos/blue ghost/blue_ghost_arriba_1.png",Texture.class);

		manager.load("datos/pink ghost/pink_ghost_derecha_0.png",Texture.class);
		manager.load("datos/pink ghost/pink_ghost_derecha_1.png",Texture.class);
		manager.load("datos/pink ghost/pink_ghost_abajo_0.png",Texture.class);
		manager.load("datos/pink ghost/pink_ghost_abajo_1.png",Texture.class);
		manager.load("datos/pink ghost/pink_ghost_izquierda_0.png",Texture.class);
		manager.load("datos/pink ghost/pink_ghost_izquierda_1.png",Texture.class);
		manager.load("datos/pink ghost/pink_ghost_arriba_0.png",Texture.class);
		manager.load("datos/pink ghost/pink_ghost_arriba_1.png",Texture.class);

		manager.load("datos/orange ghost/orange_ghost_derecha_0.png",Texture.class);
		manager.load("datos/orange ghost/orange_ghost_derecha_1.png",Texture.class);
		manager.load("datos/orange ghost/orange_ghost_abajo_0.png",Texture.class);
		manager.load("datos/orange ghost/orange_ghost_abajo_1.png",Texture.class);
		manager.load("datos/orange ghost/orange_ghost_izquierda_0.png",Texture.class);
		manager.load("datos/orange ghost/orange_ghost_izquierda_1.png",Texture.class);
		manager.load("datos/orange ghost/orange_ghost_arriba_0.png",Texture.class);
		manager.load("datos/orange ghost/orange_ghost_arriba_1.png",Texture.class);

		manager.load("datos/afraid ghost/afraid_ghost_0.png",Texture.class);
		manager.load("datos/afraid ghost/afraid_ghost_1.png",Texture.class);
		manager.load("datos/afraid ghost/afraid_ghost_2.png",Texture.class);
		manager.load("datos/afraid ghost/afraid_ghost_3.png",Texture.class);
		manager.load("datos/dead ghost/dead_ghost_derecha.png",Texture.class);
		manager.load("datos/dead ghost/dead_ghost_abajo.png",Texture.class);
		manager.load("datos/dead ghost/dead_ghost_izquierda.png",Texture.class);
		manager.load("datos/dead ghost/dead_ghost_arriba.png",Texture.class);


		// cargamos los recursos
	//	manager.finishLoading();

	//	setScreen(gameScreen);

	//}
	// creo las pantallas
	loadingScreen = new LoadingScreen(this);
	setScreen(loadingScreen);

}
	public void terminarDeCargar (){
	//	gameOverScreen = new GameOverScreen(this);
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	//Metodos observadores
	public AssetManager getAssetManager() {
		return manager;
	}
}
