package com.pacman.factoryMethod.factoryEntities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.Entities.Fruta;
import com.pacman.Entities.Ghost;
import com.pacman.factoryMethod.FabricaActores;

import java.util.ArrayList;
import java.util.Random;

public class FabricaFrutas extends FabricaActores {

    private ArrayList<Vector2> posiciones;

    public FabricaFrutas(AssetManager manager) {
        super(manager);
        // creo un arreglo y cargo todas las posiciones aleatorias que tendra la fruta
        posiciones = new ArrayList<Vector2>();
        posiciones.add(new Vector2(0.4f,6.7f));
        posiciones.add(new Vector2(3.5f,1f));
        posiciones.add(new Vector2(4.6f,4.6f));
        posiciones.add(new Vector2(5.6f,6.55f));
        posiciones.add(new Vector2(7.3f,3f));
        posiciones.add(new Vector2(7.80f,5.95f));
        posiciones.add(new Vector2(8.93f,0.8f));
        posiciones.add(new Vector2(11.35f,3.9f));
        posiciones.add(new Vector2(12.42f,6.33f));
        posiciones.add(new Vector2(12.1f,2.49f));

    }


    public Actor crearFrutaFrutilla(World myWorld) {

        Texture tex = this.getManager().get("datos/fruits/frutilla.png");

        return  new Fruta(myWorld, tex , getPosicionAleatoria(), 50, "frutilla");

    }
    public Actor crearFrutaCereza(World myWorld) {

        Texture tex = this.getManager().get("datos/fruits/cereza.png");

        return  new Fruta(myWorld, tex , getPosicionAleatoria(), 70, "cereza");

    }
    public Actor crearFrutaManzana(World myWorld) {

        Texture tex = this.getManager().get("datos/fruits/manzana.png");

        return  new Fruta(myWorld, tex , getPosicionAleatoria(), 80, "manzana");

    }
    public Actor crearFrutaPera(World myWorld) {

        Texture tex = this.getManager().get("datos/fruits/pera.png");

        return  new Fruta(myWorld, tex , getPosicionAleatoria(), 30, "pera");

    }

    private   Vector2 getPosicionAleatoria(){
        Random r= new Random();
        // retorna un numero aleatorio entre 0 y 9 la primera vez.. ya que son 10 elementos en la lista
        int numAleatorio = r.nextInt(posiciones.size()-1);
        Vector2 retorno = posiciones.get(numAleatorio);
        posiciones.remove(numAleatorio);
        return retorno;

    }
}
