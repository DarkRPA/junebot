package org.darkrpa.discord.bots.june.comandos.nivel;

import java.util.List;
import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.comandos.Comando;
import org.darkrpa.discord.bots.june.logging.discord.events.nivel.AddedExpDiscordEvent;
import org.darkrpa.discord.bots.june.model.UserNivel;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Comando encargado de poder dar una cantidad de EXP a un usuario especificado
 */
public class AddExp extends Comando{

    public AddExp(String nombre, Matcher matcher) {
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
        if(cantidad == null || this.isNumeric(cantidad.trim())){
            creator.description("La cantidad no es válida, recuerde usar solo valores númericos");
            mensaje.replyEmbeds(creator.build()).queue();
            return;
        }

        //Todo está en regla
        int cantidadNumerico = Integer.parseInt(cantidad.trim());
        Member miembro = menciones.get(0);
        UserNivel usuarioNivel = new UserNivel(miembro.getId(), server.getId());
        usuarioNivel.incrementarMensajes(cantidadNumerico);

        if(usuarioNivel.actualizar()){
            creator.description(String.format("Se ha dado `%d` exp a %s", cantidad, miembro.getAsMention()));
            mensaje.replyEmbeds(creator.build()).queue((e)->{
                //Emitimos el log
                AddedExpDiscordEvent eventoExp = new AddedExpDiscordEvent(Main.getBot(), 200, server, mensaje.getAuthor().getAsMention(), miembro.getAsMention(), cantidadNumerico);
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
