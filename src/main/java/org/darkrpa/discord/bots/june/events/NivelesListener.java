package org.darkrpa.discord.bots.june.events;

import org.darkrpa.discord.bots.june.model.UserNivel;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;
import org.darkrpa.discord.bots.june.utils.ImageEditor;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;

/**
 * Esta clase es la clase encargada de escuchar los mensajes del usuario y de aumentar sus mensajes en el discord
 * también es la encargada de notificar al usuario cuando ha subido de nivel
 *
 * @author Daniel Caparros Duran
 * @version 1.0
 * @since 1.0
 */
public class NivelesListener extends AbstractEventListener{

    public NivelesListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof MessageReceivedEvent){
            if(((MessageReceivedEvent)event).getChannel().getType() != ChannelType.PRIVATE){
                //Es un mensaje por lo que incrementamos en 1 la cantidad de mensajes que tiene el usuario
                MessageReceivedEvent eventoReal = (MessageReceivedEvent) event;
                if(!eventoReal.getAuthor().isBot()){
                    String idUsuario = eventoReal.getAuthor().getId();
                    String idServidor = eventoReal.getGuild().getId();

                    UserNivel nivelUsuario = new UserNivel(idUsuario, idServidor);

                    nivelUsuario.incrementarMensajes();

                    nivelUsuario.actualizar();

                    double nivelActual = NivelesListener.getNivelUsuario(nivelUsuario.getMensajes());
                    double nivelAntiguo = NivelesListener.getNivelUsuario(nivelUsuario.getMensajes()-1);

                    int nivelActualRedondeado = (int)Math.floor(nivelActual);
                    int nivelAntiguoRedondeado = (int)Math.floor(nivelAntiguo);

                    if(nivelActualRedondeado > nivelAntiguoRedondeado){
                        //Sabemos que ha ganado un nivel
                        //Le enviamos un embed felicitandole
                        EmbedCreator creator = EmbedCreator.generateDefaultTemplate();
                        creator.title("Subida de nivel").description(String.format("¡Felicidades! %s has subido de nivel y ahora eres nivel %d", eventoReal.getAuthor().getAsMention(), nivelActualRedondeado));
                        eventoReal.getMessage().replyEmbeds(creator.build()).queue(e->{
                            e.reply(ImageEditor.generateDefaultNivelTemplate(nivelUsuario, eventoReal.getAuthor()).saveImagen(), AttachmentOption.SPOILER).queue();
                        });
                    }
                }
            }
        }
    }

    public static double getNivelUsuario(int cantMensajes){
        double resultado = 0.75*Math.sqrt(cantMensajes);
        return resultado;
    }

}
