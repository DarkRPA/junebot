package org.darkrpa.discord.bots.june.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CampoSetter {
    public String nombreColumna();
    public boolean isAutoIncremental() default false;
}
