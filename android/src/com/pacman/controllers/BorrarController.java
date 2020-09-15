package com.pacman.controllers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class BorrarController {


    // acceso a la bd
    private AppDatabase bd;

    // contexto de la aplicacion
    private Context miContexto;

    // referencio al spinner
    private Spinner spinner;

    // referencia el usuario logueado
    private String idUsuarioLog;

    /*guarda termporalmente el usuario que se va a borrar,
    el cual se termina de borrar cuando presionan borrar*/
    private String usuarioBorrar;

    // navigation
    private final NavController navController;

    ////////////////////////////////////////////////////////////////////////////////////////////
    //  falta agregar la vista como parametro y sacar el spiner de ahi , luego navegar hacia el menu principal cuando no queden usuarios q borrar

    public BorrarController(Context context, View vista, String idUser){
        this.miContexto = context;

        /* instanciar el acceso a la bd, le pasamos el contexto de la aplicacion,
       la clase q se encarga de crear el acceso y el nombre de la bd*/

        // allow es para permitir varias consultas simultaneas a la bd sqlite
        bd = Room.databaseBuilder(miContexto, AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries().build();

        // inicializo la vista para poder tomar los elem en los otros metodos
        spinner = (Spinner)vista.findViewById(R.id.spinnerPlayer);

        // inicializo el usuario proximo a borrar(el q fue seleccionado en el spinner)
        usuarioBorrar="";

        // inicializo el usuario logueado
        idUsuarioLog=idUser;

        // inicializo el navegador
        navController = Navigation.findNavController(vista);

    }


    public void logicaBorrado(){
        // si hay mas usuarios realizo la logica para eliminar, en caso contrario retorno al menu principal
        if(cargarSpinner()>0){
            //  realizo la accion cuando se presiona el spinner
           spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /*Este metodo reconoce cuando un item es presionado AdapteView representa
             la aplicacion en general */
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position!=0){ // para que no sea el string de interfaz "seleccione..."
                        // el proximo usuario a borrar sera el seleccionado, distinto al cartel de el spinner
                        String elem = parent.getItemAtPosition(position).toString();
                        usuarioBorrar=elem;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else{
            Bundle arg = new Bundle();
            arg.putString("usuario",idUsuarioLog);
            navController.navigate(R.id.menuPrincipalFragment, arg);
            Toast.makeText(miContexto, "No hay mas usuarios para eliminar", Toast.LENGTH_LONG).show();
        }
    }
    /* carga el spinner y retorna la cantidad de elementos que contiene el spinner
     en caso de ser 0 retorna a el menu principal*/
    public int cargarSpinner(){

        int cantidadElem = 0;

        // me genera un arreglo de string para mostrar
        ArrayList<String> arrayString = generarArrayString(bd.usuarioDao().getAllUsuarios());

        //calculo el tamaño del arreglo antes de agregar el cartel de la interfaz
        cantidadElem = arrayString.size();

       /* agrego dos items al arreglo el primero va a ser un cartel de interfaz */
        arrayString.add(0, "Seleccione el usuario");

        // creo el adaptador
        ArrayAdapter<String> adaptador = new ArrayAdapter(miContexto, android.R.layout.simple_spinner_dropdown_item, arrayString);

        // se le asigna el adaptador al spinner
        this.spinner.setAdapter(adaptador);

        return cantidadElem;

    }

    // retorno un arreglo de string
    private ArrayList<String> generarArrayString (List<Usuario> lista){

        ArrayList<String> result = new ArrayList<String>();
        String elem="";

        for(int i=0; i<lista.size(); i++){
            // no agrego a la lista la opcion de eliminar el usuario logueado
            elem = lista.get(i).getNombre();
            if(!elem.equalsIgnoreCase(idUsuarioLog)){
                result.add(elem);
            }
        }
        return result;
    }

    // borro el usuario usando la interfaz de bd y actualizo el spinner luego del borrado
        public void borrarUsuario(){
        int result = bd.usuarioDao().deleteByKey(this.usuarioBorrar);

        if(result>0){
            Toast.makeText(miContexto, " se a eliminado " + usuarioBorrar, Toast.LENGTH_LONG).show();
            // si no quedan mas usuarios que borrar retorno al menu principal
            if(cargarSpinner()==0){
                Bundle arg = new Bundle();
                arg.putString("usuario",idUsuarioLog);
                navController.navigate(R.id.menuPrincipalFragment, arg);
            }
        }else{
            Toast.makeText(miContexto, usuarioBorrar+" ya fue eliminado", Toast.LENGTH_LONG).show();
            }


    }
}
