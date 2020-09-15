package com.pacman.controllers;

import android.content.Context;
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


public class LoginController {

    // acceso a la bd
    private AppDatabase bd;

    // contexto de la aplicacion
    private Context miContexto;

    // navigation
    private final NavController navController;

    private EditText nombre, contrasenia;

    public LoginController(Context context, View vista){
        this.miContexto = context;

        /* instanciar el acceso a la bd, le pasamos el contexto de la aplicacion,
       la clase q se encarga de crear el acceso y el nombre de la bd*/

        // allow es para permitir varias consultas simultaneas a la bd sqlite
        this.bd = Room.databaseBuilder(miContexto, AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries().build();

        // inicializo el navegador
        navController = Navigation.findNavController(vista);

        nombre = (EditText) vista.findViewById(R.id.etUsuario);
        contrasenia = (EditText) vista.findViewById(R.id.etContrasenia);


    }

    public void loguearUsuario (){

        // variable auxiliares
        String nombreAux = nombre.getText().toString();
        String contraseniaAux = contrasenia.getText().toString();

        if(existeUsuarioContrasenia(nombreAux, contraseniaAux)) {

            irMenuPrincipal(nombreAux);
            Toast.makeText(miContexto, " LOGEO correctamente: " + nombre, Toast.LENGTH_LONG).show();
            nombre.setText("");
            contrasenia.setText("");
        }else{
            Toast.makeText(miContexto, ""+nombre+", usuario o contrasenia no existe", Toast.LENGTH_LONG).show();
            contrasenia.setText("");
        }
    }

    // Este metodo retorna true si el usuario con su respectiva contraseña existe, de lo contrario false
    private boolean existeUsuarioContrasenia(String usuario, String contrasenia){
        boolean retorno =true;

        // si no existe el usuario retorna false
        if(bd.usuarioDao().existe2(usuario,contrasenia)==0){
            retorno = false;
        }
        return retorno;
    }

    private void irMenuPrincipal(String nombre){
        // simplemente deriba al llamador a la pantalla de menu principal y le paso el usuario q logueo
        Bundle arg = new Bundle();
        arg.putString("usuario",nombre);

        navController.navigate(R.id.menuPrincipalFragment, arg);
    }


    public void irPantallaRegistro(){
        // simplemente deriba al llamador a la pantalla de registro
        navController.navigate(R.id.registroFragment);
    }

//    // recupera los campos y los asigna a sus lugares
//    public void restaurarCampos(Bundle savedInstanceState) {
//        // si el bundle no esta vacio es porq se guardo algo.
//        if (savedInstanceState!=null){
//            this.nombre.setText( savedInstanceState.getString("etUsuario",""));
//            this.contrasenia.setText(savedInstanceState.getString("etContrasenia",""));
//
//        }
//    }

    // este metodo se encarga de guardar los campos que quiero que se conserven
//    public void guardarCampos(Bundle outState) {
//
//        String nomb= nombre.getText().toString();
//        String contr= contrasenia.getText().toString();
//
//        outState.putString("etUsuario", nomb);
//        outState.putString("etContrasenia", contr);
//    }
}
