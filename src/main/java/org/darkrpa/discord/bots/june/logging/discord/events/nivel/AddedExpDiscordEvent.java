package org.darkrpa.discord.bots.june.logging.discord.events.nivel;

import org.darkrpa.discord.bots.june.logging.discord.events.GenericGuildDiscordEvent;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class AddedExpDiscordEvent extends GenericGuildDiscordEvent{

    private int cantidad;

    public AddedExpDiscordEvent(JDA api, long responseNumber, Guild guild, String admin, String usuarioAfectado,
        int cantidad) {
        super(api, responseNumber, guild, admin, usuarioAfectado, "");
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }



}
