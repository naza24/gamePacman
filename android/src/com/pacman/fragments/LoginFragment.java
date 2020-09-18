package com.pacman.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pacman.R;
import com.pacman.controllers.LoginController;

//import com.example.android.jugadorandroid.R;
//import com.example.android.jugadorandroid.controllers.LoginController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private LoginController loginController;
    EditText nombre, contrasenia;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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


    // SE SEPARA LO QUE ES LA CREACION DE LOS COMPONENTES
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    // SE SEPARA LO QUE ES LA INICIALIZACION DE LOS COMPONENTES
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView registro = (TextView)view.findViewById(R.id.btnRegistrarLogin);
        Button ingresar = (Button)view.findViewById(R.id.idBtnRegis);
        nombre = (EditText) view.findViewById(R.id.etUsuario);
        contrasenia = (EditText) view.findViewById(R.id.etContrasenia);

        loginController = new LoginController(getContext(),view);

        /*llamamos a este metodo que se encarga de cargar los campos que se
         recuperaron luego de la nueva creacion del fragment */
        restaurarCampos(savedInstanceState);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // redirecciona a la pantalla de menu principal
             loginController.irPantallaRegistro();
            }
        });

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginController.loguearUsuario();
            }
        });
    }

    /* este metodo es el que se sobreescribe para poder guardar los campos antes de rotar el dispositivo,
     cuando se rota el dispositivo se destruye la activity o fragment y se crea uno nuevo */

    // recordar que para que un textview se mantenga hay q agregar en el xml android:freezesText="true"
    //http://codictados.com/rotacion-pantalla-android/
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // el controlador guarda los campos que quiere en outState
        String nomb="";
        String contr="";

        if(nombre!=null){
            nomb= nombre.getText().toString();
        }
        if(contrasenia!=null){
            contr= contrasenia.getText().toString();
        }

        outState.putString("etUsuario", nomb);
        outState.putString("etContrasenia", contr);


    }
    // recupera los campos y los asigna a sus lugares
    private void restaurarCampos(Bundle savedInstanceState) {
        // si el bundle no esta vacio es porq se guardo algo.
        if (savedInstanceState!=null){
          nombre.setText( savedInstanceState.getString("etUsuario",""));
          contrasenia.setText(savedInstanceState.getString("etContrasenia",""));

        }
    }

}