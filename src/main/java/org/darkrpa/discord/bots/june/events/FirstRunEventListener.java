package org.darkrpa.discord.bots.june.events;

import java.awt.Color;
import java.util.EnumSet;
import java.util.List;

import org.darkrpa.discord.bots.june.model.Servidor;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.managers.RoleManager;
import net.dv8tion.jda.api.managers.channel.concrete.TextChannelManager;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

public class FirstRunEventListener extends AbstractEventListener{

    public static final EnumSet<Permission> PERMISOS = EnumSet.of(Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_SEND_IN_THREADS, Permission.MESSAGE_TTS, Permission.CREATE_PUBLIC_THREADS, Permission.CREATE_PRIVATE_THREADS, Permission.CREATE_INSTANT_INVITE);

    public FirstRunEventListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent event) {
        //tenemos el evento, ahora vamos a verificar que el evento es el de entrada por primera vez al servidor
        if(event instanceof GuildJoinEvent){
            //tenemos el server
            GuildJoinEvent evento = (GuildJoinEvent) event;
            Guild server = evento.getGuild();
            //Tenemos el servidor, como es la primera vez que se ejecuta no tendra canal de bienvenida
            Servidor servidor = new Servidor(server.getId(), server.getMemberCount());

            servidor.setRolMute(FirstRunEventListener.crearRolMute(server));

            servidor.actualizar();

            EmbedCreator embedCreator = EmbedCreator.generateDefaultTemplate();
            embedCreator.title("¡Mensaje importante!").description("Gracias por decidir en utilizar JuneBot y sus funcionalidades de administración. Aunque June es un bot independiente y robusto debido a limitaciones de la plataforma requerimos que los administradores realicen lo siguiente, para que el funcionamiento de June sea el mejor");
            embedCreator.addField("Rol de mute", "June de manera predeterminada crea el rol "+server.getRoleById(servidor.getRolMute()).getAsMention()+" con el cual prohibir de escritura a los silenciados, no obstante para que esto realmente funcione bien el administrador debe mover el rol por encima de los roles de los usuarios");

            //server.getDefaultChannel().sendMessageEmbeds(embedCreator.build()).queue();
        }
    }

    public static String crearRolMute(Guild server) {
        RoleAction rol = server.createRole();
        rol.setColor(Color.RED).setName("Muteado");
        Role rolFinal = rol.complete();

        RoleManager roleManager = rolFinal.getManager();
        roleManager.revokePermissions(Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_SEND_IN_THREADS, Permission.MESSAGE_TTS);
        roleManager.queue();

        //Vamos a recorrer todos los canales y vamos a ponerles que no puedan ser escritos por el rol de mute

        List<TextChannel> canales = server.getTextChannels();

        for(TextChannel canal : canales){
            TextChannelManager manager = canal.getManager();
            manager.putRolePermissionOverride(rolFinal.getIdLong(), null, FirstRunEventListener.PERMISOS).queue();
        }

        return rolFinal.getId();
    }

}

