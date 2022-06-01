package org.darkrpa.discord.bots.june.comandos.moderacion;

import java.util.List;
import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.logging.discord.events.mute.UnmuteEvent;
import org.darkrpa.discord.bots.june.model.Servidor;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class UnMute extends GenericModerationCommand{

    public UnMute(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        if(o instanceof MessageReceivedEvent){
            MessageReceivedEvent evento = (MessageReceivedEvent)o;
            if(evento.getChannelType() == ChannelType.TEXT){
                Message mensaje = evento.getMessage();
                Guild guild = evento.getGuild();
                List<Member> miembrosMencionados = mensaje.getMentionedMembers();

                EmbedCreator creator = EmbedCreator.generateDefaultTemplate();

                if(miembrosMencionados.size() == 0){
                    creator.description("No ha mencionado a ningún usuario para desmutear");
                    mensaje.replyEmbeds(creator.build()).queue();
                    return;
                }

                Member miembro = miembrosMencionados.get(0);

                Servidor server = new Servidor(guild.getId());
                Role rol = guild.getRoleById(server.getRolMute());

                guild.removeRoleFromMember(miembro, rol).queue();

                UnmuteEvent eventoUnmute = new UnmuteEvent(Main.getBot(), 200, guild, mensaje.getAuthor().getId(), miembro.getId(), "Desmuteado");
                Main.getLoggingListener().onEvent(eventoUnmute);
                creator.description("Se han descilenciado a "+miembrosMencionados.size()+" miembros");
                mensaje.replyEmbeds(creator.build());
            }
        }
    }

}
