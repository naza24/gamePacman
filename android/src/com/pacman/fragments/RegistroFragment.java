package com.pacman.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pacman.R;
import com.pacman.controllers.RegistroController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment {

    // controller
    RegistroController controller;
    EditText nombre, contrasenia;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // seccion de la creacion de los componenetes
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_registro, container, false);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        nombre = (EditText) view.findViewById(R.id.etUsuario);
        contrasenia = (EditText) view.findViewById(R.id.etContrasenia);
        Button btnRegistrar = (Button) view.findViewById(R.id.idBtnRegis);

        // creo el controlador y le mando la vista
        this.controller = new RegistroController(this);

        /*llamamos a este metodo que se encarga de cargar los campos que se
         recuperaron luego de la nueva creacion del fragment */
        restaurarCampos(savedInstanceState);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               controller.registrarUsuario();
            }
        });

    }
    /* este metodo es el que se sobreescribe para poder guardar los campos antes de rotar el dispositivo,
     cuando se rota el dispositivo se destruye la activity o fragment y se crea uno nuevo */

    // recordar que para que un textview se mantenga hay q agregar en el xml android:freezesText="true"
    //http://codictados.com/rotacion-pantalla-android/

    /* al parecer las fragments se acumulan y cuando el fragment en curso gira, lo hacen
     las que estan por detras, al querer recuperar sus campos salta error ya que el outstate esta vacio
     por lo que hay q controlar si estan vacios y en cuyo caso se le setea el valor cadena nula */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // el controlador guarda los campos que quiere en outState
        String nom="";
        String cont="";

        if(nombre!=null){
            nom= nombre.getText().toString();
        }
        if(contrasenia!=null){
            cont= contrasenia.getText().toString();
        }
        outState.putString("etUsuario", nom);
        outState.putString("etContrasenia", cont);
    }

    // recupera los campos y los asigna a sus lugares
    private void restaurarCampos(Bundle savedInstanceState) {
        // si el bundle no esta vacio es porq se guardo algo.
        if (savedInstanceState!=null){
            this.nombre.setText( savedInstanceState.getString("etUsuario",""));
            this.contrasenia.setText(savedInstanceState.getString("etContrasenia",""));

        }
    }

    // seteo los valores en vacio
    public void vaciarCampos(){
        this.nombre.setText("");
        this.contrasenia.setText("");
    }

    // retorno el nombre en un string para analizarlo desde el controlador
    public String getNombre() {
        return nombre.getText().toString();
    }

    // retorno la contraseña en un string para analizarlo desde el controlador
    public String getContraseña() {
        return contrasenia.getText().toString();
    }

    //Muestra un texto por pantalla segun lo que el controlador le pase de text
    public void mostrarText(String text){
        Toast.makeText(getContext(),text, Toast.LENGTH_SHORT).show();
    }

}