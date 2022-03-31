package org.darkrpa.discord.bots.june.comandos;

import net.dv8tion.jda.api.events.GenericEvent;

public interface Comando {
    public void ejecutar(GenericEvent evento);
}
