package org.darkrpa.discord.bots.june.logging.clases.roles;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;

public class RoleNameChangedEvent extends GenericLoggingEvent{

    public RoleNameChangedEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        //Se ha cambiado el nombre del rol
        RoleUpdateNameEvent evento = (RoleUpdateNameEvent)super.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`RoleNameChanged`");
        embedCreator.addField("Descripción", "El rol "+evento.getRole().getAsMention()+" ha cambiado de nombre");
        embedCreator.addField("Antiguo nombre", "`"+evento.getOldName()+"`");
        return embedCreator;
    }

}
