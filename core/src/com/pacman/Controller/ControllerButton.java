package com.pacman.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.pacman.Entities.Pacman;
import com.pacman.MainGame;

import java.security.Policy;

import static com.pacman.Constants.PIXELS_IN_METER;

public class ControllerButton extends Actor {

    private Texture textureButton;
    private World world;
    private Pacman pacman;
    private Fixture fixtureButton;
    private Body bodyButton;

    private int direccion;

    // direction 0->izquierda, 1->arriba 2->derecha 3->abajo

    public ControllerButton(final World myWorld, final Pacman pacman, Texture textureButton, final int direccion, Vector2 posicion ){
    //Genera el boton y desde la clase externa creara un rectangulo q contendra los botones
        this.world=myWorld;
        this.textureButton=textureButton;

        this.direccion = direccion;
        // creo el bodydef que contiene el body
        BodyDef bodyDefButton = new BodyDef();
        bodyDefButton.type = BodyDef.BodyType.StaticBody; // lo defino como un cuerpo estatico
        bodyDefButton.position.set(posicion); // defino su posicion inicial

        // creo el body en el mundo con el bodyDef
        this.bodyButton = myWorld.createBody(bodyDefButton);

        // le doy forma a la figura (como la posicion que se le da al box2d se centra en el 0,0),
        // le indicamos que tiene 0,5 de la longitud tanto de alto como de ancho
        PolygonShape poliButton = new PolygonShape();
        poliButton.setAsBox(0.25f,0.25f);

        // instancio la figura y le paso el poligono
        this.fixtureButton= bodyButton.createFixture(poliButton,3);

        // le asigno un identificador para las colisiones
        String id= "boton"+direccion;
        this.fixtureButton.setUserData(id);

        // declaro el tamaño del actor
        setSize(PIXELS_IN_METER*0.5f ,PIXELS_IN_METER*0.5f);

        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Ejemplo", "se toco la pantalla en  (" + x + ", " + y + ")");


                pacman.avanzar(direccion);
                return true;
            }

        });

        // saco el poligono de la memoria
        poliButton.dispose();
    }

        // dibujamos el actor
        @Override
        public void draw(Batch batch, float parentAlpha) {

            // aqui agrego la altura y ancho de pixeles dnd quier colocar las cosas
            // seria 320 - 22,5 de ancho y 180 - 22.5 de alto,
            this.setPosition(this.bodyButton.getPosition().x * PIXELS_IN_METER, this.bodyButton.getPosition().y *PIXELS_IN_METER);

        /* Lo dibujo con la Textura, Posicion X e Y inicial que q le asignamos previamente
         (SetPosition)con el alto y el ancho que dimos en el metodo setSize*/

        batch.draw(textureButton,getX(),getY(),getWidth(),getHeight());
        }

        // para destruir los objetos de nuestro mundo
        public void detach(){

        /* se agregan en un metodo aparte por que se llama cuando la pantalla se esconde,
         y no se agrega en un dispose por que este se llama una unica vez,
         mientras si la pantalla es llamada 5 veces nuestra memoria de video quedara saturada*/
            bodyButton.destroyFixture(fixtureButton);
            world.destroyBody(bodyButton);

    }

}
