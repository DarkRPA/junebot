package org.darkrpa.discord.bots.june.logging.clases.moderacion;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.mute.MuteEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Member;

public class LoggingMutedEvent extends GenericLoggingEvent{

    public LoggingMutedEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        MuteEvent evento = (MuteEvent)this.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`MemberMutedEvent`");

        Member admin = evento.getGuild().getMemberById(evento.getAdmin());

        Instant vencimiento = Instant.ofEpochMilli(evento.getTiempo());
        ZonedDateTime formato = ZonedDateTime.ofInstant(vencimiento, ZoneId.of("UTC"));

        embedCreator.addField("Descripción", String.format("El usuario %s ha muteado al usuario %s", admin.getAsMention(), "<@"+evento.getUsuarioAfectado()+">"));
        embedCreator.addField("Razón", evento.getMotivo());
        embedCreator.addField("Vencimiento", "`"+formato.format(DateTimeFormatter.ofPattern("uuuu-MMM-dd HH:mm"))+" UTC`");

        return embedCreator;
    }

}
