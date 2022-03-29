package org.darkrpa.discord.bots.june.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.EventListener;

/**
 * Clase abstracta que implementará una serie de metodos los cuales permitira que se encargue de uno o varios eventos
 * dependiendo de para que haya sido programado
 *
 * @author Daniel Caparros Duran
 * @since 1.0
 * @version 1.0
 */
public abstract class AbstractEventListener implements EventListener{
    protected JDA bot;

    public AbstractEventListener(JDA bot){
        this.bot = bot;
    }
}
