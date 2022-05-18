package org.darkrpa.discord.bots.june.comandos.ticket;

import java.util.List;
import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.comandos.Comando;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class KickUserTicket extends Comando{

    public KickUserTicket(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        if(o instanceof MessageReceivedEvent){
            MessageReceivedEvent evento = (MessageReceivedEvent)o;
            //tenemos ya el evento, ahora debemos de saber si el canal es un canal valido
            TextChannel canal = evento.getTextChannel();

            String regexCanal = "\\w+-(\\d+)";

            if(!canal.getName().matches(regexCanal)){
                return;
            }

            List<Member> listaMencionados = evento.getMessage().getMentionedMembers();

            for(Member miembroMencionado : listaMencionados){
                //Tenemos los miembros mencionados por lo que le damos permiso para escribir
                List<PermissionOverride> miembros = canal.getMemberPermissionOverrides();
                for(PermissionOverride permisoMiembro : miembros){
                    if(permisoMiembro.getId().equals(miembroMencionado.getId())){
                        permisoMiembro.delete().queue();
                    }
                }
            }
        }

    }

}
