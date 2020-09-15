package com.pacman.database;

// para crear la conexion que nos permita acceder a las tablas

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.pacman.entidades.Usuario;
import com.pacman.interfaces.UsuarioDao;

//import com.example.android.jugadorandroid.entidades.Usuario;
//import com.example.android.jugadorandroid.interfaces.UsuarioDao;

// primero declaramos todas las tablas a las cuales se tendra acceso, y la version de la bd
@Database(entities = {Usuario.class}, version =1)
public abstract class AppDatabase extends RoomDatabase {

    @SuppressWarnings("WeakersAccess")

    // establecemos que permisos van a tener para acceder , referenciando la iterfaz de dao
    public abstract UsuarioDao usuarioDao();

    // variable para poder llamar al manejador de la bd
    private static AppDatabase sInstance;
}
