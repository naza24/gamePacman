package com.pacman.controllers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.pacman.R;
//import com.example.android.jugadorandroid.R;

// implementar una clase abstracta , session de usuario que guarde una instancia del
// usuario logueado para que las  clases puedan acceder a ella y no tener variables publicas  y cosas feas
public class MenuPrincipalController {

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

}
