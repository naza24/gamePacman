package com.pacman.controllers;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.pacman.adapters.adapterUsuario;
import com.pacman.config.Constantes;
import com.pacman.database.AppDatabase;
import com.pacman.entidades.Usuario;


//import com.example.android.jugadorandroid.adapters.adapterUsuario;
//import com.example.android.jugadorandroid.config.Constantes;
//import com.example.android.jugadorandroid.database.AppDatabase;
//import com.example.android.jugadorandroid.entidades.Usuario;

import java.util.List;

public class RankingController {

    // acceso a la bd
    private AppDatabase bd;

    // contexto de la aplicacion
    private Context miContexto;

    private RecyclerView ranking;

    public RankingController(Context context, RecyclerView recycler){

        this.miContexto= context;
        /* instanciar el acceso a la bd, le pasamos el contexto de la aplicacion,
       la clase q se encarga de crear el acceso y el nombre de la bd*/

        // allow es para permitir varias consultas simultaneas a la bd sqlite
        this.bd = Room.databaseBuilder(miContexto, AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries().build();

        this.ranking = recycler;

        // hago la consulta y configuro la muestra del recicler
        rankingDeUsuarios();

    }

    public void rankingDeUsuarios (){

    // le digo como presentar los datos y le pongo que sea un linearLayoout ,
    // con el parametro this la dejo por defecto el cual seria vertical
     ranking.setLayoutManager(new LinearLayoutManager(miContexto));

    // recupero los Usuarios
     List<Usuario> usuarios = bd.usuarioDao().getAllUsuarios();

     ranking.setAdapter(new adapterUsuario(usuarios));
    }

}
