package com.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pacman.Controller.ControllerButton;
import com.pacman.Entities.Ghost;
import com.pacman.Entities.Pacman;

public class GameScreen extends BasicScreen {

    // Escenario para el Scene2d, (Es una libreria para dibujar actores en la pantalla)
    //https://github.com/libgdx/libgdx/wiki/Scene2d
    private Stage stage;

    // Para el box2d (Es una libreria que se encarga de los actores del Stage)
    //https://github.com/libgdx/libgdx/wiki/Box2d
    private World world;

    // ayuda a agregar el mapa
    private TiledMap map;

    // la camara ortogonal ayudara a dibujar el mapa
    private OrthogonalTiledMapRenderer tmr;
    private OrthographicCamera cam;

    // actores
    private Pacman pacman;
    private Ghost fantasmaRojo;

    // botones
    private ControllerButton botonIzquierdo;
    private ControllerButton botonArriba;
    private ControllerButton botonDerecha;
    private ControllerButton botonAbajo;

    // variable que referencia la instancia de game
    //private MainGame game;

    public GameScreen(MainGame game) {
        super(game);
        map = new TmxMapLoader().load("maps/nivel1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);

        // Defino las dimensiones del escenario como para mobile
        stage = new Stage(new FitViewport(640,360));

        Gdx.input.setInputProcessor(stage);

        // Defino el mundo y le paso los paramtros para la gravedad
        world = new World(new Vector2(0,0),true);


      /*  this.stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Ejemplo", "se toco la pantalla en  (" + x + ", " + y + ")");

                return true;
            }

        });*/
    }

    // Cuando se muestra la pantalla se carga lo que aparece dentro de este metodo
    @Override
    public void show() {

        // para entrar en modo debug
         stage.setDebugAll(true);

        // las coordenadas centrales de dond quiero el pad
        Vector2 controls = new Vector2(12,0.9f);

        world.setContactListener(new ContactListener() {

            private boolean colisionaron (Contact contact, Object a, Object b){
                return  (contact.getFixtureA().getUserData().equals(a) && contact.getFixtureB().getUserData().equals(b)) ||
                        (contact.getFixtureB().getUserData().equals(a)  && contact.getFixtureA().getUserData().equals(b));
            }
            @Override
            public void beginContact(Contact contact) {
                if(colisionaron(contact, "pacman", "ghost")){
                  contact.getFixtureA().setSensor(true);
                }
            }

            @Override
            public void endContact(Contact contact) {
                contact.getFixtureA().setSensor(false);
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        // recupero las texturas
        //Texture texturePacman = game.getAssetManager().get("datos/pacman32x32.png");
        Texture [] texturePacman = new Texture[5];
        texturePacman[0] = game.getAssetManager().get("datos/pac_man_0.png");
        texturePacman[1] = game.getAssetManager().get("datos/pac_man_1.png");
        texturePacman[2] = game.getAssetManager().get("datos/pac_man_2.png");
        texturePacman[3] = game.getAssetManager().get("datos/pac_man_muerte0.png");
        texturePacman[4] = game.getAssetManager().get("datos/pac_man_muerte1.png");

        Texture [] textureGhost = new Texture[2];
        textureGhost[0] = game.getAssetManager().get("datos/red ghost/red_ghost_derecha_0.png");
        textureGhost[1] = game.getAssetManager().get("datos/red ghost/red_ghost_derecha_1.png");


        Texture textBtnIzquierda = game.getAssetManager().get("datos/flechaIzquierdaBlanca.png");
        Texture textBtnArriba = game.getAssetManager().get("datos/flechaArribaBlanca.png");
        Texture textBtnDerecha = game.getAssetManager().get("datos/flechaDerechaBlanca.png");
        Texture textBtnAbajo = game.getAssetManager().get("datos/flechaAbajoBlanca.png");

        // creo el actor en la pantalla principal


        fantasmaRojo = new Ghost(this.world, textureGhost, new Vector2(7.3f,3.2f),
                                (TiledMapTileLayer)map.getLayers().get("Terreno"));

        Pacman pacman = new Pacman(this.world,texturePacman,new Vector2(7.3f,2.1f),
                                  (TiledMapTileLayer)map.getLayers().get(1),// cargo terreno primero
                                  (TiledMapTileLayer) map.getLayers().get(0));// puntos despues

        ControllerButton botonIzquierda= new ControllerButton(this.world,pacman,textBtnIzquierda,0,new Vector2(controls.x-0.6f,controls.y));
        ControllerButton botonArriba= new ControllerButton(this.world,pacman,textBtnArriba,1,new Vector2(controls.x,controls.y+0.6f));
        ControllerButton botonDerecha= new ControllerButton(this.world,pacman,textBtnDerecha,2,new Vector2(controls.x+0.6f,controls.y));
        ControllerButton botonAbajo= new ControllerButton(this.world,pacman,textBtnAbajo,3,new Vector2(controls.x,controls.y-0.6f));

        //Agrego los actores al escenario
        stage.addActor(pacman);
        stage.addActor(fantasmaRojo);

        stage.addActor(botonIzquierda);
        stage.addActor(botonArriba);
        stage.addActor(botonDerecha);
        stage.addActor(botonAbajo);
    }

    // cada vez q se cierra la pantalla o se va a otra
    @Override
    public void hide() {
        // destruye el body de pacman
        pacman.detach();
        botonIzquierdo.detach();
        botonArriba.detach();
        botonDerecha.detach();
        botonAbajo.detach();
        fantasmaRojo.detach();

        //remueve el actor del stage;
        pacman.remove();
        fantasmaRojo.remove();
        botonIzquierdo.remove();
        botonArriba.remove();
        botonDerecha.remove();
        botonAbajo.remove();

        map.dispose();
        tmr.dispose();
    }

    /* este metodo es el que se usa para representar la aplicacion o para actualizar el juego ,
        se ejecuta de 30 a 60 veces por segundo muestra imagenes, movimiento de los personajes etc*/
    @Override
    public void render(float delta) {
        // Le aplico color al fondo
        Gdx.gl.glClearColor(0,0,0,1);

        // limpio el buffer de video
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.setView((OrthographicCamera) stage.getCamera());

        tmr.render();

        // Actualizo los actores
        stage.act();

        //Actualizo el mundo, delta indica cuando fue la ultima vez que se ejecuto render
        world.step(delta,6,2);


        /*dibujar todos los actores, Siempre dibujar despues de hacer
        las actualizaciones y cualquier comprobacion que se requiera*/
        stage.draw();
    }


    /*Dispose hace que se vacie la memoria de la tarjeta grafica,
    sin esto cada vez q se inicia el juego se iria sobrecargando la memoria*/
    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
