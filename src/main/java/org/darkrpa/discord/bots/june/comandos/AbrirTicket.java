package org.darkrpa.discord.bots.june.comandos;

import java.time.Instant;
import java.util.EnumSet;

import org.darkrpa.discord.bots.june.model.Servidor;
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

/**
 * Clase encargada de añadir la funcionalidad para poder abrir un ticket nuevo, en este caso
 * vamos a comprobar que el canal donde ejecuto el comando es el correcto, sino, no hacemos nada
 */
public class AbrirTicket extends Comando{

    public AbrirTicket(String nombre) {
        super(nombre);
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
                    long actual = Instant.now().toEpochMilli();
                    String nombreCanalTicket = "ticket-"+actual;
                    category.createTextChannel(nombreCanalTicket).addPermissionOverride(rolEveryone, null, EnumSet.of(Permission.VIEW_CHANNEL)).queue(e->{
                        //Se ha creado el canal, debemos de poner toda la información necesaria para que los administradores
                        //lo sepan
                        TextChannel canalCreado = e;
                        //TODO Proseguir con la asignacion de los permisos
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
