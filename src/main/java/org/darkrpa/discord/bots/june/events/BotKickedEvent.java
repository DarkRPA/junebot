package org.darkrpa.discord.bots.june.events;

import org.darkrpa.discord.bots.june.model.Servidor;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;

public class BotKickedEvent extends AbstractEventListener{

    public BotKickedEvent(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent o) {
        if(o instanceof GuildLeaveEvent){
            //El bot ha sido sacado
            GuildLeaveEvent evento = (GuildLeaveEvent)o;
            //Debemos de eliminar todo
            Servidor server = new Servidor(evento.getGuild().getId());
            Guild guild = evento.getGuild();

            server.eliminar();
        }

    }

}
