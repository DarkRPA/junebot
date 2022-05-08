package org.darkrpa.discord.bots.june.logging.clases.guild;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.events.guild.update.GuildUpdateBannerEvent;

public class GuildBannerChangeEvent extends GenericLoggingEvent{

    public GuildBannerChangeEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        GuildUpdateBannerEvent event = (GuildUpdateBannerEvent)super.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`GuildBannerChangeEvent`");
        embedCreator.addField("Descripción", "El banner del servidor ha sido cambiado");
        embedCreator.addField("Antiguo", event.getOldBannerUrl(), true);
        embedCreator.addField("Nuevo", event.getNewBannerUrl(), true);
        embedCreator.thumbnail(event.getNewBannerUrl());
        return embedCreator;
    }

}
