package com.pacman;

import com.badlogic.gdx.math.Vector2;

public class Recorrido {

    // arreglo que almacena los puntos del recorrido
    private Vector2 [] recorrido;

    // direccion que tomara para llegar a cada punto del recorrido
    private int[] direccionRecorrido;

    // lleva el indicador que recorre el arreglo
    private int contador;

    // segundos antes de iniciar el recorrido
    float seg;

    // Id de recorrido
    private int numeroRecorrido;

    public Recorrido(int numeroRecorrido, float seg) {
        this.seg = seg;
        this.contador =0;
        /* dependiendo del numero de recorrido es dond empezar cuando
         se reinicie el contador de el arreglo de vectores2 */
        this.numeroRecorrido=numeroRecorrido;

        switch (numeroRecorrido){
            case(1):
                this.recorrido = new Vector2[16];

                // cuando muera arranca desde el 1
                this.recorrido[0]= new Vector2(22,9);
                this.recorrido[1]= new Vector2(21,12);
                this.recorrido[2]= new Vector2(15,11);
                this.recorrido[3]= new Vector2(16,21);
                this.recorrido[4]= new Vector2(33,20);
                this.recorrido[5] =new Vector2(32,10);
                this.recorrido[6]= new Vector2(36,11);
                this.recorrido[7]= new Vector2(35,21);
                this.recorrido[8]= new Vector2(39,20);
                this.recorrido[9]= new Vector2(38,6);
                this.recorrido[10]= new Vector2(29,7);
                this.recorrido[11]= new Vector2(30,0);
                this.recorrido[12]= new Vector2(15,1);
                this.recorrido[13]= new Vector2(16,12);
                this.recorrido[14]= new Vector2(22,11);
                this.recorrido[15]= new Vector2(21,8);

                this.direccionRecorrido = new int [16];

                this.direccionRecorrido[0]=2; // arriba
                this.direccionRecorrido[1]=1; // arriba
                this.direccionRecorrido[2]=0; // izquierda
                this.direccionRecorrido[3]=1;
                this.direccionRecorrido[4]=2; // derecha
                this.direccionRecorrido[5]=-1; // abajo
                this.direccionRecorrido[6]=2;
                this.direccionRecorrido[7]=1;
                this.direccionRecorrido[8]=2;
                this.direccionRecorrido[9]=-1;
                this.direccionRecorrido[10]=0;
                this.direccionRecorrido[11]=-1;
                this.direccionRecorrido[12]=0;
                this.direccionRecorrido[13]=1;
                this.direccionRecorrido[14]=2;
                this.direccionRecorrido[15]=-1;
                break;
            case(2):
                this.recorrido = new Vector2[22];

                this.recorrido[0]= new Vector2(22,9);
                this.recorrido[1]= new Vector2(21,12);
                this.recorrido[2]= new Vector2(15,11);
                this.recorrido[3]= new Vector2(16,19);
                this.recorrido[4]= new Vector2(12,18);
                this.recorrido[5]= new Vector2(13,21);
                this.recorrido[6]= new Vector2(3,20);
                this.recorrido[7]= new Vector2(4,0);
                this.recorrido[8]= new Vector2(14,1);
                this.recorrido[9]= new Vector2(13,4);
                this.recorrido[10]= new Vector2(17,3);
                this.recorrido[11]= new Vector2(16,7);
                this.recorrido[12]= new Vector2(24,6);
                this.recorrido[13]= new Vector2(23,3);
                this.recorrido[14]= new Vector2(26,4);
                this.recorrido[15]= new Vector2(25,1);
                this.recorrido[16]= new Vector2(29,2);
                this.recorrido[17]= new Vector2(28,21);
                this.recorrido[18]= new Vector2(24,20);
                this.recorrido[19]= new Vector2(25,10);
                this.recorrido[20]= new Vector2(20,11);
                this.recorrido[21]= new Vector2(21,8);

                this.direccionRecorrido = new int [22];

                this.direccionRecorrido[0]=2;
                this.direccionRecorrido[1]=1; // arriba
                this.direccionRecorrido[2]=0; // izquierda
                this.direccionRecorrido[3]=1;
                this.direccionRecorrido[4]=0;
                this.direccionRecorrido[5]=1;
                this.direccionRecorrido[6]=0;
                this.direccionRecorrido[7]=-1;
                this.direccionRecorrido[8]=2;
                this.direccionRecorrido[9]=1;
                this.direccionRecorrido[10]=2;
                this.direccionRecorrido[11]=1;
                this.direccionRecorrido[12]=2;
                this.direccionRecorrido[13]=-1;
                this.direccionRecorrido[14]=2;
                this.direccionRecorrido[15]=-1;
                this.direccionRecorrido[16]=2;
                this.direccionRecorrido[17]=1;
                this.direccionRecorrido[18]=0;
                this.direccionRecorrido[19]=-1;
                this.direccionRecorrido[20]=0;
                this.direccionRecorrido[21]=-1;
                break;
            case(3):

                this.recorrido = new Vector2[18];

                this.recorrido[0]= new Vector2(20,9);
                this.recorrido[1]= new Vector2(21,12);
                this.recorrido[2]= new Vector2(15,11);
                this.recorrido[3]= new Vector2(16,16);
                this.recorrido[4]= new Vector2(9,15);
                this.recorrido[5]= new Vector2(10,5);
                this.recorrido[6]= new Vector2(6,6);
                this.recorrido[7]= new Vector2(7,0);
                this.recorrido[8]= new Vector2(0,1);
                this.recorrido[9]= new Vector2(1,21);
                this.recorrido[10]= new Vector2(17,20);
                this.recorrido[11]= new Vector2(16,16);
                this.recorrido[12]= new Vector2(20,17);
                this.recorrido[13]= new Vector2(19,14);
                this.recorrido[14]= new Vector2(26,15);
                this.recorrido[15]= new Vector2(25,10);
                this.recorrido[16]= new Vector2(20,11);
                this.recorrido[17]= new Vector2(21,8);


                this.direccionRecorrido = new int [18];

                this.direccionRecorrido[0]=0; // arriba
                this.direccionRecorrido[1]=1; // arriba
                this.direccionRecorrido[2]=0; // izquierda
                this.direccionRecorrido[3]=1;
                this.direccionRecorrido[4]=0;
                this.direccionRecorrido[5]=-1;
                this.direccionRecorrido[6]=0;
                this.direccionRecorrido[7]=-1;
                this.direccionRecorrido[8]=0;
                this.direccionRecorrido[9]=1;
                this.direccionRecorrido[10]=2;
                this.direccionRecorrido[11]=-1;
                this.direccionRecorrido[12]=2;
                this.direccionRecorrido[13]=-1;
                this.direccionRecorrido[14]=2;
                this.direccionRecorrido[15]=-1;
                this.direccionRecorrido[16]=0;
                this.direccionRecorrido[17]=-1;


                break;

            default:
                this.recorrido = new Vector2[18];

                this.recorrido[0]= new Vector2(20,9);
                this.recorrido[1]= new Vector2(21,12);
                this.recorrido[2]= new Vector2(26,11);
                this.recorrido[3]= new Vector2(25,6);
                this.recorrido[4]= new Vector2(15,7);
                this.recorrido[5]= new Vector2(16,5);
                this.recorrido[6]= new Vector2(6,6);
                this.recorrido[7]= new Vector2(7,16);
                this.recorrido[8]= new Vector2(24,15);
                this.recorrido[9]= new Vector2(23,18);
                this.recorrido[10]= new Vector2(26,17);
                this.recorrido[11]= new Vector2(25,20);
                this.recorrido[12]= new Vector2(30,19);
                this.recorrido[13]= new Vector2(29,0);
                this.recorrido[14]= new Vector2(24,1);
                this.recorrido[15]= new Vector2(25,12);
                this.recorrido[16]= new Vector2(20,11);
                this.recorrido[17]= new Vector2(21,8);

                this.direccionRecorrido = new int [18];

                this.direccionRecorrido[0]=0; // arriba
                this.direccionRecorrido[1]=1; // arriba
                this.direccionRecorrido[2]=2; // derecha
                this.direccionRecorrido[3]=-1;
                this.direccionRecorrido[4]=0;
                this.direccionRecorrido[5]=-1;
                this.direccionRecorrido[6]=0;
                this.direccionRecorrido[7]=1;
                this.direccionRecorrido[8]=2;
                this.direccionRecorrido[9]=1;
                this.direccionRecorrido[10]=2;
                this.direccionRecorrido[11]=1;
                this.direccionRecorrido[12]=2;
                this.direccionRecorrido[13]=-1;
                this.direccionRecorrido[14]=0;
                this.direccionRecorrido[15]=1;
                this.direccionRecorrido[16]=0;
                this.direccionRecorrido[17]=-1;

                break;
        }
    }

    // avanza el indice
    public void next(){
        if(contador<this.direccionRecorrido.length-1){
            this.contador++;
        }else{
            this.contador=1;
        }
    }

    // Da el vector a dnd apunta el recorrido
    public Vector2 getPunto(){
        return this.recorrido[contador];
    }

    // Da la direccion dnd apunta el indice
    public int getDireccion(){
        return this.direccionRecorrido[contador];
    }

    public float getSeg() {
        return seg;
    }

}
