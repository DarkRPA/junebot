package org.darkrpa.discord.bots.june.logging.clases.moderacion;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.ban.UnBanEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Member;

public class LoggingUnBanEvent extends GenericLoggingEvent{

    public LoggingUnBanEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        UnBanEvent evento = (UnBanEvent)this.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`UnBanMemberEvent`");

        Member admin = evento.getGuild().getMemberById(evento.getAdmin());

        embedCreator.addField("Descripción", String.format("El usuario %s ha desbaneado al usuario %s", admin.getAsMention(), "<@"+evento.getUsuarioAfectado()+">"));

        return embedCreator;
    }

}
