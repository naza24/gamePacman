package com.pacman;

public class Constants {
    /*Box2D y Scene2d trabajan en dimensiones distintas, box2d trabaja en  metros y scene2d en pixels
     * por lo que para poder dibujar un box2d de un personaje en la pantalla debemo hacer una conversion
     * por ejemplo 90f (pixels) --> equivalen a 1 metro*/

    public static final float PIXELS_IN_METER = 45f;

    public static final float PACMAN_VELOCITY =2f;

}
