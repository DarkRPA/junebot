package org.darkrpa.discord.bots.june.anotaciones;

/**
 * Anotacion encargada de poder obtener la informacion de un campo y saber si es una clave primaria
 * o no
 */
public @interface CampoGetter {
    public String nombreColumna();
    public boolean isPrimary() default false;
}
