package org.darkrpa.discord.bots.june.model;

/**
 * La interfaz AccionEjecutable es una interfaz que se encarga de definir los metodos que
 * deben de incorporar aquellas clases que vayan a poder ejecutar un codigo especifico tras un tiempo
 * asignado de forma predeterminada
 *
 */
public interface AccionEjecutable {
    void ejecutar();
    boolean isEjecutado();
    void setEjecutado(boolean ejecutado);
}
