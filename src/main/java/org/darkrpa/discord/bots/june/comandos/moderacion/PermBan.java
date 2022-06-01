package org.darkrpa.discord.bots.june.comandos.moderacion;

import java.time.Instant;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.logging.discord.events.ban.BanEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.ban.PermBanEvent;
import org.darkrpa.discord.bots.june.model.Servidor;
import org.darkrpa.discord.bots.june.model.sanciones.Sancion;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PermBan extends GenericModerationCommand{

    public PermBan(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        if(o instanceof MessageReceivedEvent){
            MessageReceivedEvent evento = (MessageReceivedEvent)o;

            if(evento.getChannelType() == ChannelType.TEXT){
                Guild guild = evento.getGuild();
                Servidor server = new Servidor(guild.getId());
                Message mensaje = evento.getMessage();
                //Vamos a permitir la expulsion masiva
                List<Member> miembrosMencionados = mensaje.getMentionedMembers();

                EmbedCreator creator = EmbedCreator.generateDefaultTemplate();

                if(miembrosMencionados.isEmpty()){
                    //Es vacio
                    creator.description("No se han mencionado miembros para banear");
                    mensaje.replyEmbeds(creator.build()).queue();
                    return;
                }

                if(this.matcher.group(5) == null){
                    creator.description("No se ha especificado el motivo");
                    mensaje.replyEmbeds(creator.build()).queue();
                    return;
                }

                String duracion = "50A";
                String motivo = this.matcher.group(5).trim();

                Pattern patronDuracion = Pattern.compile("(\\d+)([mdsMA])");
                Matcher matcherDuracion = patronDuracion.matcher(duracion);

                if(!matcherDuracion.matches()){
                    creator.description("La duración no esta bien formateada");
                    mensaje.replyEmbeds(creator.build()).queue();
                    return;
                }

                //Tenemos los grupos si o si

                String parteDecimal = matcherDuracion.group(1);
                String controladorTiempo = matcherDuracion.group(2);

                Instant instanteActual = Instant.now();
                Instant instanteFuturo = this.getFinalDuration(parteDecimal, controladorTiempo);


                for(Member miembro : miembrosMencionados){
                    //Tenemos los miembros, vamos a proceder a poner el rol
                    Sancion sancion = new Sancion(guild.getId(), miembro.getId());
                    sancion.setIdUsuarioAdmin(mensaje.getAuthor().getId());
                    sancion.setMotivo(motivo);
                    sancion.setFechaEvento(instanteActual.toEpochMilli());
                    sancion.setFechaVencimiento(instanteFuturo.toEpochMilli());
                    sancion.setIdEvento(Sancion.PERM_BAN);

                    sancion.actualizar();

                    miembro.kick().queue(e->{
                        PermBanEvent eventoBan = new PermBanEvent(Main.getBot(), 200, guild, mensaje.getAuthor().getId(), miembro.getId(), motivo);
                        Main.getControladorBans().addSancion(sancion);
                        Main.getLoggingListener().onEvent(eventoBan);
                    });
                }



                creator.description(String.format("Se han perma-baneado %d miembros", miembrosMencionados.size()));
                mensaje.replyEmbeds(creator.build()).queue();
            }
        }
    }

}
