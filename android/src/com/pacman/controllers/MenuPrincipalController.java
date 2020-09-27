package com.pacman.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.room.Room;

import com.badlogic.gdx.backends.android.AndroidApplication;

import com.pacman.AndroidLauncher;
import com.pacman.MainActivity;
import com.pacman.R;
import com.pacman.config.Constantes;
import com.pacman.database.AppDatabase;
import com.pacman.entidades.Usuario;
import com.pacman.fragments.MenuPrincipalFragment;

// implementar una clase abstracta , session de usuario que guarde una instancia del
// usuario logueado para que las  clases puedan acceder a ella y no tener variables publicas  y cosas feas
public class MenuPrincipalController extends AndroidApplication {


    // acceso a la bd
    private AppDatabase bd;

    // navigation
    private final NavController navController;

    //guardo el id de usuario
    private String idUsuarioLog;

    private MenuPrincipalFragment miVista;

    public MenuPrincipalController(MenuPrincipalFragment  vista){

        miVista= vista;

        // inicializo el navegador
        navController = Navigation.findNavController(miVista.getView());

        // allow es para permitir varias consultas simultaneas a la bd sqlite
        this.bd = Room.databaseBuilder(miVista.getContext(), AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries().build();

        idUsuarioLog = miVista.getUsuarioLogeado();
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
//        System.out.println("usuario a actualizar puntaje "+userAux.getNombre()+" con: "+userAux.getContrasenia()+" puntaje: "+userAux.getPuntajeMaximo());

        if(pun > userAux.getPuntajeMaximo()){
          /*  userAux.setPuntajeMaximo(pun);
            System.out.println("puntaje del usuario luego del set: "+userAux.getPuntajeMaximo());*/
            // actualizo el usuario en la bd
           // System.out.println("usuario "+idUsuarioLog+" puntaje: "+pun);
//            if(bd.usuarioDao().updateUsuario(userAux)>0){
                if(bd.usuarioDao().updateUsuarioPuntaje(pun,idUsuarioLog)>0){
                miVista.mostrarText("Se actualizo el puntaje");
            }/*
            Usuario userAux2 = bd.usuarioDao().getUsuario(this.idUsuarioLog);
            System.out.println("usuario actualizado "+userAux2.getNombre()+" con: "+userAux2.getContrasenia()+" puntaje: "+userAux2.getPuntajeMaximo());*/
        }
    }

    public void lanzarJuego() {

        Intent lanzadorJuego = new Intent(miVista.getActivity(), AndroidLauncher.class);
        // Le mando el usuario al juego
        Bundle bun = new Bundle();
        bun.putString("usuario",idUsuarioLog);
        Toast.makeText(miVista.getContext(), "idUsuario : "+ idUsuarioLog , Toast.LENGTH_SHORT).show();
        lanzadorJuego.putExtras(bun);

        miVista.startActivity(lanzadorJuego);

        // finalizo la mainActivity antes de lanzar el juego
        miVista.getActivity().finish();    }
}
