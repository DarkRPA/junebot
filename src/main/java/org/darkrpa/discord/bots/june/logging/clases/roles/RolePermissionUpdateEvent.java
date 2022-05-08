package org.darkrpa.discord.bots.june.logging.clases.roles;

import java.util.EnumSet;

import org.darkrpa.discord.bots.june.logging.clases.GenericLoggingEvent;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePermissionsEvent;

public class RolePermissionUpdateEvent extends GenericLoggingEvent{

    public RolePermissionUpdateEvent(Class claseEscucha) {
        super(claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        RoleUpdatePermissionsEvent event = (RoleUpdatePermissionsEvent)super.eventoGenerico;
        EmbedCreator embedCreator = EmbedCreator.generateLogTemplate();
        embedCreator.addField("Tipo", "`RolePermissionChanged`");
        embedCreator.addField("Descripción", "Los permisos de "+event.getRole().getAsMention()+" han cambiado");
        EnumSet<Permission> permisosEventoNuevos = event.getNewPermissions().clone();
        EnumSet<Permission> permisosEventoAntiguos = event.getOldPermissions().clone();

        String permisosAdded = "+";
        String permisosRemoved = "-";

        permisosEventoAntiguos.removeAll(event.getNewPermissions());
        permisosEventoNuevos.removeAll(event.getOldPermissions());

        for(Permission permiso : permisosEventoAntiguos){
            permisosRemoved += permiso.getName()+" ";
        }

        for(Permission permiso : permisosEventoNuevos){
            permisosAdded += permiso.getName()+" ";
        }

        String resultadoFinal = permisosAdded+"\n"+permisosRemoved;

        embedCreator.addField("Cambios", "```diff\n"+resultadoFinal+"```");
        return embedCreator;
    }

}
