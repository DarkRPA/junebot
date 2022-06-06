package org.darkrpa.discord.bots.june.logging.clases.tickets;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.tickets.GenericTicketGuildEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

public class CloseTicketEvent extends GenericLoggingEvent{

    public CloseTicketEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        GenericTicketGuildEvent evento = (GenericTicketGuildEvent) this.eventoGenerico;

        EmbedCreator creator = EmbedCreator.generateLogTemplate();
        creator.addField("Tipo", "`CloseTicketEvent`");
        creator.description("Se ha cerrado un ticket ticket");
        creator.addField("Canal", evento.getCanal());
        creator.addField("Motivo", evento.getMotivo());
        creator.addField("Usuario emisor", evento.getUsuarioAfectado());

        return creator;
    }

}
