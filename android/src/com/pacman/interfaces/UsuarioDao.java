package com.pacman.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pacman.entidades.Usuario;

//import com.example.android.jugadorandroid.entidades.Usuario;

import java.util.List;

// Aqui se desarrollan los metodos que se comunicaran con la bd , las querys
@Dao
public interface UsuarioDao {

    /*selecciono las cantidades (se escribe la consulta interna,
     y luego el metodo con el que se llamara en las clases en este caso count()*/
    @Query("SELECT COUNT(*) FROM "+ Usuario.TABLE_NAME)
    int count();

    // selecciono tod0
    @Query("SELECT * FROM "+Usuario.TABLE_NAME)
    List<Usuario>getAllUsuarios();

    // el unico long es en insertar los otros son int

    @Insert
    void insertAll(Usuario... usuarios);

    // eliminar , el id e la linea superior es el parametro que se pasa en el metodo de abajo
    @Query("DELETE FROM "+Usuario.TABLE_NAME+" WHERE nombre like :nom")
    int deleteByKey(String nom);

    @Update
    int updateEntities(Usuario obj);

    // insertar 2 , EN LOS INSERTS DEVUELVE 1 CUANDO SE INSERTO BIEN Y 0 CUANDO ES FALLIDO
    @Insert
    long insert(Usuario usuario);

    /// esta opcion retorna 1 si existe alguno sino retorna 0
    @Query("SELECT COUNT(*) FROM "+ Usuario.TABLE_NAME + " WHERE nombre like :nom")
    int existe(String nom);

    /// esta opcion retorna 1 si existe el usuario y coincide el nombre y la contrasenia sino retorna 0
    @Query("SELECT COUNT(*) FROM "+ Usuario.TABLE_NAME + " WHERE nombre like :nom and contrasenia like :contr")
    int existe2(String nom, String contr);

    /// esta consulta retorna el usuario con ese nombre que seria la key
    @Query("SELECT * FROM "+ Usuario.TABLE_NAME + " WHERE nombre like :nom ")
    Usuario getUsuario(String nom);


}
