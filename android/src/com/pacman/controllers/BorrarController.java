package com.pacman.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.pacman.config.Constantes;
import com.pacman.R;
import com.pacman.database.AppDatabase;
import com.pacman.entidades.Usuario;
import com.pacman.fragments.BorrarFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BorrarController {


    // acceso a la bd
    private AppDatabase bd;

    // referencio al spinner
    private Spinner spinner;

    /*guarda termporalmente el usuario que se va a borrar,
    el cual se termina de borrar cuando presionan borrar*/
    private String usuarioBorrar;

    private BorrarFragment miVista;

    // navigation
    private final NavController navController;

    public BorrarController(BorrarFragment vista){

        miVista= vista;

        /* instanciar el acceso a la bd, le pasamos el contexto de la aplicacion,
       la clase q se encarga de crear el acceso y el nombre de la bd*/

        // allow es para permitir varias consultas simultaneas a la bd sqlite
        bd = Room.databaseBuilder(miVista.getContext(), AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries().build();

        // inicializo la vista para poder tomar los elem en los otros metodos
        spinner = miVista.getSpinner();

        // inicializo el usuario proximo a borrar(el q fue seleccionado en el spinner)
        usuarioBorrar="";

        // inicializo el navegador
        navController = Navigation.findNavController(miVista.getView());
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
            arg.putString("usuario",miVista.getUsuarioLogueado());
            navController.navigate(R.id.menuPrincipalFragment, arg);

            if(idiomaIngles()){
                miVista.mostrarText("There aren´t more users for delete");
            }else{
                miVista.mostrarText("No hay mas usuarios para eliminar");
            }
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
        if(idiomaIngles()){
            arrayString.add(0, "Select user");
        }else{
            arrayString.add(0, "Seleccione el usuario");
        }

        // creo el adaptador
        ArrayAdapter<String> adaptador = new ArrayAdapter(miVista.getContext(), android.R.layout.simple_spinner_dropdown_item, arrayString);

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
            if(!elem.equalsIgnoreCase(miVista.getUsuarioLogueado())){
                result.add(elem);
            }
        }
        return result;
    }

    // borro el usuario usando la interfaz de bd y actualizo el spinner luego del borrado
        public void borrarUsuario(){
        int result = bd.usuarioDao().deleteByKey(this.usuarioBorrar);

        if(result>0){
            if(idiomaIngles()){
                miVista.mostrarText(" user "+ usuarioBorrar+ " was delete" );
            }else{
                miVista.mostrarText(" el usuario "+ usuarioBorrar+ " fue eliminado ");
            }
            // si no quedan mas usuarios que borrar retorno al menu principal
            if(cargarSpinner()==0){
                Bundle arg = new Bundle();
                arg.putString("usuario",miVista.getUsuarioLogueado());
                navController.navigate(R.id.menuPrincipalFragment, arg);
            }
        }else{
            if(idiomaIngles()){
                miVista.mostrarText(usuarioBorrar+" was previously delete");
            }else{
                miVista.mostrarText(usuarioBorrar+" fue eliminado previamente");
            }
        }
    }

    private boolean idiomaIngles(){
        if(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("english")){
            return true;
        }else{
            return false;
        }
    }
}
