package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
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
import com.pacman.Constantes.Constants;
import com.pacman.Controller.ControllerButton;
import com.pacman.Entities.Fruta;
import com.pacman.Entities.Ghost;
import com.pacman.Entities.Pacman;
import com.pacman.MainGame;
import com.pacman.factoryMethod.factoryController.FabricaBotones;
import com.pacman.factoryMethod.factoryEntities.FabricaFrutas;
import com.pacman.factoryMethod.factoryEntities.FabricaGhost;
import com.pacman.factoryMethod.factoryEntities.FabricaPacman;

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

    // Label que muestar el puntaje
    private Label labelScore;

    // botones
    private ControllerButton botonIzquierdo;
    private ControllerButton botonArriba;
    private ControllerButton botonDerecha;
    private ControllerButton botonAbajo;

    // frutas
    private Fruta fManzana;
    private Fruta fCereza;
    private Fruta fPera;
    private Fruta fFrutilla;

    // sonidos
    private Sound soundDie;
    private Music soundWaka;
    private Music soundAlarm;
    private Sound soundGhostDie;

    public MainGame game;

    private int puntajeEscenario;

    // variable que indica si la app tiene q tener sonido
    private boolean sonidoOn;

    public GameScreen(MainGame game) {
        super(game);

        // esta variable almacena el puntaje del nivel, el cual si llega gana el juego
        puntajeEscenario = 386;

        this.game = game;

        // Defino las dimensiones del escenario como para mobile
        stage = new Stage(new FitViewport(640, 360));

        // declaro el skin y el cartel (Label) con dicho skin
        // Skin que contiene los caracteres a usar
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        // cargo el cartel de puntaje
        labelScore = new Label("", skin);
        labelScore.setWidth(0.5F * Constants.PIXELS_IN_METER);
        labelScore.setHeight(2F * Constants.PIXELS_IN_METER);

        labelScore.setPosition(stage.getWidth() - labelScore.getWidth(), labelScore.getHeight() / 7);

        //cargo los sonidos
        soundDie = game.getAssetManager().get("datos/sounds/pacman-dies.mp3");
        soundGhostDie = game.getAssetManager().get("datos/sounds/pacman-eating-ghost.mp3");
        soundWaka = game.getAssetManager().get("datos/sounds/pacman-waka-waka.mp3");
        soundAlarm = game.getAssetManager().get("datos/sounds/pacman-alarm.mp3");

        // Defino el mundo y le paso los paramtros para la gravedad
        world = new World(new Vector2(0, 0), true);

        sonidoOn = game.getSonido();
    }

    // Cuando se muestra la pantalla se carga lo que aparece dentro de este metodo
    @Override
    public void show() {

        /* Las coordenadas centrales de dond quiero el pad,
         en base a esto se posicionan los controllerButtons */
        Vector2 controls = new Vector2(12, 0.9f);

        map = new TmxMapLoader().load("maps/nivel1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);

        Gdx.input.setInputProcessor(stage);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                // sin importar si chocan dos fantasmas o alguno de estos con una fruta ,q lo atraviese sin accion
                contact.getFixtureA().setSensor(true);

                // falta controlar que solo pueda comer la fruta cuando halla respawneado
                if(colisionaron(contact,"pacman","manzana")) {
                    pacmanChocoFruta(pacman,fManzana,contact);
                }
                if(colisionaron(contact,"pacman","frutilla")) {
                    pacmanChocoFruta(pacman,fFrutilla,contact);
                }
                if(colisionaron(contact,"pacman","cereza")) {
                    pacmanChocoFruta(pacman,fCereza,contact);
                }
                if(colisionaron(contact,"pacman","pera")) {
                    pacmanChocoFruta(pacman,fPera,contact);
                }

                // logica de colision entre pacman y fantasmas
                if (colisionaron(contact, "pacman", "rojo")) {
                    /* Si pacman esta bonificado el fantasma muere ,
                        caso contrario de q ya este muerto evita la colision*/

                    pacmanChocoFantasma(pacman, fantasmaRojo, contact);
                }

                if (colisionaron(contact, "pacman", "azul")) {
                    pacmanChocoFantasma(pacman, fantasmaAzul, contact);
                }

                if (colisionaron(contact, "pacman", "rosa")) {
                    pacmanChocoFantasma(pacman, fantasmaRosa, contact);
                }

                if (colisionaron(contact, "pacman", "naranja")) {
                    pacmanChocoFantasma(pacman, fantasmaNaranja, contact);
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

            private boolean colisionaron(Contact contact, Object a, Object b) {

                return (contact.getFixtureA().getUserData().equals(a) && contact.getFixtureB().getUserData().equals(b)) ||
                        (contact.getFixtureB().getUserData().equals(a) && contact.getFixtureA().getUserData().equals(b));
            }

            private void pacmanChocoFruta(final Pacman pacman, Fruta fruta, Contact contact) {

                        if(fruta.fueComida()){
                            pacman.actualizarPuntaje(fruta.getValor());
                            playComer();
                        }

                contact.getFixtureA().setSensor(true);
            }

            private void pacmanChocoFantasma(final Pacman pacman, Ghost fantasma, Contact contact) {

                if (pacman.isBonificado() && fantasma.isAlive() && fantasma.isFear()) {
                    fantasma.dead();
                    fantasma.fearOff();
                    playComer();
                }

                if ((!pacman.isBonificado() &&  pacman.isAlive() && fantasma.isAlive()) ||
                        (pacman.isBonificado() && fantasma.isAlive() && !fantasma.isFear())) {
                        pacman.dead();
                        playPacmanDead();
                            cambiarPantalla(game.gameOverScreen,5f);
                    }

                // los actores se traspasan el uno al otro  para continuar el recorrido
                contact.getFixtureA().setSensor(true);
            }
        });

        FabricaPacman fabPacman = new FabricaPacman(game.getAssetManager());
        pacman = (Pacman) fabPacman.crearActor(world, new Vector2(7.3f, 2.1f), map);

        FabricaGhost fabricaGhost = new FabricaGhost(game.getAssetManager());
        fantasmaAzul = (Ghost) fabricaGhost.crearGhostBlue(world, new Vector2(6.3f, 3.2f), map);
        fantasmaRojo = (Ghost) fabricaGhost.crearGhostRed(world, new Vector2(6.6f, 3.2f), map);
        fantasmaRosa = (Ghost) fabricaGhost.crearGhostPink(world, new Vector2(7.6f, 3.2f), map);
        fantasmaNaranja = (Ghost) fabricaGhost.crearGhostOrange(world, new Vector2(8.2f, 3.2f), map);

        FabricaBotones fabBotones = new FabricaBotones(game.getAssetManager());
        botonIzquierdo = (ControllerButton) fabBotones.crearBotonIzquierdo(world, controls, pacman);
        botonDerecha = (ControllerButton) fabBotones.crearBotonDerecho(world, controls, pacman);
        botonArriba = (ControllerButton) fabBotones.crearBotonArriba(world, controls, pacman);
        botonAbajo = (ControllerButton) fabBotones.crearBotonAbajo(world, controls, pacman);

        FabricaFrutas fabFrut = new FabricaFrutas(game.getAssetManager());

        fManzana =(Fruta)fabFrut.crearFrutaManzana(world);
        fCereza =(Fruta)fabFrut.crearFrutaCereza(world);
        fFrutilla =(Fruta)fabFrut.crearFrutaFrutilla(world);
        fPera =(Fruta)fabFrut.crearFrutaPera(world);

        /*Agrego los actores al escenario*/

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

        stage.addActor(fManzana);
        stage.addActor(fCereza);
        stage.addActor(fFrutilla);
        stage.addActor(fPera);
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

        fManzana.detach();
        fCereza.detach();
        fFrutilla.detach();
        fPera.detach();

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

        fManzana.remove();
        fCereza.remove();
        fFrutilla.remove();
        fPera.remove();


        map.dispose();
        tmr.dispose();

    }

    // FALTA REINICIAR EL CONTADOR CUANDO TOCA OTRO PNTO MAX ,
    /* este metodo es el que se usa para representar la aplicacion o para actualizar el juego ,
        se ejecuta de 30 a 60 veces por segundo muestra imagenes, movimiento de los personajes etc*/
    @Override
    public void render(float delta) {
        // Le aplico color al fondo
        Gdx.gl.glClearColor(0, 0, 0, 1);

        // limpio el buffer de video
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (pacman.isBonificado()) {

            if (pacman.resetBonificacion(delta)) {

                if (fantasmaRojo.isAlive() ) {
                    fantasmaRojo.fearOn();
                }
                if (fantasmaAzul.isAlive()) {
                    fantasmaAzul.fearOn();
                }
                if (fantasmaNaranja.isAlive()) {
                    fantasmaNaranja.fearOn();
                }
                if (fantasmaRosa.isAlive()) {
                    fantasmaRosa.fearOn();
                }
            }
        } else {
            fantasmaRojo.fearOff();
            fantasmaAzul.fearOff();
            fantasmaNaranja.fearOff();
            fantasmaRosa.fearOff();
        }

        if(pacman.getContadorPuntosNivel() == puntajeEscenario){
            cambiarPantalla(game.gameWinScreen,2f);

        }


        playAlarma();
        playWaka();
        tmr.setView((OrthographicCamera) stage.getCamera());

        tmr.render();

        this.labelScore.setText(this.pacman.getPuntaje().toString());

        // Actualizo los actores
        stage.act();

        //Actualizo el mundo, delta indica cuando fue la ultima vez que se ejecuto render
        world.step(delta, 6, 2);

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

    private void playAlarma() {
        if(sonidoOn) {
            if (pacman.isBonificado()) {
                if (!soundAlarm.isPlaying()) {
                    soundAlarm.play();
                }
            }

            if (soundAlarm.isPlaying() & !pacman.isBonificado()) {
                soundAlarm.pause();
            }
        }
    }

    private void playWaka() {
        if (sonidoOn) {
            if (pacman.enMovimimento()) {
                if (!soundWaka.isPlaying()) {
                    soundWaka.play();
                }
            }else {
                if(soundWaka.isPlaying()){
                    soundWaka.pause();
                }
            }
        }
    }

    private void playComer() {
        if(sonidoOn){
            soundGhostDie.play(0.5f);
        }
    }

    private void playPacmanDead() {
        if(sonidoOn){
            soundDie.play(0.5f,0.9f,0);
        }
    }

    private void cambiarPantalla(final Screen pantalla, float delay){
        // addaction es para hacer animaciones
        stage.addAction(
                // para efectuar una secuencia de acciones utilizamos Actions Secuences.
                //Que es una secuencia de acciones

                // es para dar la accion de esperar unos segundos
                Actions.sequence(
                        Actions.delay(delay), // QUE ESPERO 1,5 SEGUNDOS
                        // Creo una accion de run para lanzar la pantalla de game Over
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                // el hilo ejecutara el lanzamiento de la pantalla con
                                // la variable que se inicializo en main game, pero antes le asigno el puntaje
                                int score = (pacman.getPuntaje()).getScore();
                                game.setPuntajePlayer(score);
                                game.setScreen(pantalla);
                            }
                        })
                )
        );
    }
}
