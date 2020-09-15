package com.pacman.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// Esta es una tabla de la bd
@Entity(tableName = Usuario.TABLE_NAME)
public class Usuario {

    // defino el nombre de la tabla
    public static final String TABLE_NAME = "usuario";

    //Atributos de la columna para asignarles nombre
    public  static final String COLUMN_NAME ="name";

    // declaro el nombre , como clave primaria
    // defino la columna nombre
    @PrimaryKey
    @NonNull
    private String nombre;

    // defino la columna contraseña
    @ColumnInfo(name="contrasenia")
    private String contrasenia;

    // defino la columna puntaje maximo
    @ColumnInfo(name="puntajeMaximo")
    private int puntajeMaximo;



    // se agrega el atributo id y sus seters y geters por que room los necesita
    public Usuario( String nombre, String contrasenia, int puntajeMaximo) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }

    // Se agrega esto para que room no tnga en cuenta el constructor vacio
    @Ignore
    public Usuario(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public void setPuntajeMaximo(int puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }
}

