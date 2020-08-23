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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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

    // Label que muestar el puntaje
    private Label labelScore;

    // Skin que contiene los caracteres a usar
    private Skin skin;

    // botones
    private ControllerButton botonIzquierdo;
    private ControllerButton botonArriba;
    private ControllerButton botonDerecha;
    private ControllerButton botonAbajo;

    public MainGame game;

    public GameScreen(MainGame game) {
        super(game);

        this.game= game;

        // Defino las dimensiones del escenario como para mobile
        stage = new Stage(new FitViewport(640,360));

        // declaro el skin y el cartel (Label) con dicho skin
        this.skin= new Skin(Gdx.files.internal("skin/uiskin.json"));


        // cargo el cartel de puntaje
        this.labelScore = new Label("", skin);

        labelScore.setWidth(0.5F*Constants.PIXELS_IN_METER);
        labelScore.setHeight(2F*Constants.PIXELS_IN_METER);

        labelScore.setPosition(stage.getWidth() - labelScore.getWidth() , labelScore.getHeight()/7);


        // Defino el mundo y le paso los paramtros para la gravedad
        world = new World(new Vector2(0,0),true);

    }

    // Cuando se muestra la pantalla se carga lo que aparece dentro de este metodo
    @Override
    public void show() {

        // recupero el tiempo
        tiempoFear = 10f;

        // para entrar en modo debug
         //stage.setDebugAll(true);

        // las coordenadas centrales de dond quiero el pad
        Vector2 controls = new Vector2(12,0.9f);

        map = new TmxMapLoader().load("maps/nivel1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);

        Gdx.input.setInputProcessor(stage);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                String c1 = (String) contact.getFixtureA().getUserData();
                String c2 = (String) contact.getFixtureB().getUserData();

                /* si colisionan dos fantasmas, desactivo la colision en uno al iniciar el
                 contacto y lo vuelvo a activar al finalizar el choque */

                if(collisionGhostGhost(contact,c1, c2)){
                    contact.getFixtureA().setSensor(true);
                }

                //////////////////////// hacer un metodo
                if(colisionaron(contact,"pacman","rojo")){
                    /* Si pacman esta bonificado el fantasma muere ,
                        caso contrario de q ya este muerto evita la colision*/

                    if(pacman.isBonificado() || !fantasmaRojo.isAlive()){
                        if(pacman.isBonificado()){
                            fantasmaRojo.setAlive(false);
                        }
                        contact.getFixtureA().setSensor(true);
                    }
                    if(!pacman.isBonificado()){
                        // para que no sigan colisionando
                        if(pacman.isAlive()){
                            pacman.dead();
                            //game.setScreen(game.gameOverScreen);
                            //falta agregarle el delay
                            //game.setScreen(game.gameOverScreen);
                            // addaction es para hacer animaciones
                            stage.addAction(
                                    // para efectuar una secuencia de acciones utilizamos Actions Secuences.
                                    //Que es una secuencia de acciones

                                    // es para dar la accion de esperar unos segundos
                                    Actions.sequence(
                                            Actions.delay(5f), // QUE ESPERO 1,5 SEGUNDOS
                                            // Creo una accion de run para lanzar la pantalla de game Over
                                            Actions.run(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // el hilo ejecutara el lanzamiento de la pantalla con
                                                    // la variable que se inicializo en main game
                                                    game.setScreen(game.gameOverScreen);
                                                }
                                            })
                                    )
                            );
                        }
                        // espera unos segundos y lanza la pantalla game over
                        contact.getFixtureA().setSensor(true);
                    }
                }
                ////////////////////////// hasta aca
                if(colisionaron(contact,"pacman","azul")){
                    /* Si pacman esta bonificado el fantasma muere ,
                        caso contrario de q ya este muerto evita la colision*/

                    if(pacman.isBonificado() || !fantasmaAzul.isAlive()){
                        if(pacman.isBonificado()){
                            fantasmaAzul.setAlive(false);
                        }
                        contact.getFixtureA().setSensor(true);
                    }
                    if(!pacman.isBonificado()){
                        if(pacman.isAlive()){
                            pacman.dead();
                            //game.setScreen(game.gameOverScreen);
                            // addaction es para hacer animaciones
                            stage.addAction(
                                    // para efectuar una secuencia de acciones utilizamos Actions Secuences.
                                    //Que es una secuencia de acciones

                                    // es para dar la accion de esperar unos segundos
                                    Actions.sequence(
                                            Actions.delay(5f), // QUE ESPERO 1,5 SEGUNDOS
                                            // Creo una accion de run para lanzar la pantalla de game Over
                                            Actions.run(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // el hilo ejecutara el lanzamiento de la pantalla con
                                                    // la variable que se inicializo en main game
                                                    game.setScreen(game.gameOverScreen);
                                                }
                                            })
                                    )
                            );
//                            game.setScreen(game.gameOverScreen);
                        }
                        // espera unos segundos y lanza la pantalla game over
                        contact.getFixtureA().setSensor(true);
                    }

                }
                if(colisionaron(contact,"pacman","rosa")){
                    /* Si pacman esta bonificado el fantasma muere ,
                        caso contrario de q ya este muerto evita la colision*/

                    if(pacman.isBonificado() || !fantasmaRosa.isAlive()){
                        if(pacman.isBonificado()){
                            fantasmaRosa.setAlive(false);
                        }
                        contact.getFixtureA().setSensor(true);
                    }
                    if(!pacman.isBonificado()){
                        if(pacman.isAlive()){
                            pacman.dead();
                           // game.setScreen(game.gameOverScreen);
                       //game.setScreen(game.gameOverScreen);
                            // addaction es para hacer animaciones
                            stage.addAction(
                                    // para efectuar una secuencia de acciones utilizamos Actions Secuences.
                                    //Que es una secuencia de acciones

                                    // es para dar la accion de esperar unos segundos
                                    Actions.sequence(
                                            Actions.delay(5f), // QUE ESPERO 1,5 SEGUNDOS
                                            // Creo una accion de run para lanzar la pantalla de game Over
                                            Actions.run(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // el hilo ejecutara el lanzamiento de la pantalla con
                                                    // la variable que se inicializo en main game
                                                    game.setScreen(game.gameOverScreen);
                                                }
                                            })
                                    )
                            );
                        }

                        // espera unos segundos y lanza la pantalla game over
                        contact.getFixtureA().setSensor(true);
                    }

                }
                if(colisionaron(contact,"pacman","naranja")){
                    /* Si pacman esta bonificado el fantasma muere ,
                        caso contrario de q ya este muerto evita la colision*/

                    if(pacman.isBonificado() || !fantasmaNaranja.isAlive()){
                        if(pacman.isBonificado()){
                            fantasmaNaranja.setAlive(false);
                        }
                        contact.getFixtureA().setSensor(true);
                    }
                    if(!pacman.isBonificado()){
                        if(pacman.isAlive()){
                            pacman.dead();
                            //game.setScreen(game.gameOverScreen);
                            // addaction es para hacer animaciones
                            stage.addAction(
                                    // para efectuar una secuencia de acciones utilizamos Actions Secuences.
                                    //Que es una secuencia de acciones

                                    // es para dar la accion de esperar unos segundos
                                    Actions.sequence(
                                            Actions.delay(5f), // QUE ESPERO 1,5 SEGUNDOS
                                            // Creo una accion de run para lanzar la pantalla de game Over
                                            Actions.run(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // el hilo ejecutara el lanzamiento de la pantalla con
                                                    // la variable que se inicializo en main game
                                                   game.setScreen(game.gameOverScreen);
                                                }
                                            })
                                    )
                            );
                        }
                        // espera unos segundos y lanza la pantalla game over
                        contact.getFixtureA().setSensor(true);
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

            private boolean colisionaron (Contact contact, Object a, Object b){

                return  (contact.getFixtureA().getUserData().equals(a) && contact.getFixtureB().getUserData().equals(b)) ||
                        (contact.getFixtureB().getUserData().equals(a)  && contact.getFixtureA().getUserData().equals(b));
            }

            private boolean collisionGhostGhost(Contact contact, String ghost1, String ghost2){
                boolean retorno = false;

                if( colisionaron(contact, "rojo", "azul")   || colisionaron(contact, "rojo", "rosa")   ||
                        colisionaron(contact, "rojo", "naranja")|| colisionaron(contact, "azul", "rosa")   ||
                        colisionaron(contact, "azul", "naranja")|| colisionaron(contact, "rosa", "naranja")){
                    retorno = true;
                }
                return retorno;
            }

        });

        // recupero las texturas
        Texture [] texturePacman = new Texture[14];
        texturePacman[0] = game.getAssetManager().get("datos/pac_man_0.png");
        texturePacman[1] = game.getAssetManager().get("datos/pac_man_1.png");
        texturePacman[2] = game.getAssetManager().get("datos/pac_man_2.png");
        //texturePacman[3] = game.getAssetManager().get("datos/pac_man_muerte0.png"); // sacar esto del repo
        //texturePacman[4] = game.getAssetManager().get("datos/pac_man_muerte1.png"); // sacar esto del repo

        // texturas de la muerte de pacman
        texturePacman[3] = game.getAssetManager().get("datos/dead pacman/dead_pacman_0.png");
        texturePacman[4] = game.getAssetManager().get("datos/dead pacman/dead_pacman_1.png");
        texturePacman[5] = game.getAssetManager().get("datos/dead pacman/dead_pacman_2.png");
        texturePacman[6] = game.getAssetManager().get("datos/dead pacman/dead_pacman_3.png");
        texturePacman[7] = game.getAssetManager().get("datos/dead pacman/dead_pacman_4.png");
        texturePacman[8] = game.getAssetManager().get("datos/dead pacman/dead_pacman_5.png");
        texturePacman[9] = game.getAssetManager().get("datos/dead pacman/dead_pacman_6.png");
        texturePacman[10] = game.getAssetManager().get("datos/dead pacman/dead_pacman_7.png");
        texturePacman[11] = game.getAssetManager().get("datos/dead pacman/dead_pacman_8.png");
        texturePacman[12] = game.getAssetManager().get("datos/dead pacman/dead_pacman_9.png");
        texturePacman[13] = game.getAssetManager().get("datos/dead pacman/dead_pacman_10.png");

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

         botonIzquierdo= new ControllerButton(this.world,pacman,textBtnIzquierda,0,new Vector2(controls.x-0.6f,controls.y));
         botonArriba= new ControllerButton(this.world,pacman,textBtnArriba,1,new Vector2(controls.x,controls.y+0.6f));
         botonDerecha= new ControllerButton(this.world,pacman,textBtnDerecha,2,new Vector2(controls.x+0.6f,controls.y));
         botonAbajo= new ControllerButton(this.world,pacman,textBtnAbajo,3,new Vector2(controls.x,controls.y-0.6f));

        //Agrego los actores al escenario
        stage.addActor(pacman);
        stage.addActor(fantasmaRojo);
        stage.addActor(fantasmaRosa);
        stage.addActor(fantasmaAzul);
        stage.addActor(fantasmaNaranja);

        stage.addActor(labelScore);

        stage.addActor(botonIzquierdo);
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
        fantasmaAzul.remove();
        fantasmaNaranja.remove();



        botonIzquierdo.remove();
        botonArriba.remove();
        botonDerecha.remove();
        botonAbajo.remove();

        map.dispose();
        tmr.dispose();

    }

    // FALTA REINICIAR EL CONTADOR CUANDO TOCA OTRO PNTO MAX ,
    /* este metodo es el que se usa para representar la aplicacion o para actualizar el juego ,
        se ejecuta de 30 a 60 veces por segundo muestra imagenes, movimiento de los personajes etc*/
    @Override
    public void render(float delta) {
        // Le aplico color al fondo
        Gdx.gl.glClearColor(0,0,0,1);

        // limpio el buffer de video
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            if(this.pacman.isBonificado() ){
                fantasmaRojo.setFear(true);
                fantasmaAzul.setFear(true);
                fantasmaNaranja.setFear(true);
                fantasmaRosa.setFear(true);

                if(!fantasmaRojo.isAlive()){
                    fantasmaRojo.setFear(false);
                }
                if(!fantasmaAzul.isAlive()){
                    fantasmaAzul.setFear(false);
                }
                if(!fantasmaNaranja.isAlive()){
                    fantasmaNaranja.setFear(false);
                }
                if(!fantasmaRosa.isAlive()){
                    fantasmaRosa.setFear(false);
                }

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

        this.labelScore.setText(this.pacman.getPuntaje().toString());

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
