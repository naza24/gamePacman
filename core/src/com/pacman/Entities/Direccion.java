package com.pacman.Entities;
// esta clase se creo con el objetivo de manejar el direccionamiento de pacman ,
// pacman a diferencia de los fantasmas tiene texturas orientadas hacia un soli lado ,
// por lo que hay q rotarla segun la direccion que tome, los fantasmas tienen 2 texturas
// por cada direccion en la que avanzan

public class Direccion {

    // almacena la direccion , 2 derecha, -2 izquierda, 1 arriba, -1 abajo, 0 detenido.
    private int direccion;

    //almacena las rotaciones de la textura (para pacman)
    // 360f derecha, 180f izquierda, 90f arriba, 270f abajo, cuando esta detenido no rota
    private float rotacion;

    public Direccion() {
        this.direccion = 0;
        this.rotacion = 360f;
    }

    public void irDerecha(){
     direccion=2;
     rotacion=360f;
    }
    public void irIzquierda(){
        direccion=-2;
        rotacion=180f;
    }
    public void irArriba(){
        direccion=1;
        rotacion=90f;
    }
    public void irAbajo(){
        direccion=-1;
        rotacion=270f;
    }
    public void parar(){
        direccion=0;
    }

    public int getDireccion() {
        return direccion;
    }

    public float getRotacion() {
        return rotacion;
    }
}
