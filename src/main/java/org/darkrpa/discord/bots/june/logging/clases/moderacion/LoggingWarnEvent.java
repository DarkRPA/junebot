package org.darkrpa.discord.bots.june.logging.clases.moderacion;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.warn.WarnEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Member;

public class LoggingWarnEvent extends GenericLoggingEvent{

    public LoggingWarnEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        WarnEvent evento = (WarnEvent)this.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`MemberWarnedEvent`");

        Member admin = evento.getGuild().getMemberById(evento.getAdmin());

        embedCreator.addField("Descripción", String.format("El usuario %s ha advertido al usuario %s", admin.getAsMention(), "<@"+evento.getUsuarioAfectado()+">"));
        embedCreator.addField("Razón", evento.getMotivo());

        return embedCreator;
    }

}
