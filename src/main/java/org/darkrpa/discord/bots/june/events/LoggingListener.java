package org.darkrpa.discord.bots.june.events;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.clases.guild.GuildBannerChangeEvent;
import org.darkrpa.discord.bots.june.logging.clases.guild.GuildNameChangedEvent;
import org.darkrpa.discord.bots.june.logging.clases.miembros.MemberAvatarChanged;
import org.darkrpa.discord.bots.june.logging.clases.miembros.MemberNameChanged;
import org.darkrpa.discord.bots.june.logging.clases.miembros.MemberRoleAdded;
import org.darkrpa.discord.bots.june.logging.clases.miembros.MemberRoleRemoved;
import org.darkrpa.discord.bots.june.logging.clases.moderacion.LoggingBanEvent;
import org.darkrpa.discord.bots.june.logging.clases.moderacion.LoggingKickEvent;
import org.darkrpa.discord.bots.june.logging.clases.moderacion.LoggingMutedEvent;
import org.darkrpa.discord.bots.june.logging.clases.moderacion.LoggingPermBanEvent;
import org.darkrpa.discord.bots.june.logging.clases.moderacion.LoggingUnBanEvent;
import org.darkrpa.discord.bots.june.logging.clases.moderacion.LoggingUnMuteEvent;
import org.darkrpa.discord.bots.june.logging.clases.moderacion.LoggingWarnEvent;
import org.darkrpa.discord.bots.june.logging.clases.roles.RoleCreatedEvent;
import org.darkrpa.discord.bots.june.logging.clases.roles.RoleDeletedEvent;
import org.darkrpa.discord.bots.june.logging.clases.roles.RoleNameChangedEvent;
import org.darkrpa.discord.bots.june.logging.clases.roles.RolePermissionUpdateEvent;
import org.darkrpa.discord.bots.june.logging.clases.tickets.CloseTicketEvent;
import org.darkrpa.discord.bots.june.logging.clases.tickets.OpenTicketEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.ban.BanEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.ban.PermBanEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.ban.UnBanEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.kick.KickEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.mute.MuteEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.mute.UnmuteEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.tickets.CloseTicketDiscordEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.tickets.GenericTicketGuildEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.tickets.OpenTicketDiscordEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.warn.WarnEvent;
import org.darkrpa.discord.bots.june.model.Logging;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateAvatarEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBannerEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;
import net.dv8tion.jda.api.events.role.GenericRoleEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePermissionsEvent;

/**
 * Clase LoggingListener, esta clase va a ser la que se va a encargar de poder registrar todos los eventos
 * que podamos tener en algun momento, ya sean eventos de roles, eventos de cambios de usuarios
 * etc.
 * Para ello crearemos una serie de eventos custom que seran lanzados cuando algun comando se utilice
 *
 */
public class LoggingListener extends AbstractEventListener{

    public static final String[] EVENTOS_LOGGING = {"ROL", "ASIG_ROL", "NOMBRE_GUILD", "ICON_CHANGE", "KICK", "WARN", "MUTE", "TEMP_BAN", "SOFT_BAN", "PERM_BAN", "ADD_EXP", "REMOVE_EXP", "SET_WELCOME", "OPEN_TICKET", "USERNAME_CHANGE", "USERNAME_ICON_CHANGE"};
    private static final GenericLoggingEvent[][] CLASES_EVENTOS = {
        {
            new RoleCreatedEvent(RoleCreateEvent.class),
            new RoleNameChangedEvent(RoleUpdateNameEvent.class),
            new RolePermissionUpdateEvent(RoleUpdatePermissionsEvent.class),
            new RoleDeletedEvent(RoleDeleteEvent.class)
        },{
            new MemberRoleAdded(GuildMemberRoleAddEvent.class),
            new MemberRoleRemoved(GuildMemberRoleRemoveEvent.class)
        },{
            new GuildNameChangedEvent(GuildUpdateNameEvent.class)
        },{
            new GuildBannerChangeEvent(GuildUpdateBannerEvent.class)
        },{
            new LoggingKickEvent(KickEvent.class)
        },{
            new LoggingWarnEvent(WarnEvent.class)
        },{
            new LoggingMutedEvent(MuteEvent.class),
            new LoggingUnMuteEvent(UnmuteEvent.class)
        },{
            new LoggingBanEvent(BanEvent.class),
            new LoggingUnBanEvent(UnBanEvent.class),
        },{

        },{
            new LoggingPermBanEvent(PermBanEvent.class),
        },{

        },{

        },{

        },{
            new OpenTicketEvent(OpenTicketDiscordEvent.class),
            new CloseTicketEvent(CloseTicketDiscordEvent.class)
        },{
            new MemberNameChanged(GuildMemberUpdateNicknameEvent.class)
        },{
            new MemberAvatarChanged(GuildMemberUpdateAvatarEvent.class)
        }
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
                    int indexReal = (binario.length()-1)-i;
                    String posicion = String.valueOf(binario.charAt(indexReal));
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
