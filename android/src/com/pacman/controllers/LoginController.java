package com.pacman.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.pacman.R;
import com.pacman.config.Constantes;
import com.pacman.database.AppDatabase;
import com.pacman.entidades.Usuario;
import com.pacman.fragments.LoginFragment;


public class LoginController {

    // acceso a la bd
    private AppDatabase bd;

    // navigation
    private final NavController navController;

    private LoginFragment miVista;

    public LoginController(LoginFragment vista) {

        // referencia a la vista
        miVista = vista;

        /* instanciar el acceso a la bd, le pasamos el contexto de la aplicacion,
       la clase q se encarga de crear el acceso y el nombre de la bd*/

        // allow es para permitir varias consultas simultaneas a la bd sqlite
        this.bd = Room.databaseBuilder(miVista.getContext(), AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries().build();

        // inicializo el navegador
        navController = Navigation.findNavController(miVista.getView());

    }

    public void loguearUsuario() {

        // variable auxiliares
        String nombreAux = miVista.getNombre();
        String contraseniaAux = miVista.getContraseña();

        if (existeUsuarioContrasenia(nombreAux, contraseniaAux)) {
            miVista.mostrarText("LOGEO correctamente: " + nombreAux);
            miVista.vaciarNombre();

            irMenuPrincipal(nombreAux);

        } else {
            miVista.mostrarText(" usuario o contrasenia no existe ");
       }
        miVista.vaciarContraseña();
    }

    // Este metodo retorna true si el usuario con su respectiva contraseña existe, de lo contrario false
    private boolean existeUsuarioContrasenia(String usuario, String contrasenia) {
        boolean retorno = true;

        // si no existe el usuario retorna false
        if (bd.usuarioDao().existe2(usuario, contrasenia) == 0) {
            retorno = false;
        }
        return retorno;
    }

    private void irMenuPrincipal(String nombre) {
        // simplemente deriba al llamador a la pantalla de menu principal y le paso el usuario q logueo
        Bundle arg = new Bundle();
        arg.putString("usuario", nombre);

        // creo el archivo de sharedPreferences de la app con nombre configPacman y el modo private para q solo la app pueda leerlo
        SharedPreferences config = miVista.getContext().getSharedPreferences("configPacman", Context.MODE_PRIVATE);
        // editor del archivo
        SharedPreferences.Editor editor = config.edit();
        editor.putBoolean("sonido", miVista.sonidoOn());
        editor.apply();     // actualizo los valores

        navController.navigate(R.id.menuPrincipalFragment, arg);
    }

    public void irPantallaRegistro() {
        // simplemente deriba al llamador a la pantalla de registro
        navController.navigate(R.id.registroFragment);
    }
}