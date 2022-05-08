package org.darkrpa.discord.bots.june.logging.clases.miembros;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;

public class MemberRoleRemoved extends GenericLoggingEvent{

    public MemberRoleRemoved(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        GuildMemberRoleRemoveEvent event = (GuildMemberRoleRemoveEvent)super.eventoGenerico;
        EmbedCreator creator = EmbedCreator.generateLogTemplate();
        creator.addField("Tipo", "`MemberRoleRemovedEvent`");
        String roles = "";
        for(Role rol : event.getRoles()){
            roles += rol.getAsMention();
        }
        creator.addField("Descripción", "Se han eliminado los roles "+roles+" a "+event.getMember().getAsMention());
        return creator;
    }

}
