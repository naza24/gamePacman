package com.pacman.factoryMethod.factoryEntities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.Entities.Ghost;
import com.pacman.factoryMethod.FabricaActores;

public class FabricaGhostPink extends FabricaActores {

    private Texture[] ghostPink;
    private Texture [] ghostMiedoMuerte;
    private Ghost rosa;


    public FabricaGhostPink(AssetManager manager) {
        super(manager);
    }

    public Actor crearActor(World myWorld, Vector2 posicion, Object map) {

        TiledMap mapAux= (TiledMap) map;

        Texture [] ghostPink = new Texture[8];
        ghostPink[0] = this.getManager().get("datos/pink ghost/pink_ghost_derecha_0.png");
        ghostPink[1] = this.getManager().get("datos/pink ghost/pink_ghost_derecha_1.png");
        ghostPink[2] = this.getManager().get("datos/pink ghost/pink_ghost_abajo_0.png");
        ghostPink[3] = this.getManager().get("datos/pink ghost/pink_ghost_abajo_1.png");
        ghostPink[4] = this.getManager().get("datos/pink ghost/pink_ghost_izquierda_0.png");
        ghostPink[5] = this.getManager().get("datos/pink ghost/pink_ghost_izquierda_1.png");
        ghostPink[6] = this.getManager().get("datos/pink ghost/pink_ghost_arriba_0.png");
        ghostPink[7] = this.getManager().get("datos/pink ghost/pink_ghost_arriba_1.png");

        // texturas varias sobre el moo fear y cuando un fantasma es comido
        Texture [] ghostMiedoMuerte = new Texture[8];
        ghostMiedoMuerte[0] = this.getManager().get("datos/afraid ghost/afraid_ghost_0.png");
        ghostMiedoMuerte[1] = this.getManager().get("datos/afraid ghost/afraid_ghost_1.png");
        ghostMiedoMuerte[2] = this.getManager().get("datos/afraid ghost/afraid_ghost_2.png");
        ghostMiedoMuerte[3] = this.getManager().get("datos/afraid ghost/afraid_ghost_3.png");
        ghostMiedoMuerte[4] = this.getManager().get("datos/dead ghost/dead_ghost_derecha.png");
        ghostMiedoMuerte[5] = this.getManager().get("datos/dead ghost/dead_ghost_abajo.png");
        ghostMiedoMuerte[6] = this.getManager().get("datos/dead ghost/dead_ghost_izquierda.png");
        ghostMiedoMuerte[7] = this.getManager().get("datos/dead ghost/dead_ghost_arriba.png");

        TiledMapTileLayer capaTerreno = (TiledMapTileLayer)mapAux.getLayers().get(1);

        rosa = new Ghost(myWorld, ghostPink, ghostMiedoMuerte, posicion,
                capaTerreno,3, "rosa");

        return rosa;

    }
    // se le asigna un recorrido
    public void setRecorrido(int numRecorrido){
        this.rosa.setRecorrido(numRecorrido);
    }
}
