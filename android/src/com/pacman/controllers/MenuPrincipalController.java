package com.pacman.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.pacman.AndroidLauncher;
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

        if(pun > userAux.getPuntajeMaximo()){
          if(bd.usuarioDao().updateUsuarioPuntaje(pun,idUsuarioLog)>0){
                miVista.mostrarText("Se actualizo el puntaje");
            }
        }
    }

    public void lanzarJuego() {

        Intent lanzadorJuego = new Intent(miVista.getActivity(), AndroidLauncher.class);
        // Le mando el usuario al juego
        Bundle bun = new Bundle();
        bun.putString("usuario",idUsuarioLog);
        lanzadorJuego.putExtras(bun);
        /*SharedPreferences config = miVista.getContext().getSharedPreferences("configPacman", Context.MODE_PRIVATE);

        Toast.makeText(miVista.getContext(), "sonido: "+config.getBoolean("sonido", false), Toast.LENGTH_SHORT).show();
*/
        miVista.startActivity(lanzadorJuego);

        // finalizo la mainActivity antes de lanzar el juego
        miVista.getActivity().finish();
    }
}
