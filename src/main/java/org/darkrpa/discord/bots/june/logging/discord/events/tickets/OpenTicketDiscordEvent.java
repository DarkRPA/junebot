package org.darkrpa.discord.bots.june.logging.discord.events.tickets;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class OpenTicketDiscordEvent extends GenericTicketGuildEvent{

    public OpenTicketDiscordEvent(JDA api, long responseNumber, Guild guild, String admin, String usuarioAfectado,
            String motivo) {
        super(api, responseNumber, guild, admin, usuarioAfectado, motivo);
    }

}
