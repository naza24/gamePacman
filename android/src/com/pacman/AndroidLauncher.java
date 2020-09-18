package com.pacman;

import android.content.Intent;
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
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// creamos una instancia de juego y le seteamos la callBack
		MainGame game = new MainGame();

		// como androidLauncher implementa la interfaz solo le pasamos la referencia a la clase
		game.setMyGameCallBack(this);

		initialize(game, config);
	}

	@Override
	public void volverMenuPrincipal(int puntaje) {
		Intent intent = new Intent(this, MainActivity.class);
		Bundle arg = new Bundle();
		arg.putInt("puntaje", puntaje);
		intent.putExtras(arg);
		startActivity(intent);

		// capaz que hace falta poner tambien el usuario para mandarlo al menu principal
		// ver el tema de la flecha volver, la idea es que no pueda volver para el menu de game over
	}
}
