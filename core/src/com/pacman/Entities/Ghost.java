package com.pacman.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.Recorrido;

import static com.pacman.Constants.PACMAN_VELOCITY;
import static com.pacman.Constants.PIXELS_IN_METER;

public class Ghost extends Actor {
    private int direccion;
    // Animacion de movimiento
    private Animation animacionMovimiento;

    // arreglo que guarda el recorrido de el fantasma
//    private int [] recorrido;

    private Recorrido recorrido;

    // Necesita conocer su textura
    private Texture[] textureGhostMove;
    // tiempo para rotar las animacione
    private float time;

    // necesita conocer su figura, su body y su world
    private Fixture fixtureGhost;
    private Body bodyGhost;
    private World world;

    // mapa de bloques, para analizar colisiones
    private
    TiledMapTileLayer collisionLayer;

    // contador que dice la altura del recorrido , hay que crear una clase recorrido
    int contador;

    public Ghost(World myWorld, Texture tex[], Vector2 position, TiledMapTileLayer collision ){

        this.world= myWorld;
        this.textureGhostMove = new Texture[]{tex[0], tex[1]};
        // bloques de colision
        this.collisionLayer= collision;

        // inicializo el contador y el recorrido
       /* this.recorrido = new int[6];
        this.recorrido[0] = 1;
        this.recorrido[1] = 0;
        this.recorrido[2] = 1;
        this.recorrido[3] = 2;
        this.recorrido[4] = -1;
*/
       this.recorrido = new Recorrido(4,0);
       this.contador=0;

        // creo el bodyDef que internamente posee el Body
        BodyDef bodyDefGhost = new BodyDef();
        bodyDefGhost.position.set(position); // le asigno la posicion inicial
        bodyDefGhost.type = BodyDef.BodyType.DynamicBody; // establezco que es un cuero que se mueve

        // instancio el Body en el mundo
        this.bodyGhost = myWorld.createBody(bodyDefGhost);

        // le doy forma a la figura (como la posicion que se le da al box2d se centra en el 0,0),
        // le indicamos que tiene 0,5 de la longitud tanto de alto como de ancho
        PolygonShape shapeGhost = new PolygonShape();
        shapeGhost.setAsBox(0.1f,0.1f);

        // instancio la figura y le paso el poligono
        this.fixtureGhost = bodyGhost.createFixture(shapeGhost,3);

        // le asigno un identificador para las colisiones
        this.fixtureGhost.setUserData("ghost");

        this.animacionMovimiento= new Animation(0.3f,this.textureGhostMove);

        this.time= 0f;

        // declaro el tamaño del actor medio metro
        // setSize(PIXELS_IN_METER*0.355f ,PIXELS_IN_METER*0.355f);
        setSize(PIXELS_IN_METER*0.45f ,PIXELS_IN_METER*0.45f);

        shapeGhost.dispose();
    }
    // dibujamos el actor
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Texture frameActual = animarGhost(time);
        // aqui agrego la altura y ancho de pixeles dnd quier colocar las cosas
        // seria 320 - 22,5 de ancho y 180 - 22.5 de alto

        this.setPosition(this.bodyGhost.getPosition().x * PIXELS_IN_METER, this.bodyGhost.getPosition().y *PIXELS_IN_METER);
        /* Lo dibujo con la Textura, Posicion X e Y inicial que q le asignamos previamente
         (SetPosition)con el alto y el ancho que dimos en el metodo setSize*/
        TextureRegion textureRegion = new TextureRegion(frameActual);

        batch.draw(textureRegion,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,0f);
    }

    private Texture animarGhost(float time) {
        Texture retorno;

        if(this.bodyGhost.getLinearVelocity().x != 0 || this.bodyGhost.getLinearVelocity().y!=0){
            // recupero el tiempo para saber q frame mostrar
            this.time= time + Gdx.graphics.getDeltaTime();
            retorno = (Texture) this.animacionMovimiento.getKeyFrame(time,true);
            // le doy origen al actor en el centro de masa
            this.setOrigin(getWidth()/2, getHeight()/2);

        }else{
            retorno = this.textureGhostMove[0];
        }
        return retorno;
    }

    @Override
    public void act(float delta) {

        // Si choco con algun elemento del eje Y
        boolean collisionY= false;

        // Si choco con algun elemento del eje Y
        boolean collisionX= false;

        // posicion viejo sobre el eje X
        float oldX = getX();

        // posicion vieja sobre el eje Y
        float oldY= getY();

        // ancho de un bloque en pixeles
        float tileWidth = collisionLayer.getTileWidth();

        // alto de un bloque en pixeles(son los mismos en los dos layers)
        float tileHeight= collisionLayer.getTileHeight();

        // retorno la velocidad
        Vector2 velocidad =null;

        // valor del puntaje del bloque
        int posicionx =0;
        int posiciony=0;


            //this.avanzar(recorrido[contador]);
            this.avanzar(this.recorrido.getDireccion());
        velocidad = bodyGhost.getLinearVelocity();

            if((velocidad.x!=0 || velocidad.y!=0) && !collisionX && !collisionY ){

                if(velocidad.x!=0){
                    //adelanto el actor para ver la colission
                    setX(getX()+(velocidad.x *delta));

                    if(velocidad.x > 0){
                        posicionx = (int) ((getX()+getWidth()) / tileWidth);
                        posiciony = (int) ((getY()+getHeight()/2) / tileHeight);
                    }else{
                        posicionx = (int) ((getX()) / tileWidth);
                        posiciony = (int) ((getY()+getHeight()/2) / tileHeight);
                    }

                   collisionX = collisionedBlock(posicionx,posiciony,"blocked");

                }else {

                    if(velocidad.y!=0){
                        //adelanto el actor para ver la colission
                        setY(getY()+velocidad.y*delta);

                        if(velocidad.y > 0){
                            posicionx = (int) ((getX()+getWidth()/2) / tileWidth);
                            posiciony = (int) ((getY()+getHeight()) / tileHeight);
                        }else{
                            posicionx = (int) ((getX()+getWidth()/ 2)/ tileWidth);
                            posiciony = (int)(getY() / tileHeight);

                        }

                        collisionY = collisionedBlock(posicionx,posiciony,"blocked");

                    }
                }

                if(collisionX){
                    setX(oldX);
                    this.bodyGhost.setLinearVelocity(0,0);
                }

                if(collisionY){
                    setX(oldY);
                    this.bodyGhost.setLinearVelocity(0,0);
                }


        }

    }

    public void detach(){

        /* se agregan en un metodo aparte por que se llama cuando la pantalla se esconde,
         y no se agrega en un dispose por que este se llama una unica vez,
         mientras si la pantalla es llamada 5 veces nuestra memoria de video quedara saturada*/
        bodyGhost.destroyFixture(fixtureGhost);
        world.destroyBody(bodyGhost);
    }


    public void avanzar(int direccion){
        // agregar esta sentencia cuando se agrega el escenari o
        // agregar primero todos los puntos para comer y luego agregar el escenario encima de estos puntos

        switch (direccion){
            case(0):
                this.bodyGhost.setLinearVelocity(-PACMAN_VELOCITY,0);
                break;
            case(1):
                this.bodyGhost.setLinearVelocity(0,PACMAN_VELOCITY);
                break;
            case(2):
                this.bodyGhost.setLinearVelocity(PACMAN_VELOCITY,0);
                break;
            default:
                this.bodyGhost.setLinearVelocity(0,-PACMAN_VELOCITY);
                break;
            }
    }
    private boolean collisionedBlock( int posicionX,int posicionY, String key){

     /*   boolean aux = this.collisionLayer.getCell(posicionX,posicionY).getTile()
                .getProperties().containsKey(key);
*/
     boolean aux =false;
        System.out.print("posicion X "+ posicionX+"  posicion y "+posicionY);
            //if((aux && contador<this.recorrido.length) || (posicionX==36 && posicionY==20)){
        System.out.print(" x: "+posicionX + " y: " + posicionY);
        if(this.recorrido.getPunto().x == posicionX && this.recorrido.getPunto().y == posicionY ){
            this.recorrido.next();
            aux=true;

        }
       /* if((posicionX==21 && posicionY==12) || (posicionX==15 && posicionY==11) ||
                (posicionX==16 && posicionY==21) || (posicionX==33 && posicionY==20) ||
                (posicionX==32 && posicionY==10) || (posicionX==36 && posicionY==11) ||
                (posicionX==35 && posicionY==21) || (posicionX==39 && posicionY==20) ||
                (posicionX==38 && posicionY==6)  || (posicionX==29 && posicionY==7)  ||
                (posicionX==30 && posicionY==0)  || (posicionX==15 && posicionY==1)
        ){
                System.out.println("asdasdasd");
            this.recorrido.next();
                  aux=true;
              }*/
        return aux;
    }
}
