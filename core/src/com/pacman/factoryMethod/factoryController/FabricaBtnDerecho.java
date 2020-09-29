package com.pacman.factoryMethod.factoryController;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.Controller.ControllerButton;
import com.pacman.Entities.Pacman;
import com.pacman.factoryMethod.FabricaActores;

public class FabricaBtnDerecho extends FabricaActores {

    public FabricaBtnDerecho(AssetManager manager) {
        super(manager);
    }

    public Actor crearActor(World myWorld, Vector2 posicion, Object actorAsociado) {

        Pacman pacman= (Pacman) actorAsociado;

        // texturas de los botones
        Texture textBtn= this.getManager().get("datos/flechaDerechaBlanca.png");

        return new ControllerButton(myWorld,pacman,textBtn,2,new Vector2(posicion.x+0.6f,posicion.y));
    }
}
