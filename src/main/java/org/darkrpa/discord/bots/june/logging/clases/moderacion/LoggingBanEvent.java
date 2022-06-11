package org.darkrpa.discord.bots.june.logging.clases.moderacion;

import java.awt.Color;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.ban.BanEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Member;

public class LoggingBanEvent extends GenericLoggingEvent{

    public LoggingBanEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        BanEvent evento = (BanEvent)this.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`MemberBannedEvent`");

        Instant vencimiento = Instant.ofEpochMilli(evento.getTiempo());
        ZonedDateTime formato = ZonedDateTime.ofInstant(vencimiento, ZoneId.of("UTC"));

        Member admin = evento.getGuild().getMemberById(evento.getAdmin());

        embedCreator.addField("Descripción", String.format("El usuario %s ha baneado al usuario %s", admin.getAsMention(), "<@"+evento.getUsuarioAfectado()+">"));
        embedCreator.addField("Razón", evento.getMotivo());
        embedCreator.addField("Vencimiento", "`"+formato.format(DateTimeFormatter.ofPattern("uuuu-MMM-dd HH:mm"))+" UTC`");
        embedCreator.setColor(Color.red);

        return embedCreator;
    }

}
