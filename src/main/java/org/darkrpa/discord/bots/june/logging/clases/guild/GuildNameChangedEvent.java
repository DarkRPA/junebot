package org.darkrpa.discord.bots.june.logging.clases.guild;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;

public class GuildNameChangedEvent extends GenericLoggingEvent{

    public GuildNameChangedEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        GuildUpdateNameEvent event = (GuildUpdateNameEvent)super.eventoGenerico;
        EmbedCreator creator = EmbedCreator.generateLogTemplate();
        creator.addField("Tipo", "`GuildNameChangeEvent`");
        creator.addField("Descripción", "El nombre del servidor ha cambiado");
        creator.addField("Antiguo", event.getOldName(),true);
        creator.addField("Nuevo", event.getNewName(),true);
        return creator;
    }

}
