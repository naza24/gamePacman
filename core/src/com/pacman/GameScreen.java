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
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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

    // actores
    private Pacman pacman;
    private Ghost fantasmaRojo;
    private Ghost fantasmaRosa;
    private Ghost fantasmaAzul;
    private Ghost fantasmaNaranja;

    // tiempo en segundos que dura el modo fear
    private float tiempoFear;

    // botones
    private ControllerButton botonIzquierdo;
    private ControllerButton botonArriba;
    private ControllerButton botonDerecha;
    private ControllerButton botonAbajo;

    public GameScreen(MainGame game) {
        super(game);

        // recupero el tiempo
        tiempoFear = 10f;

        map = new TmxMapLoader().load("maps/nivel1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);

        // Defino las dimensiones del escenario como para mobile
        stage = new Stage(new FitViewport(640,360));

        Gdx.input.setInputProcessor(stage);

        // Defino el mundo y le paso los paramtros para la gravedad
        world = new World(new Vector2(0,0),true);

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
                /* si colisionan dos fantasmas, desactivo la colision en uno al iniciar el
                 contacto y lo vuelvo a activar al finalizar el choque */
                if( colisionaron(contact, "rojo", "azul")   || colisionaron(contact, "rojo", "rosa")   ||
                    colisionaron(contact, "rojo", "naranja")|| colisionaron(contact, "azul", "rosa")   ||
                    colisionaron(contact, "azul", "naranja")|| colisionaron(contact, "rosa", "naranja")){
                  contact.getFixtureA().setSensor(true);
                }

/////////////////////////////////////////////////////////////// mandar el color
// por parametro para el user id y segun corresponda matar al fantasma
                if(colisionaron(contact,"pacman","rojo")){
                        if(pacman.isBonificado()){
                            fantasmaRojo.setAlive(false);
                        }
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
        Texture [] texturePacman = new Texture[5];
        texturePacman[0] = game.getAssetManager().get("datos/pac_man_0.png");
        texturePacman[1] = game.getAssetManager().get("datos/pac_man_1.png");
        texturePacman[2] = game.getAssetManager().get("datos/pac_man_2.png");
        texturePacman[3] = game.getAssetManager().get("datos/pac_man_muerte0.png");
        texturePacman[4] = game.getAssetManager().get("datos/pac_man_muerte1.png");

        Texture [] textureRedGhost = new Texture[8];
        textureRedGhost[0] = game.getAssetManager().get("datos/red ghost/red_ghost_derecha_0.png");
        textureRedGhost[1] = game.getAssetManager().get("datos/red ghost/red_ghost_derecha_1.png");
        textureRedGhost[2] = game.getAssetManager().get("datos/red ghost/red_ghost_abajo_0.png");
        textureRedGhost[3] = game.getAssetManager().get("datos/red ghost/red_ghost_abajo_1.png");
        textureRedGhost[4] = game.getAssetManager().get("datos/red ghost/red_ghost_izquierda_0.png");
        textureRedGhost[5] = game.getAssetManager().get("datos/red ghost/red_ghost_izquierda_1.png");
        textureRedGhost[6] = game.getAssetManager().get("datos/red ghost/red_ghost_arriba_0.png");
        textureRedGhost[7] = game.getAssetManager().get("datos/red ghost/red_ghost_arriba_1.png");

        Texture [] textureBlueGhost = new Texture[8];
        textureBlueGhost[0] = game.getAssetManager().get("datos/blue ghost/blue_ghost_derecha_0.png");
        textureBlueGhost[1] = game.getAssetManager().get("datos/blue ghost/blue_ghost_derecha_1.png");
        textureBlueGhost[2] = game.getAssetManager().get("datos/blue ghost/blue_ghost_abajo_0.png");
        textureBlueGhost[3] = game.getAssetManager().get("datos/blue ghost/blue_ghost_abajo_1.png");
        textureBlueGhost[4] = game.getAssetManager().get("datos/blue ghost/blue_ghost_izquierda_0.png");
        textureBlueGhost[5] = game.getAssetManager().get("datos/blue ghost/blue_ghost_izquierda_1.png");
        textureBlueGhost[6] = game.getAssetManager().get("datos/blue ghost/blue_ghost_arriba_0.png");
        textureBlueGhost[7] = game.getAssetManager().get("datos/blue ghost/blue_ghost_arriba_1.png");

        Texture [] textureOrangeGhost = new Texture[8];
        textureOrangeGhost[0] = game.getAssetManager().get("datos/orange ghost/orange_ghost_derecha_0.png");
        textureOrangeGhost[1] = game.getAssetManager().get("datos/orange ghost/orange_ghost_derecha_1.png");
        textureOrangeGhost[2] = game.getAssetManager().get("datos/orange ghost/orange_ghost_abajo_0.png");
        textureOrangeGhost[3] = game.getAssetManager().get("datos/orange ghost/orange_ghost_abajo_1.png");
        textureOrangeGhost[4] = game.getAssetManager().get("datos/orange ghost/orange_ghost_izquierda_0.png");
        textureOrangeGhost[5] = game.getAssetManager().get("datos/orange ghost/orange_ghost_izquierda_1.png");
        textureOrangeGhost[6] = game.getAssetManager().get("datos/orange ghost/orange_ghost_arriba_0.png");
        textureOrangeGhost[7] = game.getAssetManager().get("datos/orange ghost/orange_ghost_arriba_1.png");

        Texture [] texturePinkGhost = new Texture[8];
        texturePinkGhost[0] = game.getAssetManager().get("datos/pink ghost/pink_ghost_derecha_0.png");
        texturePinkGhost[1] = game.getAssetManager().get("datos/pink ghost/pink_ghost_derecha_1.png");
        texturePinkGhost[2] = game.getAssetManager().get("datos/pink ghost/pink_ghost_abajo_0.png");
        texturePinkGhost[3] = game.getAssetManager().get("datos/pink ghost/pink_ghost_abajo_1.png");
        texturePinkGhost[4] = game.getAssetManager().get("datos/pink ghost/pink_ghost_izquierda_0.png");
        texturePinkGhost[5] = game.getAssetManager().get("datos/pink ghost/pink_ghost_izquierda_1.png");
        texturePinkGhost[6] = game.getAssetManager().get("datos/pink ghost/pink_ghost_arriba_0.png");
        texturePinkGhost[7] = game.getAssetManager().get("datos/pink ghost/pink_ghost_arriba_1.png");

        // texturas de los botones
        Texture textBtnIzquierda = game.getAssetManager().get("datos/flechaIzquierdaBlanca.png");
        Texture textBtnArriba = game.getAssetManager().get("datos/flechaArribaBlanca.png");
        Texture textBtnDerecha = game.getAssetManager().get("datos/flechaDerechaBlanca.png");
        Texture textBtnAbajo = game.getAssetManager().get("datos/flechaAbajoBlanca.png");

        // texturas varias sobre el moo fear y cuando un fantasma es comido
        Texture [] textureGralGhost = new Texture[8];
        textureGralGhost[0] = game.getAssetManager().get("datos/afraid ghost/afraid_ghost_0.png");
        textureGralGhost[1] = game.getAssetManager().get("datos/afraid ghost/afraid_ghost_1.png");
        textureGralGhost[2] = game.getAssetManager().get("datos/afraid ghost/afraid_ghost_2.png");
        textureGralGhost[3] = game.getAssetManager().get("datos/afraid ghost/afraid_ghost_3.png");
        textureGralGhost[4] = game.getAssetManager().get("datos/dead ghost/dead_ghost_derecha.png");
        textureGralGhost[5] = game.getAssetManager().get("datos/dead ghost/dead_ghost_abajo.png");
        textureGralGhost[6] = game.getAssetManager().get("datos/dead ghost/dead_ghost_izquierda.png");
        textureGralGhost[7] = game.getAssetManager().get("datos/dead ghost/dead_ghost_arriba.png");

        // creo el actor en la pantalla principal
        fantasmaAzul = new Ghost(this.world, textureBlueGhost, textureGralGhost, new Vector2(6.3f,3.2f),
                (TiledMapTileLayer)map.getLayers().get("Terreno"),1, "azul");

        fantasmaRojo = new Ghost(this.world, textureRedGhost, textureGralGhost, new Vector2(6.6f,3.2f),
                                (TiledMapTileLayer)map.getLayers().get("Terreno"), 2, "rojo");

        fantasmaRosa = new Ghost(this.world, texturePinkGhost, textureGralGhost, new Vector2(7.6f,3.2f),
                (TiledMapTileLayer)map.getLayers().get("Terreno"),3,"rosa");

        fantasmaNaranja = new Ghost(this.world, textureOrangeGhost, textureGralGhost ,new Vector2(8.2f,3.2f),
                (TiledMapTileLayer)map.getLayers().get("Terreno"),4, "naranja");



        this.pacman = new Pacman(this.world,texturePacman,new Vector2(7.3f,2.1f),
                                  (TiledMapTileLayer)map.getLayers().get(1),// cargo terreno primero
                                  (TiledMapTileLayer) map.getLayers().get(0));// puntos despues

        ControllerButton botonIzquierda= new ControllerButton(this.world,pacman,textBtnIzquierda,0,new Vector2(controls.x-0.6f,controls.y));
        ControllerButton botonArriba= new ControllerButton(this.world,pacman,textBtnArriba,1,new Vector2(controls.x,controls.y+0.6f));
        ControllerButton botonDerecha= new ControllerButton(this.world,pacman,textBtnDerecha,2,new Vector2(controls.x+0.6f,controls.y));
        ControllerButton botonAbajo= new ControllerButton(this.world,pacman,textBtnAbajo,3,new Vector2(controls.x,controls.y-0.6f));

        //Agrego los actores al escenario
        stage.addActor(pacman);
        stage.addActor(fantasmaRojo);
        stage.addActor(fantasmaRosa);
        stage.addActor(fantasmaAzul);
        stage.addActor(fantasmaNaranja);

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
        fantasmaRojo.detach();
        fantasmaRosa.detach();
        fantasmaAzul.detach();
        fantasmaNaranja.detach();

        botonIzquierdo.detach();
        botonArriba.detach();
        botonDerecha.detach();
        botonAbajo.detach();

        //remueve el actor del stage;
        pacman.remove();
        fantasmaRosa.remove();
        fantasmaRojo.remove();
        fantasmaAzul.detach();
        fantasmaNaranja.remove();

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
        // se ve que no llega a crear el pacman la primer vuelta por eso controlo si no es nulo

            if(this.pacman.isBonificado() ){
                fantasmaRojo.setFear(true);
                fantasmaAzul.setFear(true);
                fantasmaNaranja.setFear(true);
                fantasmaRosa.setFear(true);

                if(!fantasmaRojo.isAlive()){
                    fantasmaRojo.setFear(false);
                }
//                System.out.print(this.tiempoFear);

             /*disminuyo el tiempo de tiempo fear,el proporcional
              de veces por segundo correspondientes a frame*/
                this.tiempoFear= this.tiempoFear-delta;

                /* cuando llega a 0 o menos lo desactivo,
                seteo de nuevo el tiempo y desactivo las animaciones*/
                if(this.tiempoFear<=0){
                    this.pacman.setBonificado(false);
                    tiempoFear = 10f;
                    fantasmaRojo.setFear(false);
                    fantasmaAzul.setFear(false);
                    fantasmaNaranja.setFear(false);
                    fantasmaRosa.setFear(false);
                }

            }

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
    sin esto cada vez q se inicia el juego se iria sobrecargando la memoria */
    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
