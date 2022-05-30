package org.darkrpa.discord.bots.june.logging.discord.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;

public class GenericGuildDiscordEvent extends GenericGuildEvent{

    private String admin;
    private String motivo;
    private String usuarioAfectado;

    public GenericGuildDiscordEvent(JDA api, long responseNumber, Guild guild, String admin, String usuarioAfectado, String motivo) {
        super(api, responseNumber, guild);
        this.admin = admin;
        this.usuarioAfectado = usuarioAfectado;
        this.motivo = motivo;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getUsuarioAfectado() {
        return usuarioAfectado;
    }

    public void setUsuarioAfectado(String usuarioAfectado) {
        this.usuarioAfectado = usuarioAfectado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
