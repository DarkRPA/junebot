package org.darkrpa.discord.bots.june.logging.clases.moderacion;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.mute.UnmuteEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Member;

public class LoggingUnMuteEvent extends GenericLoggingEvent{

    public LoggingUnMuteEvent(Class claseEscucha) {
        super(claseEscucha);

    }

    @Override
    public EmbedCreator getMessage() {
        UnmuteEvent evento = (UnmuteEvent)this.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`UnMutedMemberEvent`");

        Member admin = evento.getGuild().getMemberById(evento.getAdmin());

        embedCreator.addField("Descripción", String.format("El usuario %s ha desmuteado al usuario %s", admin.getAsMention(), "<@"+evento.getUsuarioAfectado()+">"));

        return embedCreator;
    }

}
