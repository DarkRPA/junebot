package org.darkrpa.discord.bots.june.comandos.moderacion;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.logging.discord.events.warn.WarnEvent;
import org.darkrpa.discord.bots.june.model.sanciones.Sancion;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Warn extends GenericModerationCommand{

    public Warn(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        if(o instanceof MessageReceivedEvent){
            MessageReceivedEvent evento = (MessageReceivedEvent)o;
            if(evento.getChannelType() == ChannelType.TEXT){
                EmbedCreator creator = EmbedCreator.generateDefaultTemplate();
                Message mensaje = evento.getMessage();
                User admin = mensaje.getAuthor();
                Guild server = mensaje.getGuild();
                List<Member> mencionado = mensaje.getMentionedMembers();
                if(mencionado.isEmpty()){
                    creator.description("No se ha mencionado a ningún usuario");
                    mensaje.replyEmbeds(creator.build()).queue();
                    return;
                }
                String motivo = this.matcher.group(5);
                if(motivo == null){
                    creator.description("No se ha especificado ningún motivo");
                    mensaje.replyEmbeds(creator.build()).queue();
                    return;
                }
                Instant momentoActual = Instant.now();
                Member miembro = mencionado.get(0);
                Sancion sancion = new Sancion(server.getId(), miembro.getId());
                sancion.setFechaEvento(momentoActual.toEpochMilli());
                sancion.setIdEvento(Sancion.WARN);
                sancion.setIdUsuarioAdmin(admin.getId());
                sancion.setMotivo(motivo);



                creator.title("Usuario advertido");
                creator.description(String.format("El usuario %s ha advertido al usuario %s", admin.getAsMention(), miembro.getAsMention()));
                creator.addField("Razón", "*"+motivo.trim()+"*");

                String textoOtrasAdvertencias = "";

                //Vamos a ver cuantas sanciones tiene este usuario en este servidor

                HashMap<String, Object> resultado = Main.getMySQLController().get(String.format("SELECT COUNT(*) AS cantidad FROM bans WHERE idServidor = '%s' AND idUsuarioSancionado = '%s' AND idEvento = '%s'", server.getId(), miembro.getId(), Sancion.WARN)).get(0);
                long cantidad = (long)resultado.get("cantidad");

                if(cantidad == 0){
                    textoOtrasAdvertencias = "Este usuario no ha tenido ninguna advertencia prevía";
                }else{
                    textoOtrasAdvertencias = "Este usuario ha tenido "+cantidad+" advertencias prevías";
                }


                sancion.actualizar();

                WarnEvent eventoWarn = new WarnEvent(Main.getBot(), 200, server, admin.getId(), miembro.getId(), motivo);
                Main.getLoggingListener().onEvent(eventoWarn);


                creator.addField("Otras advertencias", textoOtrasAdvertencias);

                mensaje.replyEmbeds(creator.build()).queue();
            }
        }
    }

}
