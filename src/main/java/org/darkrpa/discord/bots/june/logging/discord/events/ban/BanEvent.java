package org.darkrpa.discord.bots.june.logging.discord.events.ban;

import org.darkrpa.discord.bots.june.logging.discord.events.GenericGuildDiscordEvent;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class BanEvent extends GenericGuildDiscordEvent{

    private long tiempo;

    public BanEvent(JDA api, long responseNumber, Guild guild, String admin, String usuarioAfectado, String motivo, long tiempo) {
        super(api, responseNumber, guild, admin, usuarioAfectado, motivo);
        this.tiempo = tiempo;
    }

    public long getTiempo(){
        return this.tiempo;
    }

}
