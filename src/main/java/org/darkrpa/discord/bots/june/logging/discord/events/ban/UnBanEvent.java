package org.darkrpa.discord.bots.june.logging.discord.events.ban;

import org.darkrpa.discord.bots.june.logging.discord.events.GenericGuildDiscordEvent;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class UnBanEvent extends GenericGuildDiscordEvent{

    public UnBanEvent(JDA api, long responseNumber, Guild guild, String admin, String usuarioAfectado, String motivo) {
        super(api, responseNumber, guild, admin, usuarioAfectado, motivo);
    }

}
