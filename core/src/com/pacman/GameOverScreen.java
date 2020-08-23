package com.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class GameOverScreen extends BasicScreen{

    private Stage stage;

    // los skins que se usan para los carteles
    private Skin skin;

    // boton para volver a jugar
    private TextButton retry;

    // boton para volver al menu principal
    private TextButton quit;

    // cartel de gameOver
    private Label gameover;


    public MainGame game;

    public GameOverScreen(final MainGame game) {
        super(game);

        this.game= game;

        stage = new Stage(new FillViewport(640,360));

        /* le decimos que queremos acceder a los archivos internos y cargar el archivo
            que esta en la ruta "skin/..." */
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // creo el cartel
        gameover = new Label("Game Over", skin);

        // creo el boton para reintentar y le paso el skin
        retry = new TextButton("Reintentar",skin);

        // creo el boton para volver al menu principal
        quit = new TextButton("Salir",skin);

        // posiciono el cartel de gameover en el centro
        this.gameover.setPosition(stage.getWidth()/2 -this.gameover.getWidth()/2, stage.getHeight()/2-this.gameover.getHeight()/2);

        // le doy tamaño y posiciono los botones
        retry.setSize(100,30);
        retry.setPosition(stage.getWidth()/2 -this.retry.getWidth()/2 ,stage.getHeight()/4 );

        // agrego el cartel y el boton al escenario
        stage.addActor(gameover);
        stage.addActor(retry);

        // LE PONEMOS UN LISTENER A EL BOTON PARA QUE SEPA CUANDO FUE PRESIONADO
        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // a el juego le damos la pantalla del juego principal
                game.setScreen(new GameScreen(game));

            }
        });
    }

    @Override
    public void render(float delta) {


        // color del fondo simulando el cielo
        Gdx.gl.glClearColor(0,0,0,1);

        // limpio el buffer de video
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // actualizar los actores, x ejemplo acercar el fuego en cada iteracion
        stage.act();


        //dibujar todos los actores, Siempre dibujar despues de hacer las actualizaciones
        // y cualquier comprobacion que se requiera
        stage.draw();

    }
    @Override
    public void show() {
        // lo creo cada vez q e muestra la pantalla
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        // lo saco cada vez q se cierre la pantalla para q no haya problemas
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
