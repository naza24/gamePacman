package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.pacman.MainGame;

public class LoadingScreen extends BasicScreen {
    private Stage stage;

    private Skin skin;

    private Label carga;

    private MainGame game;

    private String cartel;

    public LoadingScreen(final MainGame game) {
        super(game);

        this. game = game;

        stage = new Stage(new FillViewport(640,360));
        /* le decimos que queremos acceder a los archivos internos y cargar el archivo
            que esta en la ruta "skin/..." */
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // cargo el cartel de carga y lo agrego al escenario
        if(game.idiomaIngles()){
            cartel ="Loading...";
        }else{
            cartel ="Cargando...";
        }
        carga = new Label(cartel,skin);

        carga.setPosition(320 - carga.getWidth  ()/2 , 180 - carga.getHeight()/2);
        stage.addActor(carga);

    }
    // ESTE METODO SE EJECUTA SIEMPRE
    @Override
    public void render(float delta) {

        // color del fondo simulando el cielo
        Gdx.gl.glClearColor(0,0,0,1);

        // limpio el buffer de video
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update va actualizando la cola de carga del manager, y retorna true cuando termina
        if(game.getAssetManager().update()){
            game.terminarDeCargar();
        }else{
            // get progress nos da un valor entre 0 y 1 que nos dice como va la carga
            int porcentaje = (int) (game.getAssetManager().getProgress() * 100) ;
            carga.setText(cartel+" "+porcentaje);

        }

        stage.act();

        stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
