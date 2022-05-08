package org.darkrpa.discord.bots.june.logging.clases.roles;


import java.awt.Color;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.events.role.RoleCreateEvent;

public class RoleCreatedEvent extends GenericLoggingEvent {


    public RoleCreatedEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        RoleCreateEvent evento = (RoleCreateEvent) super.eventoGenerico;
        EmbedCreator plantilla = EmbedCreator.generateLogTemplate();
        plantilla.addField("Tipo", "`RolCreatedEvent`");
        plantilla.setColor(Color.green);
        plantilla.addField("Descripción", "Se ha creado el rol "+evento.getRole().getAsMention());
        //Tenemos el evento
        return plantilla;
    }

}
