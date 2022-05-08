package org.darkrpa.discord.bots.june.logging.clases;

import java.time.LocalDateTime;

import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;

public class RoleCreatedEvent extends GenericLoggingEvent {


    public RoleCreatedEvent(String textoEvento, Class claseEscucha) {
        super(textoEvento, claseEscucha);
    }

    @Override
    public EmbedCreator getMessage() {
        RoleCreateEvent evento = (RoleCreateEvent) super.eventoGenerico;
        EmbedCreator plantilla = EmbedCreator.generateDefaultTemplate();
        LocalDateTime tiempoActual = LocalDateTime.now();
        plantilla.title("Mensaje de LOG");
        plantilla.addField("Tipo", "Rol creado");
        plantilla.addField("Descripción", "Se ha creado el rol "+evento.getRole().getAsMention());
        plantilla.addField("Tiempo", "`"+tiempoActual+"`");
        //Tenemos el evento
        return plantilla;
    }

}
