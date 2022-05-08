package org.darkrpa.discord.bots.june.logging.clases;

import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.events.GenericEvent;

public abstract class GenericLoggingEvent {
    protected String textoEvento;
    protected Class claseEscucha;
    protected GenericEvent eventoGenerico;

    public GenericLoggingEvent(String textoEvento, Class claseEscucha){
        this.textoEvento = textoEvento;
        this.claseEscucha = claseEscucha;
    }

    public String getTextoEvento() {
        return textoEvento;
    }

    public void setTextoEvento(String textoEvento) {
        this.textoEvento = textoEvento;
    }

    public Class getClaseEscucha() {
        return claseEscucha;
    }

    public void setClaseEscucha(Class claseEscucha) {
        this.claseEscucha = claseEscucha;
    }



    public GenericEvent getEventoGenerico() {
        return eventoGenerico;
    }

    public void setEventoGenerico(GenericEvent eventoGenerico) {
        this.eventoGenerico = eventoGenerico;
    }

    //Este metodo sera el encargado de crear y enviar el mensaje que se mostrara en el chat especificado
    public abstract EmbedCreator getMessage();
}
