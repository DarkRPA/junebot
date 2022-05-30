package org.darkrpa.discord.bots.june.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion encargada de poder obtener la informacion de un campo y saber si es una clave primaria
 * o no
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CampoGetter {
    public String nombreColumna();
    public boolean isPrimary() default false;
    public boolean isAutoIncremental() default false;
}
