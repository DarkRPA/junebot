package org.darkrpa.discord.bots.june.events;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.model.Servidor;
import org.darkrpa.discord.bots.june.model.Usuario;
import org.darkrpa.discord.bots.june.utils.ImageEditor;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
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
            //Vamos a comprobar tambien si el usuario esta baneado y si lo está lo expulsamos hasta que se acabe su ban
            //Un usuario ha entrado
            GuildMemberJoinEvent eventoReal = (GuildMemberJoinEvent)event;

            Instant momentoActual = Instant.now();
            Guild guild = eventoReal.getGuild();
            Member miembro = eventoReal.getMember();
            ArrayList<HashMap<String, Object>> sancionesUsuario = Main.getMySQLController().get(String.format("SELECT * FROM bans WHERE idUsuarioSancionado = '%s' AND (idEvento = '4' || idEvento = '5') AND idServidor = '%s' AND fechaVencimiento >= '%d'", miembro.getId(), guild.getId(), momentoActual.toEpochMilli()));

            if(sancionesUsuario.size() != 0){
                //Hay registros validos por lo que esta baneado y no hay más que hablar
                miembro.kick("Estas baneado de "+guild.getName()).queue();
                return;
            }

            //Vamos a enviar la bienvenida

            ImageEditor editor = ImageEditor.generateDefaultWelcomeTemplate(miembro.getUser(), guild);
            Servidor server = new Servidor(guild.getId());

            if(server.getBienvenidasHabilitado() == 1){
                //Esta habilitado
                String canalBienvenidas = server.getIdCanalBienvenida();
                TextChannel canal = guild.getTextChannelById(canalBienvenidas);

                canal.sendFile(editor.saveImagen()).queue();
            }

            Usuario usuario = new Usuario(eventoReal.getUser().getId());
            usuario.setNombreUsuario(eventoReal.getUser().getName());
            usuario.actualizar();
        }
    }

}
