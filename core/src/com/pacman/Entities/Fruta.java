package com.pacman.Entities;

import com.badlogic.gdx.graphics.Texture;
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
import static com.pacman.Constantes.Constants.PIXELS_IN_METER;

public class Fruta extends Actor {
    // las texturas de el modo fear y de la muerte del fantasma
    private Texture texture;

    // necesita conocer su figura, su body y su world
    private Fixture fixtureFruta;
    private Body bodyFruta;
    private World world;

    // mapa de bloques, para analizar si en ese lugar hay un punto
//    private TiledMapTileLayer mapa;

    private int valorFruta;

    private boolean colocarFruta;

    private float respawndFruta;

    public Fruta(World myWorld, Texture tex , Vector2 position , int puntaje, float respawnd, String id) {

        // el valor que tiene la fruta en cuanto a puntaje
        valorFruta= puntaje;

        // bandera que indica si se puede colocar la fruta
        colocarFruta= false;
        
        this.world= myWorld;

        // texturas el modo miedo y de la muerte del fantasma
        texture = tex;

        System.out.println("respawnd de la fruta"+ respawnd);

        respawndFruta = respawnd;

        // creo el bodyDef que internamente posee el Body
        BodyDef bodyDefFruta = new BodyDef();
        bodyDefFruta.position.set(position); // le asigno la posicion inicial
        bodyDefFruta.type = BodyDef.BodyType.DynamicBody; // establezco que es un cuero que se mueve

        // instancio el Body en el mundo
        bodyFruta = myWorld.createBody(bodyDefFruta);

        // le doy forma a la figura (como la posicion que se le da al box2d se centra en el 0,0),
        // le indicamos que tiene 0,1 de la longitud tanto de alto como de ancho
        PolygonShape shapeFruta = new PolygonShape();
        shapeFruta.setAsBox(0.1f,0.1f);

        // instancio la figura y le paso el poligono
        fixtureFruta = bodyFruta.createFixture(shapeFruta,3);

        // le asigno un identificador para las colisiones

        fixtureFruta.setUserData("id");

        // declaro el tamaño del actor medio metro
        setSize(PIXELS_IN_METER*0.45f ,PIXELS_IN_METER*0.45f);

        shapeFruta.dispose();
    }

    @Override
    public void act(float delta) {
       // TiledMapTileLayer.Cell cell = mapa.getCell((int)getX(),(int)getY());



            // si en el lugar no hay un punto
            if(respawndFruta<=0){
                colocarFruta=true;
                respawndFruta= respawndFruta;
            }
            respawndFruta = respawndFruta-delta;
//            System.out.println("respawnd: "+respawndFruta);



    }

    // dibujamos el actor
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(colocarFruta){

            // ancho de un bloque en pixeles
  //          float tileWidth = mapa.getTileWidth();

            // alto de un bloque en pixeles(son los mismos en los dos layers)
    //        float tileHeight= mapa.getTileHeight();

            // aqui agrego la altura y ancho de pixeles dnd quier colocar las cosas
            // seria 320 - 22,5 de ancho y 180 - 22.5 de alto
/*
        System.out.println("se dibujo la fruta"+bodyFruta.getPosition().x * tileWidth+
                            "   "+bodyFruta.getPosition().y  * tileHeight);
*/

/*            this.setPosition(bodyFruta.getPosition().x * tileWidth,
                    bodyFruta.getPosition().y * tileHeight);*/
            this.setPosition(bodyFruta.getPosition().x * PIXELS_IN_METER,
                    bodyFruta.getPosition().y * PIXELS_IN_METER);

            // le doy origen al actor en el centro de masa
//            this.setOrigin(getX()*tileWidth,getY()*tileHeight);

//            TextureRegion textureRegion = new TextureRegion(texture);

//            batch.draw(textureRegion,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,0f);

            batch.draw(texture,getX(),getY(),getWidth(),getHeight());
        }
    }

    public void detach(){

        /* se agregan en un metodo aparte por que se llama cuando la pantalla se esconde,
         y no se agrega en un dispose por que este se llama una unica vez,
         mientras si la pantalla es llamada 5 veces nuestra memoria de video quedara saturada*/
        bodyFruta.destroyFixture(fixtureFruta);
        world.destroyBody(bodyFruta);
    }

    public int getValor(){
        return valorFruta;
    }

    // hago que la fruta desaparezca hasta respawnear
    public void fueComida(){
        colocarFruta=false;
        System.out.println("poscion de la fruta"+getX()+" "+getY());
    }

}
