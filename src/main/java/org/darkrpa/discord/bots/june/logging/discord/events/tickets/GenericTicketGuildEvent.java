package org.darkrpa.discord.bots.june.logging.discord.events.tickets;

import org.darkrpa.discord.bots.june.logging.discord.events.GenericGuildDiscordEvent;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class GenericTicketGuildEvent extends GenericGuildDiscordEvent{

    private String canal;

    public GenericTicketGuildEvent(JDA api, long responseNumber, Guild guild, String admin, String usuarioAfectado,
            String motivo) {
        super(api, responseNumber, guild, admin, usuarioAfectado, motivo);
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }



}
