package org.darkrpa.discord.bots.june.events;

import org.darkrpa.discord.bots.june.model.Servidor;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.managers.channel.concrete.TextChannelManager;

public class NewTextChannelListener extends AbstractEventListener{

    public NewTextChannelListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent o) {
        if(o instanceof ChannelCreateEvent){
            ChannelCreateEvent evento = (ChannelCreateEvent)o;
            if(evento.getChannelType() == ChannelType.TEXT){
                //Es un canal de texto, podemos ponerle el rol
                Guild guild = evento.getGuild();
                Servidor server = new Servidor(evento.getGuild().getId());
                Role rolEncontrado = guild.getRoleById(server.getRolMute());
                if(rolEncontrado != null){
                    TextChannel canalTexto = (TextChannel)evento.getChannel();
                    TextChannelManager manager = canalTexto.getManager();
                    manager.putPermissionOverride(rolEncontrado, null, FirstRunEventListener.PERMISOS).queue();
                }
            }
        }

    }

}
