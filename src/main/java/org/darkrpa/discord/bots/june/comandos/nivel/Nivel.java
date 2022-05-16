package org.darkrpa.discord.bots.june.comandos.nivel;

import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.comandos.Comando;
import org.darkrpa.discord.bots.june.model.UserNivel;
import org.darkrpa.discord.bots.june.utils.ImageEditor;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Nivel extends Comando{

    public Nivel(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent evento) {
        if(evento instanceof MessageReceivedEvent){
            MessageReceivedEvent mensajeReal = (MessageReceivedEvent)evento;
            UserNivel usuarioNivel = new UserNivel(mensajeReal.getAuthor().getId(), mensajeReal.getGuild().getId());
            mensajeReal.getMessage().reply(ImageEditor.generateDefaultNivelTemplate(usuarioNivel, mensajeReal.getAuthor()).saveImagen(), "Nivel.png").queue();
        }
    }

}
