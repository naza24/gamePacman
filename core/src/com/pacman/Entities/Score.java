package com.pacman.Entities;

public class Score  {
    // contador que almacena el puntaje

    private int score;

    public Score(){
        this.score =0;
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
        String retorno=null;         // cadena que retorna

        /*inicializo en 0 si recien esta iniciando
         sino cadena nula para no agregar 0s en cualquier lado*/
        if(cociente == 0){
            retorno = "0";
        }else{
            retorno ="";
        }

        while(cociente!=0){
            resto = (cociente % 10);
            retorno = resto +"\n"
                      +retorno;
            cociente = cociente/10;
        }

        return retorno;
    }
}
