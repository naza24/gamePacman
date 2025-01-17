package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.pacman.MainGame;

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

        // Inicializo por defecto los botones
        String cartel ="";
        String bSalir = "";
        String bReintentar ="";

        if(game.idiomaIngles()){
            cartel = "Game Over";
            bSalir = "Quit";
            bReintentar = "Retry";
        }else{
            cartel = "Has Perdido";
            bSalir = "Salir";
            bReintentar = "Reintentar";
        }

        // creo el cartel
        gameover = new Label(cartel, skin);

        // creo el boton para reintentar y le paso el skin
        retry = new TextButton(bReintentar,skin);

        // creo el boton para volver al menu principal
        quit = new TextButton(bSalir,skin);

        // posiciono el cartel de gameover en el centro
        this.gameover.setPosition(stage.getWidth()/2 -this.gameover.getWidth()/2, stage.getHeight()/2-this.gameover.getHeight()/2);

        // le doy tamaño y posiciono los botones
        retry.setSize(100,30);
        retry.setPosition(stage.getWidth()/2 -this.retry.getWidth()/2 ,stage.getHeight()/4 );

        quit.setSize(100,30);
        quit.setPosition(stage.getWidth()/2 -this.retry.getWidth()/2 ,stage.getHeight()/7 );


        // agrego el cartel y el boton al escenario
        stage.addActor(gameover);
        stage.addActor(retry);
        stage.addActor(quit);

        // LE PONEMOS UN LISTENER A EL BOTON PARA QUE SEPA CUANDO FUE PRESIONADO
        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // a el juego le damos la pantalla del juego principal (una nueva)
                game.setScreen(new GameScreen(game)); // aca hay q setearle el usuario de nuevo
            }
        });

        quit.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // obtenemos la llamada de retorno de mainGame y ejecutamos
                // el metodo para volver al menu principal con el puntaje
                /*System.out.println(game.getUsuario()+" ........ ");*/
                game.getMyGameCallBack().volverMenuPrincipal(game.getUsuario(),game.getPuntajePlayer());
            }
        });
    }

    @Override
    public void render(float delta) {

        // color del fondo simulando el cielo
        Gdx.gl.glClearColor(0,0,0,1);

        // limpio el buffer de video
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // actualizar los actores en cada iteracion
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
