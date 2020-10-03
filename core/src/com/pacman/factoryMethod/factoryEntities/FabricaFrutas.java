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
        ArrayList<Vector2>pos = new ArrayList<Vector2>();
        pos.add(new Vector2(0.4f,6.7f));
        pos.add(new Vector2(3.5f,1f));
        pos.add(new Vector2(4.6f,4.6f));
        pos.add(new Vector2(5.6f,6.55f));
        pos.add(new Vector2(7.3f,3f));
        pos.add(new Vector2(7.80f,5.95f));
        pos.add(new Vector2(8.93f,0.8f));
        pos.add(new Vector2(11.35f,3.9f));
        pos.add(new Vector2(12.42f,6.33f));
        pos.add(new Vector2(12.1f,2.49f));

        posiciones= pos;
        //ArrayList<Vector2>posicionesUsadas = new ArrayList<Vector2>();

    }


    public Actor crearFruta(World myWorld, Vector2 posicion, Object map) {

        TiledMap mapAux= (TiledMap) map;

        Texture tex = this.getManager().get("datos/afraid ghost/afraid_ghost_2.png");

        // recupero la capa superior dnd estan los puntos del mapa
        TiledMapTileLayer capaTerreno = (TiledMapTileLayer)mapAux.getLayers().get(1);

        return  new Fruta(myWorld, tex , getPosicionAleatoria(), capaTerreno, 50);

    }
    private Vector2 getPosicionAleatoria(){
        Random r= new Random();
        // retorna un numero aleatorio entre 0 y 9 la primera vez.. ya que son 10 elementos en la lista
        int numAleatorio = r.nextInt(posiciones.size());

        return posiciones.get(numAleatorio);

        // ver despues el tema del respawn aleatorio y el tema de ir cambiando dnd aparecen las frutas.
        // llevar unna lista de las posiciones usadas y en base a eso calcular los posibles nuevos resawnd para la fruta
        ///
        // ademas sacar la posicion de los lguares dnd no corresponde y en los fantasmas dejar tmb un
        // arreglo fijo en la fabrica que guarde la posicion de los fantasmas para que no sea toacado
        // desde el mainscram al igual que pacman
    }
}
