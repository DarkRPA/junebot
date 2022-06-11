package org.darkrpa.discord.bots.june.comandos.ticket;

import java.time.Instant;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.darkrpa.discord.bots.june.comandos.Comando;
import org.darkrpa.discord.bots.june.model.Servidor;
import org.darkrpa.discord.bots.june.model.Ticket;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Comando contrario al de abrirTicket, este comando solo servira dentro de un ticket y se encargará de cerrarlo
 * dandole una razon especifica, si o si se debe especificar la razon para así tener una constancia sobre
 * que fue lo que paso
 */
public class CerrarTicket extends Comando{

    public CerrarTicket(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        if(o instanceof MessageReceivedEvent){
            //Es nuestro evento
            MessageReceivedEvent evento = (MessageReceivedEvent)o;
            //Primero debemos de comprobar si el canal es valido, sino, mandamos un mensaje diciendo que el
            //canal no es valido
            Guild servidor = evento.getGuild();
            TextChannel canal = (TextChannel) evento.getChannel();
            Member miembro = evento.getMember();

            String nombreCanal = canal.getName();
            String regexCanal = "\\w+-(\\d+)";
            Pattern patron = Pattern.compile(regexCanal);
            Matcher matcher = patron.matcher(nombreCanal);
            if(matcher.matches()){
                //Es un canal valido
                String razon = super.matcher.group(6);
                Servidor server = new Servidor(servidor.getId());
                if(!razon.isBlank()){
                    long actual = Instant.now().toEpochMilli();
                    String idTicket = matcher.group(1);
                    Ticket ticket = new Ticket(Long.valueOf(idTicket));
                    //Tenemos el ticket, ponemos quien lo ha cerrado y la razon
                    ticket.setIdUsuarioCerroTicket(miembro.getId());
                    ticket.setEstado(Ticket.CERRADO);
                    ticket.setFechaCierre(actual);
                    ticket.setResolucion(razon);

                    ticket.actualizar();

                    if(server.getEliminarTicketsCerrados() == 0){
                        EmbedCreator creator = EmbedCreator.generateDefaultTemplate();
                        creator.title(miembro.getEffectiveName());
                        creator.thumbnail(miembro.getEffectiveAvatarUrl());
                        creator.addField("Ticket cerrado", "*"+razon.trim()+"*");
                        //No eliminamos el canal, simplemente quitamos el acceso al usuario de poder escribir
                        evento.getMessage().replyEmbeds(creator.build()).queue(e->{
                            //Una vez se ha enviado quitamos permisos de escribir a los usuarios que no son moderadores
                            List<PermissionOverride> permisos = canal.getPermissionOverrides();
                            for(PermissionOverride permiso : permisos){
                                if(permiso.isMemberOverride()){
                                    //Es un miembro, le quitamos el acceso
                                    permiso.delete().queue();
                                    //Para futuro podriamos tener en cuenta el hecho de que se pueda tal vez tener una categoria para tickets cerrados
                                }
                            }
                        });
                    }else{
                        //Se debe eliminar el canal
                        canal.delete().queue();
                    }


                }else{
                    //No ha especificado razon, por lo que se lo decimos
                    EmbedCreator creator = EmbedCreator.generateDefaultTemplate();
                    creator.title("Razón no especificada").description("Por favor especifique una razón para cerrar el ticket");
                    canal.sendMessageEmbeds(creator.build()).queue();
                }
            }else{
                //No es valido
                EmbedCreator creator = EmbedCreator.generateDefaultTemplate();
                creator.title("Formato no valido").description("El formato del comando no es valido, por favor revise !ayuda para obtener más información");
                canal.sendMessageEmbeds(creator.build()).queue();
            }
        }
    }

}
