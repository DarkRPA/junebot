package org.darkrpa.discord.bots.june.comandos.ticket;

import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.comandos.Comando;
import org.darkrpa.discord.bots.june.logging.clases.tickets.OpenTicketEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.tickets.GenericTicketGuildEvent;
import org.darkrpa.discord.bots.june.model.RolTicket;
import org.darkrpa.discord.bots.june.model.Servidor;
import org.darkrpa.discord.bots.june.model.Ticket;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

/**
 * Clase encargada de añadir la funcionalidad para poder abrir un ticket nuevo, en este caso
 * vamos a comprobar que el canal donde ejecuto el comando es el correcto, sino, no hacemos nada
 */
public class AbrirTicket extends Comando{

    public AbrirTicket(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        if(o instanceof MessageReceivedEvent){
            MessageReceivedEvent evento = (MessageReceivedEvent)o;
            TextChannel canal = (TextChannel) evento.getChannel();
            Member miembro = evento.getMember();
            Guild guild = evento.getGuild();
            Role rolEveryone = guild.getRoleById(guild.getId());
            //Primero miramos si esta habilitado el sistema de tickets en este servidor
            Servidor servidor = new Servidor(guild.getId());
            if(servidor.getTicketsHabilitado() == 1){
                //Esta habilitado por lo que comprobamos que el canal en el que lo envío es el mismo que el que está establecido
                if(servidor.getIdCanalTickets() != null && servidor.getIdCanalTickets().equals(canal.getId())){
                    //Lo hemos encontrado, esta en un canal valido
                    Category category = canal.getParentCategory();
                    if(category == null){
                        EmbedCreator creator = EmbedCreator.generateDefaultTemplate();
                        creator.description("¡El canal especificado para abrir tickets no está en ninguna categoría!");
                        evento.getMessage().replyEmbeds(creator.build()).queue();
                        return;
                    }
                    long actual = Instant.now().toEpochMilli();
                    String nombreCanalTicket = "ticket-"+actual;
                    //Vamos a conseguir todos los roles que pueden acceder a este ticket
                    ArrayList<RolTicket> roles = RolTicket.getRolsEnServer(servidor.getIdServidor());
                    ChannelAction<TextChannel> accionCreacionCanal = category.createTextChannel(nombreCanalTicket);
                    for(RolTicket rol : roles){
                        //Buscamos el rol
                        Role rolEnServidor = guild.getRoleById(rol.getIdRol());
                        if(rolEnServidor != null){
                            //Esta
                            accionCreacionCanal.addPermissionOverride(rolEnServidor, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null);
                        }
                    }

                    //Finalmente damos acceso a quien ha solicitado el ticket
                    accionCreacionCanal.addPermissionOverride(miembro, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null);
                    accionCreacionCanal.addPermissionOverride(rolEveryone, null, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND));

                    accionCreacionCanal.queue(e->{
                        EmbedCreator creator = EmbedCreator.generateDefaultTemplate();
                        creator.description("Se ha abierto el "+e.getAsMention()+" los administradores contactaran los más rápido posible");
                        evento.getMessage().replyEmbeds(creator.build()).queue();

                        //Vamos a coger la razon del matcher

                        String razonApertura = super.matcher.group(6);
                        if(razonApertura != null && !razonApertura.isBlank()){
                            //Tenemos la razon por la que la podemos podemos enviar al nuevo canal para dar contexto
                            EmbedCreator embedCreator = EmbedCreator.generateDefaultTemplate();
                            embedCreator.title(miembro.getEffectiveName()).description("*"+razonApertura.trim()+"*").thumbnail(miembro.getEffectiveAvatarUrl());
                            MessageAction actionMensaje = e.sendMessageEmbeds(embedCreator.build());
                            actionMensaje.queue();
                        }

                        //Ahora debemos de hacer el ticket en la base de datos
                        Ticket ticket = new Ticket(actual);
                        ticket.setIdUsuarioAbrioTicket(miembro.getId());
                        ticket.setIdServidor(servidor.getIdServidor());
                        ticket.setIdChat(e.getId());
                        ticket.setFechaCreacion(actual);
                        ticket.setCausaApertura((razonApertura.isBlank())?"no-especificada":razonApertura);
                        ticket.actualizar();

                        GenericTicketGuildEvent eventoTicket = new GenericTicketGuildEvent(Main.getBot(), 200, guild, "", evento.getMember().getId(), (razonApertura.isBlank())?"No especificada":razonApertura);
                        eventoTicket.setCanal(e.getAsMention());
                        Main.getLoggingListener().onEvent(eventoTicket);
                    });
                }
            }else{
                //No esta habilitado por lo que informamos al usuario
                EmbedCreator embedCreator = EmbedCreator.generateDefaultTemplate();
                embedCreator.description("El modulo de tickets no está habilitado en este servidor.");
                canal.sendMessageEmbeds(embedCreator.build()).queue();
            }
        }
    }

}
