package org.darkrpa.discord.bots.june.comandos.moderacion;

import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.comandos.Comando;

import net.dv8tion.jda.api.events.GenericEvent;

public class Kick extends Comando{

    public Kick(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent evento) {
        //TODO seguir con la creacion de los comandos de moderacion
    }

}
