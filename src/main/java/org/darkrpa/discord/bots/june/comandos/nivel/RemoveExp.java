package org.darkrpa.discord.bots.june.comandos.nivel;

import java.util.List;
import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.comandos.Comando;
import org.darkrpa.discord.bots.june.logging.discord.events.nivel.RemovedExpDiscordEvent;
import org.darkrpa.discord.bots.june.model.UserNivel;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RemoveExp extends Comando{

    public RemoveExp(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        MessageReceivedEvent evento = (MessageReceivedEvent)o;
        //Tenemos el evento, vamos a ver si ha mencionado a alguien, solo vamos a permitir añadir exp a la primera mencion
        Message mensaje = evento.getMessage();
        Guild server = evento.getGuild();

        List<Member> menciones = mensaje.getMentionedMembers();

        EmbedCreator creator = EmbedCreator.generateDefaultTemplate();

        if(menciones.isEmpty()){
            creator.description("No ha mencionado a ningún miembro");
            mensaje.replyEmbeds(creator.build()).queue();
            return;
        }

        String cantidad = this.matcher.group(5);
        if(cantidad == null || !this.isNumeric(cantidad.trim())){
            creator.description("La cantidad no es válida, recuerde usar solo valores númericos");
            mensaje.replyEmbeds(creator.build()).queue();
            return;
        }

        //Todo está en regla
        int cantidadNumerico = Integer.parseInt(cantidad.trim());
        Member miembro = menciones.get(0);
        UserNivel usuarioNivel = new UserNivel(miembro.getId(), server.getId());

        if(usuarioNivel.getMensajes() - cantidadNumerico <= 0){
            usuarioNivel.setMensajes(0);
        }else{
            usuarioNivel.decrementarMensajes(cantidadNumerico);
        }



        if(usuarioNivel.actualizar()){
            creator.description(String.format("Se ha quitado `%d` puntos de exp a %s", cantidadNumerico, miembro.getAsMention()));
            mensaje.replyEmbeds(creator.build()).queue((e)->{
                //Emitimos el log
                RemovedExpDiscordEvent eventoExp = new RemovedExpDiscordEvent(Main.getBot(), 200, server, mensaje.getAuthor().getId(), miembro.getAsMention(), cantidadNumerico);
                Main.getLoggingListener().onEvent(eventoExp);
            });
        }
    }

    private boolean isNumeric(String texto){
        for(int i = 0; i < texto.length(); i++){
            char caracter = texto.charAt(i);
            if(!Character.isDigit(caracter)){
                return false;
            }
        }
        return true;
    }

}
