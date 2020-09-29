package com.pacman.factoryMethod;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.Entities.Pacman;

public abstract class FabricaActores{

    private AssetManager assets;

    public FabricaActores(AssetManager manager){
        this.assets=manager;
    }

    // se le pasa un objeto recurso , en el caso del pacman y los fantasmas sera un mapa,
    // en el caso de el pad sera el pacman que se vea afectado por la interaccion sobre este
    public abstract Actor crearActor( World myWorld, Vector2 posicion, Object Recurso);

    // comportamiento comun a todos los actores,
    public void setManager(AssetManager manager){
        assets= manager;
    }
    public AssetManager getManager(){
        return assets;
    }
}
