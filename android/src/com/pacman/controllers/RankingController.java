package com.pacman.controllers;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.pacman.adapters.adapterUsuario;
import com.pacman.config.Constantes;
import com.pacman.database.AppDatabase;
import com.pacman.entidades.Usuario;
import com.pacman.fragments.RankingFragment;

import java.util.List;

public class RankingController {

    // acceso a la bd
    private AppDatabase bd;

    private RecyclerView ranking;

    private RankingFragment miVista;

    public RankingController(RankingFragment vista){

        miVista=vista;

        /* instanciar el acceso a la bd, le pasamos el contexto de la aplicacion,
       la clase q se encarga de crear el acceso y el nombre de la bd*/

        // allow es para permitir varias consultas simultaneas a la bd sqlite
        this.bd = Room.databaseBuilder(miVista.getContext(), AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries().build();

        this.ranking = miVista.getRecycler();

        // hago la consulta y configuro la muestra del recicler
        rankingDeUsuarios();

    }

    public void rankingDeUsuarios (){

    // le digo como presentar los datos y le pongo que sea un linearLayoout ,
    // con el parametro this la dejo por defecto el cual seria vertical
     ranking.setLayoutManager(new LinearLayoutManager(miVista.getContext()));

    // recupero los Usuarios
     List<Usuario> usuarios = bd.usuarioDao().getAllUsuarios();

     ranking.setAdapter(new adapterUsuario(usuarios));
    }

}
