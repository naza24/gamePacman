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
import com.pacman.fragments.RegistroFragment;

public class RegistroController {

    // acceso a la bd
    private AppDatabase bd;

    // navigation
    private final NavController navController;

    private RegistroFragment miVista;

    public RegistroController(RegistroFragment vista){
        miVista = vista;

        /* instanciar el acceso a la bd, le pasamos el contexto de la aplicacion,
       la clase q se encarga de crear el acceso y el nombre de la bd*/

        // allow es para permitir varias consultas simultaneas a la bd sqlite
        this.bd = Room.databaseBuilder(miVista.getContext(), AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries().build();

        // inicializo el navegador
        navController = Navigation.findNavController(miVista.getView());

    }

    // si lo registra bien muestra un toast y redirige a el fragment de menu principal , sino larga un toast y se queda

    public void registrarUsuario(){
    // creamos el usuario que luego guardaremos

        String nombreAux = miVista.getNombre();
        String contraseniaAux = miVista.getContraseña();

        if(camposValidos(nombreAux,contraseniaAux) & !existeUsuario(nombreAux)){

            Usuario obj = new Usuario();
            obj.setNombre(nombreAux);
            obj.setContrasenia(contraseniaAux);
            obj.setPuntajeMaximo(0);

            // recordar que si es 1 inserto bien si es menor es debido a un error
            long codigoRetorno = bd.usuarioDao().insert(obj);

            if(codigoRetorno > 0){
                miVista.mostrarText(" Se inserto correctamente: "+nombreAux);
                miVista.mostrarText(" Ingrese los datos del Registro");

                navController.navigate(R.id.loginFragment);
            }else{
                miVista.mostrarText(" no se a podido insertar: "+nombreAux);
            }
        }else{

            if(camposValidos(nombreAux,contraseniaAux)){
                miVista.mostrarText(""+nombreAux+", ya existe en la bd");
            }else{
                miVista.mostrarText(" Hay campos ingresados invalidos, corroborelos");
            }
         }

        miVista.vaciarCampos();
    }
/// mandar un objeto usuario entre el login , el menuprinccipal , el juego al iniciar , al finalizar y al volver al mismo menu principal
    private boolean existeUsuario(String usuario){

        boolean retorno =true;

        // si no existe el usuario retorna false
        if(bd.usuarioDao().existe(usuario)==0){
            retorno = false;
        }
        return retorno;
    }

    private boolean camposValidos (String nom, String con){

        if(nom.isEmpty() || con.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}
