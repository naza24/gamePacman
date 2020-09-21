package com.pacman.fragments;

import android.os.Bundle;
import android.sax.TextElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pacman.R;
import com.pacman.controllers.BorrarController;

//import com.example.android.jugadorandroid.R;
//import com.example.android.jugadorandroid.controllers.BorrarController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BorrarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BorrarFragment extends Fragment {

    private BorrarController borrarController;

    Spinner spinner;

    String idUsuario;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BorrarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BorrarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BorrarFragment newInstance(String param1, String param2) {
        BorrarFragment fragment = new BorrarFragment();
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
        return inflater.inflate(R.layout.fragment_borrar, container, false);
    }

    // SE SEPARA LO QUE ES LA INICIALIZACION DE LOS COMPONENTES
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = (Spinner)view.findViewById(R.id.spinnerPlayer);
        Button btn_borrar = (Button)view.findViewById(R.id.btnBorrar);

        // recupero el usuario del bundle q se mando desde el login
        idUsuario = getArguments().getString("usuario");

        this.borrarController = new BorrarController(this);

        // inicializo el spinner junto con su comportamiento
        borrarController.logicaBorrado();

       // restaurarCampos(savedInstanceState);

        btn_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarController.borrarUsuario();
            }
        });

    }
    public String getUsuarioLogueado(){
        return idUsuario;
    }

    public Spinner getSpinner(){
        return spinner;
    }

    public void mostrarText ( String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}