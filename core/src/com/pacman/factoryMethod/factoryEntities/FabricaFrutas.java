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

public class FabricaFrutas extends FabricaActores {

    int [] pos;
    public FabricaFrutas(AssetManager manager) {
        super(manager);
        //pos[1]=;
    }


    public Actor crearFruta(World myWorld, Vector2 posicion, Object map) {

        TiledMap mapAux= (TiledMap) map;

        Texture tex = this.getManager().get("datos/orange ghost/orange_ghost_arriba_1.png");

        // recupero la capa superior dnd estan los puntos del mapa
        TiledMapTileLayer capaPuntos = (TiledMapTileLayer)mapAux.getLayers().get(0);

        return  new Fruta(myWorld, tex , posicion, capaPuntos);

    }
}
