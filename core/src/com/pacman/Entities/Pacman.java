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

import static com.pacman.Constantes.Constants.PACMAN_VELOCITY;
import static com.pacman.Constantes.Constants.PIXELS_IN_METER;

public class Pacman extends Actor {

    // Animacion de movimiento
    private Animation animacionMovimiento;

    // Animacion de muerte
    private Animation animacionMuerte;

    // Necesita conocer su textura
    private Texture [] texturePacman;

    // tiempo para rotar las animacione
    private float time;

    // ultima direccion
    int ultimaDireccion;

    // si pacman esta vivo
    private boolean alive=true;

    //tiene la bonificacion para comer fantasmas
    private boolean bonificado=false;

    // guarda la ultima rotacion para que no gire de golpe
    float ultimaRotacion;

    // necesita conocer su figura, su body y su world
    private Fixture fixturePacman;
    private Body bodyPacman;
    private World world;

    // mapa de bloques, para analizar colisiones
    private
    TiledMapTileLayer collisionLayer;

    // mapa de bloques para analizar las coliciones pero con los puntos
    private TiledMapTileLayer pointsLayer;

    // puntaje del jugador (referenciando a cada jugador )
    private Score score;

    // Al constructor se le pasa el mundo, la textura y un vector con la posicion inicial
    public Pacman(World myWorld, Texture tex[], Vector2 position,TiledMapTileLayer pointsLayer,  TiledMapTileLayer collision ){

        // inicia pacman con estado normal y vivo
        this.alive=true;
        this.bonificado=false;

        this.world= myWorld;
        this.texturePacman = new Texture[]{tex[0], tex[1], tex[2], tex[3], tex[4], tex[5],
                                           tex[6], tex[7], tex[8], tex[9], tex[10],tex[11],
                                           tex[12], tex[13]};

        // bloques de colision
        this.collisionLayer= collision;

        // bloques de puntajes
        this.pointsLayer =  pointsLayer;

        // inicializo el contador de puntaje
        this.score = new Score();

        // la direccion inicial es 0
        this.ultimaDireccion=0;

        // doy la rotacion inicial de 0 grados
        this.ultimaRotacion = 0f;

        // creo el bodyDef que internamente posee el Body
        BodyDef bodyDefPacman = new BodyDef();
        bodyDefPacman.position.set(position); // le asigno la posicion inicial
        bodyDefPacman.type = BodyDef.BodyType.DynamicBody; // establezco que es un cuero que se mueve

        // instancio el Body en el mundo
        this.bodyPacman = myWorld.createBody(bodyDefPacman);

        // le doy forma a la figura (como la posicion que se le da al box2d se centra en el 0,0),
        // le indicamos que tiene 0,5 de la longitud tanto de alto como de ancho
        PolygonShape shapePacman = new PolygonShape();
        shapePacman.setAsBox(0.2f,0.2f);

        // instancio la figura y le paso el poligono
        this.fixturePacman = bodyPacman.createFixture(shapePacman,3);

        // le asigno un identificador para las colisiones
        this.fixturePacman.setUserData("pacman");

        this.animacionMovimiento= new Animation(0.3f, texturePacman[0], texturePacman[1],
                                                texturePacman[2]);

        this.animacionMuerte= new Animation(0.2f,texturePacman[3], texturePacman[4],
                                            texturePacman[5], texturePacman[6], texturePacman[7],
                                            texturePacman[8], texturePacman[9], texturePacman[10],
                                            texturePacman[11],texturePacman[12],texturePacman[13]);

        this.time= 0f;

        // declaro el tamaño del actor medio metro
        setSize(PIXELS_IN_METER*0.45f ,PIXELS_IN_METER*0.45f);

/*
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                return true;
            }

        });
*/

        shapePacman.dispose();
    }

    // dibujamos el actor
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Texture frameActual = animarPacman(time);
        // aqui agrego la altura y ancho de pixeles dnd quier colocar las cosas
        // seria 320 - 22,5 de ancho y 180 - 22.5 de alto
//        this.setPosition((getStage().getWidth() /2) - getWidth()/2, (getStage().getHeight() /2) - getHeight()/2);

        this.setPosition(this.bodyPacman.getPosition().x * PIXELS_IN_METER,
                         this.bodyPacman.getPosition().y *PIXELS_IN_METER);
        /* Lo dibujo con la Textura, Posicion X e Y inicial que q le asignamos previamente
         (SetPosition)con el alto y el ancho que dimos en el metodo setSize*/
        TextureRegion textureRegion = new TextureRegion(frameActual);

        int direccionActual = direccion(); // 0 -1
        float rotacion = rotar(direccionActual); //0°  270°

        this.ultimaDireccion = direccionActual; // 0   -1

        if(rotacion!=0f){ //
            this.ultimaRotacion= rotacion; // 270°
        }
        batch.draw(textureRegion,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,ultimaRotacion);

    }

    private Texture animarPacman(float time) {
        Texture retorno;

        if(!this.alive){

            // recupero el tiempo para saber q frame mostrar
            this.time= time + 0.01f;
            retorno = (Texture) this.animacionMuerte.getKeyFrame(time,false);

        }else{
            if(this.bodyPacman.getLinearVelocity().x != 0 || this.bodyPacman.getLinearVelocity().y!=0){
                // recupero el tiempo para saber q frame mostrar
                this.time= time + Gdx.graphics.getDeltaTime();
                retorno = (Texture) this.animacionMovimiento.getKeyFrame(time,true);

            }else{
                retorno = this.texturePacman[0];
            }
        }
        // le doy origen al actor en el centro de masa
        this.setOrigin(getWidth()/2, getHeight()/2);

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
        Vector2 velocidad = bodyPacman.getLinearVelocity();

        // valor del puntaje del bloque
        int puntajeAux =0;
        int posicionx =0;
        int posiciony=0;

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
            puntajeAux= collisionedPoint(posicionx,posiciony,"valor");

            if(puntajeAux!=0){
                // aumento el puntaje
                this.score.addScore(puntajeAux);

                //puede ser que salte error cuando quiera comprobar si no esta celda tiene algo
                this.pointsLayer.setCell(posicionx,posiciony,null);
            }

            if(collisionX){
                setX(oldX);
                this.bodyPacman.setLinearVelocity(0,0);
            }
            if(collisionY){
                setX(oldY);
                this.bodyPacman.setLinearVelocity(0,0);
            }
        }
    }

    private boolean collisionedBlock( int posicionX,int posicionY, String key){

        return this.collisionLayer.getCell(posicionX,posicionY)
                                  .getTile().getProperties().containsKey(key);
    }

   // retorna 0 si es nulo o si no tiene valor y de lo contrario retorna el valor de ese punto con el que colisiono
    private int collisionedPoint( int posicionX,int posicionY, String key){

        Integer result= new Integer(0);
        TiledMapTileLayer.Cell cell = this.pointsLayer.getCell(posicionX,posicionY);

        // si no cayo en un lugar vacio
        if(cell != null){
            // recupero el valor de ese punto
            result = (Integer) cell.getTile().getProperties().get("valor");
            // si recupero algo es por que ese bloque tenia ese atributo
            if(cell.getTile().getProperties().containsKey("miedo")){
                this.setBonificado(true);
            }
                return result.intValue();
        }
        return result.intValue();
    }
    // para destruir los objetos de nuestro mundo
    public void detach(){

        /* se agregan en un metodo aparte por que se llama cuando la pantalla se esconde,
         y no se agrega en un dispose por que este se llama una unica vez,
         mientras si la pantalla es llamada 5 veces nuestra memoria de video quedara saturada*/
        bodyPacman.destroyFixture(fixturePacman);
        world.destroyBody(bodyPacman);
    }

    public void avanzar(int direccion){
        // agregar esta sentencia cuando se agrega el escenari o
        // agregar primero todos los puntos para comer y luego agregar el escenario encima de estos puntos
     if(isAlive()){

         switch (direccion){
             case(0):
                 this.bodyPacman.setLinearVelocity(-PACMAN_VELOCITY,0);
                 break;
             case(1):
                 this.bodyPacman.setLinearVelocity(0,PACMAN_VELOCITY);
                 break;
             case(2):
                 this.bodyPacman.setLinearVelocity(PACMAN_VELOCITY,0);
                 break;
             default:
                 this.bodyPacman.setLinearVelocity(0,-PACMAN_VELOCITY);
                 break;
         }
     }
    }
    // -2 direccion izquierda, 2 direccion derecha, -1 abajo, 1 arriba, 0 detenido
    private int direccion (){
        Vector2 movimiento = this.bodyPacman.getLinearVelocity();
        int direccion = 0;

        if(movimiento.x != 0 || movimiento.y!=0){
            if(movimiento.x >0){
                direccion=2;
            }else{
                if(movimiento.x<0){
                    direccion=-2;
                }
            }

            if(movimiento.y>0){
                direccion = 1;
            }else{
                if(movimiento.y<0){
                    direccion=-1;
                }
            }
        }
        return direccion;
    }

    private float rotar(int direccion){
    float grados =0 ;

        if( this.ultimaDireccion == 0 || this.ultimaDireccion == 2){
            switch (direccion){
                case (-2):
                    grados = 180f;
                    break;
                case (-1):
                    grados=270f;
                    break;
                case (1):
                    grados=90f;
                    break;
                case(0):
                    grados=this.ultimaRotacion;
                break;
                case(2):
                    grados=360f;
                    break;
            }
        }

        if(ultimaDireccion == -2){
            switch(direccion){
                case(1):
                grados = 90f;
                break;
                case(2):
                grados = 360f;
                break;
                case(-1):
                grados = 270f;
                break;
                case(-2):
                grados = 0f;
                break;
                case(0):
                    grados=this.ultimaRotacion;
                break;
            }
        }
        if(ultimaDireccion == 1){
            switch(direccion){
                case(1):
                    grados = 0f;
                    break;
                case(2):
                    grados = 360f;
                    break;
                case(-1):
                    grados = 270f;
                    break;
                case(-2):
                    grados = 180f;
                    break;
                case(0):
                    grados=this.ultimaRotacion;
                    break;
            }
        }
        if(ultimaDireccion == -1){
            switch(direccion){
                case(1):
                    grados = 90f;
                    break;
                case(2):
                    grados = 270f;
                    break;
                case(-1):
                    grados = 0f;
                    break;
                case(-2):
                    grados = 180f;
                    break;
                case(0):
                    grados=this.ultimaRotacion;
                break;
            }
        }
        return grados;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setBonificado(boolean bonificado) {
        this.bonificado = bonificado;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isBonificado() {
        return bonificado;
    }

    public Score getPuntaje(){
        return this.score;
    }

    public void dead(){
        this.alive= false;
        this.bodyPacman.setLinearVelocity(0,0);
        this.time=0f;
    }
}
