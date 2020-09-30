package com.pacman.factoryMethod.factoryEntities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.Entities.Pacman;
import com.pacman.factoryMethod.FabricaActores;

public class FabricaPacman extends FabricaActores {


    public FabricaPacman(AssetManager manager) {
        super(manager);
    }


    public Actor crearActor(World myWorld, Vector2 posicion, Object map) {

        TiledMap mapAux= (TiledMap) map;

        // recupero las texturas
        Texture[] texturePacman = new Texture[14];
        texturePacman[0] = this.getManager().get("datos/pac_man_0.png");
        texturePacman[1] = this.getManager().get("datos/pac_man_1.png");
        texturePacman[2] = this.getManager().get("datos/pac_man_2.png");

        // texturas de la muerte de pacman
        texturePacman[3] = this.getManager().get("datos/dead pacman/dead_pacman_0.png");
        texturePacman[4] = this.getManager().get("datos/dead pacman/dead_pacman_1.png");
        texturePacman[5] = this.getManager().get("datos/dead pacman/dead_pacman_2.png");
        texturePacman[6] = this.getManager().get("datos/dead pacman/dead_pacman_3.png");
        texturePacman[7] = this.getManager().get("datos/dead pacman/dead_pacman_4.png");
        texturePacman[8] = this.getManager().get("datos/dead pacman/dead_pacman_5.png");
        texturePacman[9] = this.getManager().get("datos/dead pacman/dead_pacman_6.png");
        texturePacman[10] = this.getManager().get("datos/dead pacman/dead_pacman_7.png");
        texturePacman[11] = this.getManager().get("datos/dead pacman/dead_pacman_8.png");
        texturePacman[12] = this.getManager().get("datos/dead pacman/dead_pacman_9.png");
        texturePacman[13] = this.getManager().get("datos/dead pacman/dead_pacman_10.png");

        TiledMapTileLayer capaTerreno = (TiledMapTileLayer)mapAux.getLayers().get(1);
        TiledMapTileLayer capaPuntos= (TiledMapTileLayer) mapAux.getLayers().get(0);

        return new Pacman(myWorld,texturePacman,posicion, capaTerreno,capaPuntos);

    }
}
