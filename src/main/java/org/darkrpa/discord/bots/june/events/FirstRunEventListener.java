package org.darkrpa.discord.bots.june.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;

public class FirstRunEventListener extends AbstractEventListener{

    public FirstRunEventListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent event) {
        //tenemos el evento, ahora vamos a verificar que el evento es el de entrada por primera vez al servidor
        if(event instanceof GuildJoinEvent){
            //tenemos el server
            GuildJoinEvent evento = (GuildJoinEvent) event;
            Guild server = evento.getGuild();


        }
    }

}