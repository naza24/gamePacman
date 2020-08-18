package com.pacman.Entities;

import com.badlogic.gdx.Screen;

public class Score  {
    // contador que almacena el puntaje

    private int score;

    public Score(){
        this.score =0;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    // adiciono el valor que llega por parametro
    public void addScore(int valor){
        this.score+=valor;
    }

}
