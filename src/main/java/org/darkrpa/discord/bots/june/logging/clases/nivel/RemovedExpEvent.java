package org.darkrpa.discord.bots.june.logging.clases.nivel;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.nivel.RemovedExpDiscordEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Member;

public class RemovedExpEvent extends GenericLoggingEvent{

    public RemovedExpEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        RemovedExpDiscordEvent evento = (RemovedExpDiscordEvent)this.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`AddedExpEvent`");

        Member admin = evento.getGuild().getMemberById(evento.getAdmin());

        embedCreator.addField("Descripción", String.format("El usuario %s ha quitado %d puntos de exp al usuario %s", admin.getAsMention(), evento.getCantidad(), evento.getUsuarioAfectado()));

        return embedCreator;
    }

}
