package org.darkrpa.discord.bots.june.events;

import org.darkrpa.discord.bots.june.comandos.Ayuda;

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
                new Ayuda().ejecutar(eventoReal);
            }
        }
    }

}
