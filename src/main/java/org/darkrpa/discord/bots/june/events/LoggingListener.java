package org.darkrpa.discord.bots.june.events;

import org.darkrpa.discord.bots.june.model.Logging;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;

/**
 * Clase LoggingListener, esta clase va a ser la que se va a encargar de poder registrar todos los eventos
 * que podamos tener en algun momento, ya sean eventos de roles, eventos de cambios de usuarios
 * etc.
 * Para ello crearemos una serie de eventos custom que seran lanzados cuando algun comando se utilice
 *
 */
public class LoggingListener extends AbstractEventListener{

    public LoggingListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent o) {
        //Primero debemos de saber si es un evento que podemos registrar
        if(o instanceof GenericGuildEvent){
            GenericGuildEvent eventoDiscord = (GenericGuildEvent) o;
            Guild server = eventoDiscord.getGuild();
            String idServer = server.getId();
            Logging logging = new Logging(idServer);
            if(logging.getHabilitado() == 1){
                //Sabemos que esta habilitado el logging
                String idCanalLogging = logging.getCanalLogin();
                MessageChannel canalEncontrado = server.getTextChannelById(idCanalLogging);
                if(canalEncontrado == null){
                    return;
                }

                //Tenemos el canal, podemos seguir


            }
        }

    }

}
