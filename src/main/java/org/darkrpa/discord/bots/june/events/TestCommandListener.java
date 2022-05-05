package org.darkrpa.discord.bots.june.events;

import java.awt.Font;

import org.darkrpa.discord.bots.june.comandos.Ayuda;
import org.darkrpa.discord.bots.june.comandos.Nivel;
import org.darkrpa.discord.bots.june.utils.ImageEditor;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TestCommandListener extends AbstractEventListener{

    public TestCommandListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof MessageReceivedEvent){
            //Controlador de prueba
            MessageReceivedEvent eventoReal = (MessageReceivedEvent)event;
            String mensaje = eventoReal.getMessage().getContentDisplay();
            if(mensaje.equals("!ayuda")){
                //new Ayuda().ejecutar(eventoReal);
            }else if(mensaje.equals("!nivel")){
                //new Nivel().ejecutar(eventoReal);
            }
        }
    }

}
