package com.pacman.Pantallas;

import com.badlogic.gdx.Screen;
import com.pacman.MainGame;

// se usa clase abstracta para evitar implementar todos los metodos
// ,cuando se usa, simplemente heredamos esta clase y redefinimos lo que necesitamos de ella

public class BasicScreen implements Screen {

    // siempre se conectan otras pantallas con el juego principal, para obtener recursos (Assets Manager)

    // variable para que las subclases puedan acceder a la pantalla principal

    protected MainGame game;
    public BasicScreen(MainGame game) {
        this.game = game;
    }

    public void setGame(MainGame game) {
        this.game = game;
    }


    // la pantalla que se muestra esta en el metodo show
    @Override
    public void show() {

    }

    /* este metodo es el que se usa para representar la aplicacion o para actualizar el juego ,
	se ejecuta de 30 a 60 veces por segundo se muestran imagenes, movimiento de los personajes etc*/
    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    // Este metodo se ejecuta cuando la pantalla deja de estar activa
    @Override
    public void hide() {

    }

    // Este metodo se ejecuta cuando la pantalla se cierra
    @Override
    public void dispose() {

    }
}
