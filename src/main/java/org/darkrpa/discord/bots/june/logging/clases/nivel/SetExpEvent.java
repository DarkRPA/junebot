package org.darkrpa.discord.bots.june.logging.clases.nivel;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.nivel.SetExpDiscordEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Member;

public class SetExpEvent extends GenericLoggingEvent{
    public SetExpEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        SetExpDiscordEvent evento = (SetExpDiscordEvent)this.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`AddedExpEvent`");

        Member admin = evento.getGuild().getMemberById(evento.getAdmin());

        embedCreator.addField("Descripción", String.format("El usuario %s ha establecido la exp de %s a %d", admin.getAsMention(), evento.getUsuarioAfectado(), evento.getCantidad()));

        return embedCreator;
    }
}
