package org.darkrpa.discord.bots.june.logging.clases.roles;

import java.awt.Color;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.events.role.RoleDeleteEvent;

public class RoleDeletedEvent extends GenericLoggingEvent{

    public RoleDeletedEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        RoleDeleteEvent event = (RoleDeleteEvent)super.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`RoleDeletedEvent`");
        embedCreator.setColor(Color.red);
        embedCreator.addField("Descripción", "El rol "+event.getRole().getName()+" ha sido eliminado");
        return embedCreator;
    }

}
