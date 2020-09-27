package com.pacman.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pacman.AndroidLauncher;
import com.pacman.MainActivity;
import com.pacman.MainGame;
import com.pacman.R;
import com.pacman.controllers.MenuPrincipalController;
//import com.example.android.jugadorandroid.R;
//import com.example.android.jugadorandroid.controllers.MenuPrincipalController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuPrincipalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuPrincipalFragment extends Fragment {

    private MenuPrincipalController menuPrincipalController;
    TextView usuario;
    String idUsuario;

    int puntaje;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuPrincipalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuPrincipalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuPrincipalFragment newInstance(String param1, String param2) {
        MenuPrincipalFragment fragment = new MenuPrincipalFragment();
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

    /* separo la seccion de creacion de la de inicializacion en el metodo debajo , en caso de algun error
    en esta seccino del codigo no afecte la inicializacion*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_principal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnBorrar = (Button)view.findViewById(R.id.btnBorrar);
        Button btnRanking = (Button)view.findViewById(R.id.btnRanking);
        Button btnSalir = (Button)view.findViewById(R.id.btnSalir);
        Button btnJugar = (Button)view.findViewById(R.id.btnJugar);
        usuario = (TextView) view.findViewById(R.id.txtUser);

        /* recupero el usuario del bundle q se mando desde el login, si es nulo por
         que estoy en la pantalla de juego no lo analizo*/

        // despues acomodar esto ,, el controlador esta despues por lo que salta error si llama al controlador
        // creo el controller y le paso el idUsuario por que seguramente se necesite para en futuro actualizar los scores

        // si es distinto de null es por q esta logueado y estamos en esa pantalla
        if(usuario!=null) {
            idUsuario = getArguments().getString("usuario");
            puntaje = getArguments().getInt("puntaje");
/*
            if(puntaje!=0){
                menuPrincipalController.actualizarPuntaje(puntaje);
            }*/
            // cargo el txtView con el idUsuario
            usuario.setText("Usuario: "+idUsuario);

            Toast.makeText(getContext(),"  puntaje: "+puntaje+" usuario: "+idUsuario, Toast.LENGTH_LONG).show();
        }
        menuPrincipalController = new MenuPrincipalController(this);
        menuPrincipalController.actualizarPuntaje(puntaje);

         /*llamamos a este metodo que se encarga de cargar los campos que se
         recuperaron luego de la nueva creacion del fragment */
        restaurarCampos(savedInstanceState);

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuPrincipalController.irBorrado();
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuPrincipalController.irRanking();
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuPrincipalController.irLogin();
            }
        });
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuPrincipalController.lanzarJuego();
              /*  Intent lanzadorJuego = new Intent(getActivity(), AndroidLauncher.class);
                // Le mando el usuario al juego
                Bundle bun = new Bundle();
                bun.putString("usuario",idUsuario);
                Toast.makeText(getContext(), "idUsuario : "+ idUsuario , Toast.LENGTH_SHORT).show();
                lanzadorJuego.putExtras(bun);

                Toast.makeText(getContext(), "la concha de tu madre "+idUsuario, Toast.LENGTH_SHORT).show();
                startActivity(lanzadorJuego);

                // finalizo la mainActivity antes de lanzar el juego
               getActivity().finish();*/

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

        if(usuario!=null){
            nomb= usuario.getText().toString();
        }
        outState.putString("etUsuario", nomb);
    }

    // recupera los campos y los asigna a sus lugares
    private void restaurarCampos(Bundle savedInstanceState) {
        // si el bundle no esta vacio es porq se guardo algo.
        if (savedInstanceState!=null){
            usuario.setText( savedInstanceState.getString("etUsuario",""));
        }
    }

        // retorno el nombre en un string para analizarlo desde el controlador
        public String getUsuarioLogeado() {
            return idUsuario;
        }

        //Muestra un texto por pantalla segun lo que el controlador le pase de text
        public void mostrarText(String text){
            Toast.makeText(getContext(),text, Toast.LENGTH_SHORT).show();
        }

    }