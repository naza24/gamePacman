package com.pacman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.pacman.Pantallas.GameOverScreen;
import com.pacman.Pantallas.GameScreen;
import com.pacman.Pantallas.LoadingScreen;


public class MainGame extends Game {
// Game se usa para poder hacer juegos de pantallas multiples, que extiende a AplicationAdapter

	// defino la interfaz de callback (llamada de retorno) al android launcher
	public interface MyGameCallBack{
		public void volverMenuPrincipal(String usuario, int puntaje);
	}

	/*variable local q contendra una referencia a callback*/
	private MyGameCallBack myGameCallBack;

	// agregamos un seter para que desde la app se pueda setear un callback concreto
	public void setMyGameCallBack(MyGameCallBack myCall){
		myGameCallBack=myCall;
	}

	// agregamos un getter para poder tener acceso a la interfaz desde gameOverScreen
	public MyGameCallBack getMyGameCallBack(){
		return myGameCallBack;
	}

	// instancia principal a la pantalla de juego
	public GameScreen gameScreen;

	// creo la pantalla de juego perdido
	public GameOverScreen gameOverScreen;

	// pantalla de carga
	public LoadingScreen loadingScreen;

	// creamos un administrador de recursos para cargar las texturas y demas assets

	private AssetManager manager;

	// player que esta jugando
	private String idPlayer;

	private int puntajePlayer;

	@Override
	public void create () {
		manager = new AssetManager();
		puntajePlayer=0;

		// cargamos los recursos

		// Textura de pacman
		manager.load("datos/pac_man_0.png",Texture.class);
		manager.load("datos/pac_man_1.png",Texture.class);
		manager.load("datos/pac_man_2.png",Texture.class);
		manager.load("datos/dead pacman/dead_pacman_0.png",Texture.class);
		manager.load("datos/dead pacman/dead_pacman_1.png",Texture.class);
		manager.load("datos/dead pacman/dead_pacman_2.png",Texture.class);
		manager.load("datos/dead pacman/dead_pacman_3.png",Texture.class);
		manager.load("datos/dead pacman/dead_pacman_4.png",Texture.class);
		manager.load("datos/dead pacman/dead_pacman_5.png",Texture.class);
		manager.load("datos/dead pacman/dead_pacman_6.png",Texture.class);
		manager.load("datos/dead pacman/dead_pacman_7.png",Texture.class);
		manager.load("datos/dead pacman/dead_pacman_8.png",Texture.class);
		manager.load("datos/dead pacman/dead_pacman_9.png",Texture.class);
		manager.load("datos/dead pacman/dead_pacman_10.png",Texture.class);

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

	// creo las pantallas, inicialmente creo la de carga
	loadingScreen = new LoadingScreen(this);
	setScreen(loadingScreen);

}

	public void terminarDeCargar (){
		this.gameOverScreen = new GameOverScreen(this);
		this.gameScreen = new GameScreen(this);
		// setear el player al game screen aca , gamescreen tendra PLAYER Y SCORE
		// se lo pasara al gameover y en caso de apretar retry el score se volvera a 0
		setScreen(gameScreen);
	}

	public void irGameOver(){
		this.setScreen(gameOverScreen);
	}

	//Metodos observadores
	public AssetManager getAssetManager() {
		return manager;
	}

	public String getUsuario() {return idPlayer; }

	public int getPuntajePlayer() { return puntajePlayer; }

	//Metodos Modificadores

	public void setUsuario(String idUsuario) {
		idPlayer =idUsuario;
	}

	public void setPuntajePlayer(int puntajePlayer) {
		this.puntajePlayer = puntajePlayer;
	}
}
