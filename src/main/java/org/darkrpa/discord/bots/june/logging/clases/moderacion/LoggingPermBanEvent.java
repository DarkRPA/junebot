package org.darkrpa.discord.bots.june.logging.clases.moderacion;

import java.awt.Color;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.ban.PermBanEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Member;

public class LoggingPermBanEvent extends GenericLoggingEvent{

    public LoggingPermBanEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        PermBanEvent evento = (PermBanEvent)this.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`MemberPermBannedEvent`");

        Member admin = evento.getGuild().getMemberById(evento.getAdmin());

        embedCreator.addField("Descripción", String.format("El usuario %s ha perm baneado al usuario %s", admin.getAsMention(), "<@"+evento.getUsuarioAfectado()+">"));
        embedCreator.addField("Razón", evento.getMotivo());
        embedCreator.setColor(Color.red);

        return embedCreator;
    }

}
