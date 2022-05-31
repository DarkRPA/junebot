package org.darkrpa.discord.bots.june.comandos.moderacion;

import java.time.Instant;
import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.comandos.Comando;
import org.darkrpa.discord.bots.june.logging.discord.events.kick.KickEvent;
import org.darkrpa.discord.bots.june.model.sanciones.Sancion;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;
import org.slf4j.event.LoggingEvent;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

public class Kick extends Comando{

    public Kick(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        //El comando Kick de lo que se va a encargar es de kickear al usuario especificado de la guild y registrarlo
        //como una nueva accion de moderación en la base de datos
        if(o instanceof MessageReceivedEvent){
            EmbedCreator creator = EmbedCreator.generateDefaultTemplate();
            MessageReceivedEvent evento = (MessageReceivedEvent)o;
            try{
                Guild guild = evento.getGuild();
                Member miembroMencionado = evento.getMessage().getMentionedMembers().get(0);

                //Debemos de comprobar que el rol que nosotros tenemos es más alto que el que vamos a kickear

                Sancion sancion = new Sancion(guild.getId(), miembroMencionado.getUser().getId());

                Instant momento = Instant.now();
                sancion.setFechaEvento(momento.toEpochMilli());
                sancion.setIdUsuarioAdmin(evento.getMember().getUser().getId());

                if(this.matcher.group(5) == null){
                    creator.title("Motivo no especificado").description("No ha especificado un motivo en el Kick");
                    evento.getMessage().replyEmbeds(creator.build()).queue();
                    return;
                }

                String motivo = this.matcher.group(5).trim();
                sancion.setMotivo(motivo);
                sancion.setIdEvento(Sancion.KICK);

                miembroMencionado.kick(motivo).queue();
                //Vamos a lanzar nosotros tambien el evento para que el sistema de logging se percate de el
                KickEvent eventoKick = new KickEvent(Main.getBot(), 200, guild, evento.getMember().getId(), miembroMencionado.getUser().getId(), motivo);
                Main.getLoggingListener().onEvent(eventoKick);

                creator.title("Usuario kickeado").description("El usuario "+miembroMencionado.getAsMention()+" ha sido kickeado");
                evento.getMessage().replyEmbeds(creator.build()).queue();

                sancion.actualizar();
            }catch(HierarchyException exception){
                creator.title("Error al expulsar al usuario").description("¡No puedo expulsar a un miembro con roles superiores al mio!");
                evento.getMessage().replyEmbeds(creator.build()).queue();
            }
        }
    }
}
