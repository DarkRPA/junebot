package org.darkrpa.discord.bots.june.comandos.ticket;

import java.util.List;
import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.comandos.Comando;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AddMemberTicket extends Comando{

    public AddMemberTicket(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        if(o instanceof MessageReceivedEvent){
            MessageReceivedEvent evento = (MessageReceivedEvent)o;
            //tenemos ya el evento, ahora debemos de saber si el canal es un canal valido
            Guild servidor = evento.getGuild();
            Member miembro = evento.getMember();
            TextChannel canal = evento.getTextChannel();

            String regexCanal = "\\w+-(\\d+)";

            if(!canal.getName().matches(regexCanal)){
                return;
            }

            List<Member> listaMencionados = evento.getMessage().getMentionedMembers();

            for(Member miembroMencionado : listaMencionados){
                //Tenemos los miembros mencionados por lo que le damos permiso para escribir
                canal.getPermissionContainer().createPermissionOverride(miembroMencionado).grant(Permission.VIEW_CHANNEL,Permission.MESSAGE_SEND).queue(e->{
                    miembroMencionado.getUser().openPrivateChannel().queue(canalPrivado -> {
                        EmbedCreator creator = EmbedCreator.generateDefaultTemplate();
                        creator.title("Invitación");
                        creator.description(String.format("Has sido invitado al ticket %s en el discord %s por %s. Por favor acceda lo antes posible", canal.getAsMention(), servidor.getName(), miembro.getEffectiveName()));
                        creator.thumbnail(servidor.getIconUrl());
                        canalPrivado.sendMessageEmbeds(creator.build()).queue();
                    });
                });
            }
        }
    }

}
