package org.darkrpa.discord.bots.june.logging.clases.nivel;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.nivel.AddedExpDiscordEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Member;

public class AddedExpEvent extends GenericLoggingEvent{

    public AddedExpEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        AddedExpDiscordEvent evento = (AddedExpDiscordEvent)this.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`AddedExpEvent`");

        Member admin = evento.getGuild().getMemberById(evento.getAdmin());

        embedCreator.addField("Descripción", String.format("El usuario %s ha dado %d exp al usuario %s", admin.getAsMention(), evento.getCantidad(), evento.getUsuarioAfectado()));

        return embedCreator;
    }

}
