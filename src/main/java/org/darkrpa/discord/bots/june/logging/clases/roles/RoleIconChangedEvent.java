package org.darkrpa.discord.bots.june.logging.clases.roles;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.events.role.update.RoleUpdateIconEvent;

public class RoleIconChangedEvent extends GenericLoggingEvent{

    public RoleIconChangedEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        RoleUpdateIconEvent evento = (RoleUpdateIconEvent)super.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`RoleIconChanged`");
        embedCreator.addField("Descripción", "El rol "+evento.getRole().getAsMention()+" ha cambiado su icono");
        embedCreator.addField("Antiguo icono", evento.getOldIcon().getIconUrl());
        return embedCreator;
    }

}
