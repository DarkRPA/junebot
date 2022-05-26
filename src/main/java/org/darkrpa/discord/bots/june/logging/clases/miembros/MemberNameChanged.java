package org.darkrpa.discord.bots.june.logging.clases.miembros;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;

public class MemberNameChanged extends GenericLoggingEvent{

    public MemberNameChanged(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        GuildMemberUpdateNicknameEvent event = (GuildMemberUpdateNicknameEvent)super.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`MemberChangedNameEvent`");
        embedCreator.addField("Descripción", "El usuario "+event.getMember().getAsMention()+" se ha cambiado de apodo");
        embedCreator.addField("Nuevo", (event.getNewNickname() == null)?event.getUser().getName():event.getNewNickname(), true);
        embedCreator.addField("Antiguo", (event.getOldNickname() == null)?event.getUser().getName():event.getOldNickname(), true);
        embedCreator.thumbnail(event.getUser().getAvatarUrl());
        return embedCreator;
    }

}
