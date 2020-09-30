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

public class FabricaGhostBlue extends FabricaActores {

        private Texture[] ghostBlue;
        private Texture [] ghostMiedoMuerte;
        private Ghost azul;

    public FabricaGhostBlue(AssetManager manager) {
        super(manager);
    }

        public Actor crearActor(World myWorld, Vector2 posicion, Object map) {

            TiledMap mapAux= (TiledMap) map;

            Texture [] ghostBlue = new Texture[8];
            ghostBlue[0] = this.getManager().get("datos/blue ghost/blue_ghost_derecha_0.png");
            ghostBlue[1] = this.getManager().get("datos/blue ghost/blue_ghost_derecha_1.png");
            ghostBlue[2] = this.getManager().get("datos/blue ghost/blue_ghost_abajo_0.png");
            ghostBlue[3] = this.getManager().get("datos/blue ghost/blue_ghost_abajo_1.png");
            ghostBlue[4] = this.getManager().get("datos/blue ghost/blue_ghost_izquierda_0.png");
            ghostBlue[5] = this.getManager().get("datos/blue ghost/blue_ghost_izquierda_1.png");
            ghostBlue[6] = this.getManager().get("datos/blue ghost/blue_ghost_arriba_0.png");
            ghostBlue[7] = this.getManager().get("datos/blue ghost/blue_ghost_arriba_1.png");

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

            azul = new Ghost(myWorld, ghostBlue, ghostMiedoMuerte, posicion,
                    capaTerreno,1, "azul");

            return azul;

        }
        // se le asigna un recorrido
        public void setRecorrido(int numRecorrido){
            this.azul.setRecorrido(numRecorrido);
        }
}
