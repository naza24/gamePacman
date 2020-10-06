package com.pacman.factoryMethod.factoryController;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.Controller.ControllerButton;
import com.pacman.Entities.Pacman;
import com.pacman.factoryMethod.FabricaActores;

public class FabricaBotones extends FabricaActores {

    public FabricaBotones(AssetManager manager) {
        super(manager);
    }

    public Actor crearBotonAbajo(World myWorld, Vector2 posicion, Object actorAsociado) {

        Pacman pacman= (Pacman) actorAsociado;

        // texturas de los botones
        Texture textBtn = this.getManager().get("datos/pad/flechaAbajoBlanca.png");
        return new ControllerButton(myWorld,pacman,textBtn,-1,new Vector2(posicion.x,posicion.y-0.6f));
    }

    public Actor crearBotonArriba(World myWorld, Vector2 posicion, Object actorAsociado) {

        Pacman pacman= (Pacman) actorAsociado;

        // texturas de los botones
        Texture textBtn = this.getManager().get("datos/pad/flechaArribaBlanca.png");

        return new ControllerButton(myWorld,pacman,textBtn,1,new Vector2(posicion.x,posicion.y+0.6f));
    }

    public Actor crearBotonDerecho(World myWorld, Vector2 posicion, Object actorAsociado) {

        Pacman pacman= (Pacman) actorAsociado;

        // texturas de los botones
        Texture textBtn= this.getManager().get("datos/pad/flechaDerechaBlanca.png");

        return new ControllerButton(myWorld,pacman,textBtn,2,new Vector2(posicion.x+0.6f,posicion.y));
    }

    public Actor crearBotonIzquierdo(World myWorld, Vector2 posicion, Object actorAsociado) {

        Pacman pacman= (Pacman) actorAsociado;

        // texturas de los botones
        Texture textBtn = this.getManager().get("datos/pad/flechaIzquierdaBlanca.png");

        return new ControllerButton(myWorld,pacman,textBtn,-2,new Vector2(posicion.x-0.6f,posicion.y));
    }
}
