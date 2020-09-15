package com.pacman.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.badlogic.gdx.backends.android.AndroidApplication;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.pacman.AndroidLauncher;
import com.pacman.MainActivity;
import com.pacman.MainGame;
import com.pacman.R;
import com.pacman.fragments.MenuPrincipalFragment;
//import com.example.android.jugadorandroid.R;

// implementar una clase abstracta , session de usuario que guarde una instancia del
// usuario logueado para que las  clases puedan acceder a ella y no tener variables publicas  y cosas feas
public class MenuPrincipalController extends AndroidApplication {

    // contexto de la aplicacion
    private Context miContexto;

    // navigation
    private final NavController navController;

    //guardo el id de usuario
    private String idUsuarioLog;


    public MenuPrincipalController(Context context, View vista, String user){
        this.miContexto = context;

        // inicializo el navegador
        navController = Navigation.findNavController(vista);

        // referencia al id de usuario
        idUsuarioLog = user;

    }


    public void irRanking(){
        // simplemente deriba al llamador a la pantalla de ranking
        navController.navigate(R.id.rankingFragment);
    }

    public void irBorrado(){
        // simplemente deriba al llamador a la pantalla de borrar y le paso el id de usuario
        Bundle arg = new Bundle();
        arg.putString("usuario", idUsuarioLog);
        navController.navigate(R.id.borrarFragment,arg);
    }

    public void irLogin(){
        // simplemente deriba al llamador a la pantalla login
        navController.navigate(R.id.loginFragment);
    }

//    public void irJuego(){
//        // llama al juego en libgdx
//       Intent lanzadorJuego = new Intent(miContexto, AndroidLauncher.class);
//       // si lo incluyo en el controlador esoty tratando de lanzar un metodo de un activity desde un controlador , salta error
//       startActivity(lanzadorJuego);
//        }
    }
