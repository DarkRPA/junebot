package org.darkrpa.discord.bots.june.comandos.moderacion;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.comandos.Comando;
import org.darkrpa.discord.bots.june.logging.discord.events.mute.MuteEvent;
import org.darkrpa.discord.bots.june.model.Servidor;
import org.darkrpa.discord.bots.june.model.sanciones.Sancion;
import org.darkrpa.discord.bots.june.thread.BansThreadController;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Comando encargado de poder mutear a un usuario en especifico
 */
public class Mute extends Comando{

    public Mute(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        if(o instanceof MessageReceivedEvent){
            MessageReceivedEvent evento = (MessageReceivedEvent)o;

            if(evento.getChannelType() == ChannelType.TEXT){
                Guild guild = evento.getGuild();
                Servidor server = new Servidor(guild.getId());
                Role rolMute = guild.getRoleById(server.getRolMute());
                Message mensaje = evento.getMessage();
                //Vamos a permitir la expulsion masiva
                List<Member> miembrosMencionados = mensaje.getMentionedMembers();

                EmbedCreator creator = EmbedCreator.generateDefaultTemplate();

                if(miembrosMencionados.isEmpty()){
                    //Es vacio
                    creator.description("No se han mencionado miembros para silenciar");
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

                String duracion = this.matcher.group(4);
                String motivo = this.matcher.group(5);

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
                String embellecedor = "";
                Instant instanteActual = Instant.now();
                Instant instanteFuturo = Instant.now();

                switch (controladorTiempo) {
                    case BansThreadController.MINUTOS:
                        Duration durationMinutos = Duration.ofMinutes(Integer.parseInt(parteDecimal));
                        instanteFuturo.plus(durationMinutos);
                        embellecedor = "minutos";
                        break;
                    case BansThreadController.DIAS:
                        Duration durationDias = Duration.ofDays(Integer.parseInt(parteDecimal));
                        instanteFuturo.plus(durationDias);
                        embellecedor = "dias";
                        break;
                    case BansThreadController.SEMANAS:
                        Duration durationSemanas = Duration.ofDays(Integer.parseInt(parteDecimal)*7);
                        instanteFuturo.plus(durationSemanas);
                        embellecedor = "semanas";
                        break;
                    case BansThreadController.MESES:
                        Duration durationMeses = Duration.ofDays(Integer.parseInt(parteDecimal)*31);
                        instanteFuturo.plus(durationMeses);
                        embellecedor = "meses";
                        break;
                    case BansThreadController.ANIOS:
                        Duration durationAnios = Duration.ofDays(Integer.parseInt(parteDecimal)*365);
                        instanteFuturo.plus(durationAnios);
                        embellecedor = "años";
                        break;
                    default:
                        break;
                }


                for(Member miembro : miembrosMencionados){
                    //Tenemos los miembros, vamos a proceder a poner el rol
                    Sancion sancion = new Sancion(guild.getId(), miembro.getId());
                    sancion.setIdUsuarioAdmin(mensaje.getAuthor().getId());
                    sancion.setMotivo(motivo);
                    sancion.setFechaEvento(instanteActual.toEpochMilli());
                    sancion.setFechaVencimiento(instanteFuturo.toEpochMilli());
                    sancion.setIdEvento(Sancion.MUTE);

                    sancion.actualizar();

                    guild.addRoleToMember(miembro, rolMute).queue(e->{
                        MuteEvent eventoMute = new MuteEvent(Main.getBot(), 200, guild, mensaje.getAuthor().getId(), miembro.getId(), motivo);
                        Main.getControladorBans().addSancion(sancion);
                        Main.getLoggingListener().onEvent(eventoMute);
                    });
                }



                creator.description(String.format("Se han silenciado %d miembros por %s %s", miembrosMencionados.size(), parteDecimal, embellecedor));
                mensaje.replyEmbeds(creator.build()).queue();
            }
        }
    }

}
