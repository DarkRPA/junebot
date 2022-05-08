package org.darkrpa.discord.bots.june.logging.clases.miembros;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;

public class MemberRoleAdded extends GenericLoggingEvent{

    public MemberRoleAdded(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        GuildMemberRoleAddEvent evento = (GuildMemberRoleAddEvent)super.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`MemberRoleAddedEvent`");
        String roles = "";
        for(Role rol : evento.getRoles()){
            roles += rol.getAsMention();
        }
        embedCreator.addField("Descripción", "Se le han asignado el/los rol/es "+roles+" a "+evento.getMember().getAsMention());
        return embedCreator;
    }

}
