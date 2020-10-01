package com.pacman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.pacman.MainGame;
import com.pacman.controllers.MenuPrincipalController;
import com.pacman.fragments.MenuPrincipalFragment;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class AndroidLauncher extends AndroidApplication implements MainGame.MyGameCallBack {

	private String idUsuario;
	private boolean sonido;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		SharedPreferences pref = getContext().getSharedPreferences("configPacman", Context.MODE_PRIVATE);

//		idUsuario="";

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

		game.setUsuario(idUsuario);
		game.setSonido(sonido);

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

		// capaz que hace falta poner tambien el usuario para mandarlo al menu principal
		// ver el tema de la flecha volver, la idea es que no pueda volver para el menu de game over
	}
}
