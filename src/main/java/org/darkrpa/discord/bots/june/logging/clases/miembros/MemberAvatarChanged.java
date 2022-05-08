package org.darkrpa.discord.bots.june.logging.clases.miembros;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateAvatarEvent;

public class MemberAvatarChanged extends GenericLoggingEvent{

    public MemberAvatarChanged(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        GuildMemberUpdateAvatarEvent event = (GuildMemberUpdateAvatarEvent)super.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`MemberAvatarChangedEvent`");
        embedCreator.addField("Descripción", "El usuario "+event.getUser().getAsMention()+" se ha cambiado de avatar");
        embedCreator.addField("Antiguo", event.getOldAvatarUrl(), true);
        embedCreator.addField("Nuevo", event.getNewAvatarUrl(),true);
        return embedCreator;
    }

}
