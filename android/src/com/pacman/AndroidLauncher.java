package com.pacman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication implements MainGame.MyGameCallBack {

	private String idUsuario;
	private boolean sonido;
	private String idioma;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		SharedPreferences pref = getContext().getSharedPreferences("configPacman", Context.MODE_PRIVATE);

		// creamos una instancia de juego y le seteamos la callBack
		MainGame game = new MainGame();

		// como androidLauncher implementa la interfaz solo le pasamos la referencia a la clase
		game.setMyGameCallBack(this);

		// recupero el idUsuario y se lo asigno al MainGame
		Bundle bundle = getIntent().getExtras();

		if(bundle!=null) {
		   idUsuario = bundle.getString("usuario");
		}
		sonido = pref.getBoolean("sonido", false);
		idioma = pref.getString("idioma","english");

		game.setUsuario(idUsuario);
		game.setSonido(sonido);
		game.setIdioma(idioma);

		// lanzo el juego
		initialize(game, config);
	}

	@Override
	public void volverMenuPrincipal(String idUsuario, int puntaje) {

		// lanzo una activity principal
		Intent intent = new Intent(this, MainActivity.class);

		Bundle arg = new Bundle();

		arg.putInt("puntaje", puntaje);
		arg.putString("usuario", idUsuario);

		intent.putExtras(arg);
		startActivity(intent);

		// finalizo la activite que esta corriendo , osea el juego de pacman
		finish();

	}
}
