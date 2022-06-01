package org.darkrpa.discord.bots.june.logging.clases.moderacion;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.kick.KickEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Member;

public class LoggingKickEvent extends GenericLoggingEvent{

    public LoggingKickEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        KickEvent evento = (KickEvent)this.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`MemberKickedEvent`");

        Member admin = evento.getGuild().getMemberById(evento.getAdmin());

        embedCreator.addField("Descripción", String.format("El usuario %s ha kickeado al usuario %s", admin.getAsMention(), "<@"+evento.getUsuarioAfectado()+">"));
        embedCreator.addField("Razón", evento.getMotivo());

        return embedCreator;
    }

}
