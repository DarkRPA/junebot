package org.darkrpa.discord.bots.june.comandos.moderacion;

import java.time.Instant;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.logging.discord.events.ban.BanEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.mute.MuteEvent;
import org.darkrpa.discord.bots.june.model.Servidor;
import org.darkrpa.discord.bots.june.model.sanciones.Sancion;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ban extends GenericModerationCommand{

    public Ban(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        //Todo seguir con la creacion del comando de bans
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

                if(this.matcher.group(4) == null){
                    creator.description("No se ha especificado la duración");
                    mensaje.replyEmbeds(creator.build()).queue();
                    return;
                }

                if(this.matcher.group(5) == null){
                    creator.description("No se ha especificado el motivo");
                    mensaje.replyEmbeds(creator.build()).queue();
                    return;
                }

                String duracion = this.matcher.group(4).trim();
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
                String embellecedor = this.getEmbellecedorTemporal(controladorTiempo);


                for(Member miembro : miembrosMencionados){
                    //Tenemos los miembros, vamos a proceder a poner el rol
                    Sancion sancion = new Sancion(guild.getId(), miembro.getId());
                    sancion.setIdUsuarioAdmin(mensaje.getAuthor().getId());
                    sancion.setMotivo(motivo);
                    sancion.setFechaEvento(instanteActual.toEpochMilli());
                    sancion.setFechaVencimiento(instanteFuturo.toEpochMilli());
                    sancion.setIdEvento(Sancion.BAN);

                    sancion.actualizar();

                    miembro.kick().queue(e->{
                        BanEvent eventoBan = new BanEvent(Main.getBot(), 200, guild, mensaje.getAuthor().getId(), miembro.getId(), motivo);
                        Main.getControladorBans().addSancion(sancion);
                        Main.getLoggingListener().onEvent(eventoBan);
                    });
                }



                creator.description(String.format("Se han baneado %d miembros por %s %s", miembrosMencionados.size(), parteDecimal, embellecedor));
                mensaje.replyEmbeds(creator.build()).queue();
            }
        }
    }

}
