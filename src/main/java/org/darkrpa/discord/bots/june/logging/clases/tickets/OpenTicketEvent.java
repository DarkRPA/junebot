package org.darkrpa.discord.bots.june.logging.clases.tickets;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.tickets.GenericTicketGuildEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

public class OpenTicketEvent extends GenericLoggingEvent {

    public OpenTicketEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {

        GenericTicketGuildEvent evento = (GenericTicketGuildEvent) this.eventoGenerico;

        EmbedCreator creator = EmbedCreator.generateLogTemplate();
        creator.addField("Tipo", "`OpenTicketEvent`");
        creator.description("Se ha abierto un nuevo ticket");
        creator.addField("Motivo", evento.getMotivo());
        creator.addField("Usuario emisor", evento.getUsuarioAfectado());

        return creator;

    }

}
