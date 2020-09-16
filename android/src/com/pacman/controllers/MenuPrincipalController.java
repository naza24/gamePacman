package com.pacman.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.badlogic.gdx.backends.android.AndroidApplication;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.pacman.AndroidLauncher;
import com.pacman.MainActivity;
import com.pacman.MainGame;
import com.pacman.R;
import com.pacman.config.Constantes;
import com.pacman.database.AppDatabase;
import com.pacman.entidades.Usuario;
import com.pacman.fragments.MenuPrincipalFragment;
//import com.example.android.jugadorandroid.R;

// implementar una clase abstracta , session de usuario que guarde una instancia del
// usuario logueado para que las  clases puedan acceder a ella y no tener variables publicas  y cosas feas
public class MenuPrincipalController extends AndroidApplication {


    // acceso a la bd
    private AppDatabase bd;

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

        // allow es para permitir varias consultas simultaneas a la bd sqlite
        this.bd = Room.databaseBuilder(miContexto, AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries().build();

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

    public void actualizarPuntaje(int pun) {
        // recupero el usuario para actualizar el puntaje en caso de que sea necesario
        Usuario userAux = bd.usuarioDao().getUsuario(this.idUsuarioLog);

        if(userAux.getPuntajeMaximo()<pun){
            userAux.setPuntajeMaximo(pun);
            // actualizo el usuario en la bd
            bd.usuarioDao().updateEntities(userAux);
            Toast.makeText(miContexto, "Se actualizo el puntaje", Toast.LENGTH_SHORT).show();
        }
    }

//    public void irJuego(){
//        // llama al juego en libgdx
//       Intent lanzadorJuego = new Intent(miContexto, AndroidLauncher.class);
//       // si lo incluyo en el controlador esoty tratando de lanzar un metodo de un activity desde un controlador , salta error
//       startActivity(lanzadorJuego);
//        }
    }
