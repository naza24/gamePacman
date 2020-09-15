package com.pacman.controllers;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.pacman.config.Constantes;
import com.pacman.R;
import com.pacman.database.AppDatabase;
import com.pacman.entidades.Usuario;


//import com.example.android.jugadorandroid.R;
//import com.example.android.jugadorandroid.config.Constantes;
//import com.example.android.jugadorandroid.database.AppDatabase;
//import com.example.android.jugadorandroid.entidades.Usuario;

public class RegistroController {

    // acceso a la bd
    private AppDatabase bd;

    // contexto de la aplicacion
    private  Context miContexto;

    // navigation
    private final NavController navController;

    private EditText nombre, contrasenia;


    public RegistroController(Context context, View vista){
        this.miContexto = context;

        /* instanciar el acceso a la bd, le pasamos el contexto de la aplicacion,
       la clase q se encarga de crear el acceso y el nombre de la bd*/

        // allow es para permitir varias consultas simultaneas a la bd sqlite
        this.bd = Room.databaseBuilder(miContexto, AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries().build();

        // inicializo el navegador
        navController = Navigation.findNavController(vista);

        // inicializo los campos editext, los inicializo aca por que cuando creo la vista los campos van a estar vacios
       nombre = (EditText) vista.findViewById(R.id.etUsuario);
       contrasenia = (EditText) vista.findViewById(R.id.etContrasenia);
    }

    // si lo registra bien muestra un toast y redirige a el fragment de menu principal , sino larga un toast y se queda

    public void registrarUsuario(){
    // creamos el usuario que luego guardaremos
        Usuario obj = new Usuario();
        obj.setNombre(nombre.getText().toString());
        obj.setContrasenia(contrasenia.getText().toString());
        obj.setPuntajeMaximo(0);

        if(!existeUsuario(obj.getNombre())){
            // recordar que si es 1 inserto bien si es menor es debido a un error
            long codigoRetorno = bd.usuarioDao().insert(obj);

            if(codigoRetorno > 0){
                navController.navigate(R.id.loginFragment);
                Toast.makeText(miContexto, " Se inserto correctamente: "+nombre, Toast.LENGTH_LONG ).show();
                Toast.makeText(miContexto, " Ingrese los datos del Registro", Toast.LENGTH_SHORT ).show();
            }else{
                Toast.makeText(miContexto, " no se a podido insertar: "+nombre, Toast.LENGTH_LONG ).show();
            }
        }else{
            Toast.makeText(miContexto, ""+nombre+", ya existe en la bd", Toast.LENGTH_LONG ).show();
        }

        nombre.setText("");
        contrasenia.setText("");
    }

    private boolean existeUsuario(String usuario){

        boolean retorno =true;

        // si no existe el usuario retorna false
        if(bd.usuarioDao().existe(usuario)==0){
            retorno = false;
        }
        return retorno;
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
//    public void guardCampos(Bundle outState) {
//
//        String nom= nombre.getText().toString();
//        String cont= contrasenia.getText().toString();
//
//        outState.putString("etUsuario", nom);
//        outState.putString("etContrasenia", cont);
//    }
}
