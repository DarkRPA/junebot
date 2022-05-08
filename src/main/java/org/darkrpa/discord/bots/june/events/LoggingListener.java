package org.darkrpa.discord.bots.june.events;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.clases.RoleCreatedEvent;
import org.darkrpa.discord.bots.june.model.Logging;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.role.GenericRoleEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;

/**
 * Clase LoggingListener, esta clase va a ser la que se va a encargar de poder registrar todos los eventos
 * que podamos tener en algun momento, ya sean eventos de roles, eventos de cambios de usuarios
 * etc.
 * Para ello crearemos una serie de eventos custom que seran lanzados cuando algun comando se utilice
 *
 */
public class LoggingListener extends AbstractEventListener{

    public static final String[] EVENTOS_LOGGING = {"ROL", "ASIG_ROL", "CHAT", "NOMBRE_GUILD", "ICON_CHANGE", "KICK", "WARN", "MUTE", "TEMP_BAN", "SOFT_BAN", "PERM_BAN", "ADD_EXP", "REMOVE_EXP", "SET_WELCOME", "OPEN_TICKET"};
    private static final GenericLoggingEvent[][] CLASES_EVENTOS = {
        {new RoleCreatedEvent("Test", RoleCreateEvent.class)}
    };

    public LoggingListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent o) {
        //Primero debemos de saber si es un evento que podemos registrar
        if(o instanceof GenericGuildEvent || o instanceof GenericRoleEvent){

            Guild server = null;
            if(o instanceof GenericGuildEvent){
                server = ((GenericGuildEvent) o).getGuild();
            }else if(o instanceof GenericRoleEvent){
                server = ((GenericRoleEvent) o).getGuild();
            }
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
                //Ahora revisamos los permisos que tiene y que no tiene
                String binario = String.valueOf(logging.getBinary());
                //Debemos de recorrer el array de opciones y en caso de que sea 1 entonces registrar el evento
                for(int i = binario.length() - 1; i >= 0; i--){
                    //Tenemos que invertir el binario
                    String posicion = String.valueOf(binario.charAt(i));
                    boolean habilitado = posicion.equals("1");
                    if(habilitado){
                        GenericLoggingEvent[] clasesHabilitadas = LoggingListener.CLASES_EVENTOS[i];
                        for(int x = 0; x < clasesHabilitadas.length; x++){
                            GenericLoggingEvent eventoActual = clasesHabilitadas[x];
                            if(eventoActual.getClaseEscucha() == o.getClass()){
                                //Sabemos que es ese
                                eventoActual.setEventoGenerico(o);
                                canalEncontrado.sendMessageEmbeds(eventoActual.getMessage().build()).queue();
                            }
                        }
                    }
                }
            }
        }

    }

}
