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

    public String toString(){
        // este tosctring me va a retornar un string de el score en forma vertical
        int cociente = this.score; // cociente de la operacion
        int resto =1;              // resto de la operacion
        String retorno="";         // cadena que retorna

        while(cociente!=0){
            resto = (cociente % 10);
            retorno = resto +"\n"
                      +retorno;
            cociente = cociente/10;
            System.out.println(retorno);
        }

        return retorno;
    }
}
