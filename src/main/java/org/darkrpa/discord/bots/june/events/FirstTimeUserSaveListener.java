package org.darkrpa.discord.bots.june.events;

import org.darkrpa.discord.bots.june.model.Usuario;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;


/**
 * Esta clase va a ser la que se va a encargar de insertar los miembros por primera vez en la base de datos
 * y de mostrar los mensajes de bienvenida
 */
public class FirstTimeUserSaveListener extends AbstractEventListener{

    public FirstTimeUserSaveListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof GuildMemberJoinEvent){
            //Un usuario ha entrado
            GuildMemberJoinEvent eventoReal = (GuildMemberJoinEvent)event;
            Usuario usuario = new Usuario(eventoReal.getUser().getId());
            usuario.setNombreUsuario(eventoReal.getUser().getName());
            usuario.actualizar();
        }
    }

}
