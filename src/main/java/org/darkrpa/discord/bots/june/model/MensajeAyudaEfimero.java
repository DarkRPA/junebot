package org.darkrpa.discord.bots.june.model;

import java.util.ArrayList;

import net.dv8tion.jda.api.entities.Message;

/**
 * Esta clase es la que representara a los mensaje del comando ayuda,
 * de esta forma podemos tener de forma independiente el codigo de la ayuda de otros comandos
 */
public class MensajeAyudaEfimero extends Mensaje implements AccionEjecutable{
    private boolean isEjecutado;

    public MensajeAyudaEfimero(Message mensaje) {
        super(mensaje);
    }

    @Override
    public void ejecutar() {
        //Aqui debemos de poner la logica de que hacer cuando el timer llegue a 0, en este caso
        super.mensaje.editMessageComponents(new ArrayList<>()).queue();
    }

    @Override
    public boolean isEjecutado() {
        return this.isEjecutado;
    }

    @Override
    public void setEjecutado(boolean isEjecutado) {
        this.isEjecutado = isEjecutado;
    }

}
