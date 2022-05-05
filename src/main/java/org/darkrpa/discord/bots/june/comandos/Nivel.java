package org.darkrpa.discord.bots.june.comandos;

import org.darkrpa.discord.bots.june.model.UserNivel;
import org.darkrpa.discord.bots.june.utils.ImageEditor;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Nivel extends Comando{

    public Nivel(String nombre) {
        super(nombre);
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
