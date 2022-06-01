package org.darkrpa.discord.bots.june.comandos.moderacion;

import java.time.Duration;
import java.time.Instant;
import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.comandos.Comando;
import org.darkrpa.discord.bots.june.thread.BansThreadController;

import net.dv8tion.jda.api.events.GenericEvent;

/**
 * Esta clase nos va a servir para poder añadir funcionalidad básica a todos los comandos que sean de administracion como
 * por ejemplo coger la fecha actual y sumarle + X tiempo
 */
public abstract class GenericModerationCommand extends Comando{

    public GenericModerationCommand(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    /**
     * Metodo encargado de interpretar la duracion que el usuario ha introducido en el comando
     * y generar una fecha final con dicha duracion
     * @param duracionComando
     * @return
     */
    public Instant getFinalDuration(String parteDecimal, String controladorTemporal){
        Instant instanteFuturo = Instant.now();

        switch (controladorTemporal) {
            case BansThreadController.MINUTOS:
                Duration durationMinutos = Duration.ofMinutes(Integer.parseInt(parteDecimal));
                instanteFuturo = instanteFuturo.plus(durationMinutos);
                break;
            case BansThreadController.DIAS:
                Duration durationDias = Duration.ofDays(Integer.parseInt(parteDecimal));
                instanteFuturo = instanteFuturo.plus(durationDias);
                break;
            case BansThreadController.SEMANAS:
                Duration durationSemanas = Duration.ofDays(Integer.parseInt(parteDecimal)*7);
                instanteFuturo = instanteFuturo.plus(durationSemanas);
                break;
            case BansThreadController.MESES:
                Duration durationMeses = Duration.ofDays(Integer.parseInt(parteDecimal)*31);
                instanteFuturo = instanteFuturo.plus(durationMeses);
                break;
            case BansThreadController.ANIOS:
                Duration durationAnios = Duration.ofDays(Integer.parseInt(parteDecimal)*365);
                instanteFuturo = instanteFuturo.plus(durationAnios);
                break;
            default:
                break;
        }

        return instanteFuturo;
    }

    public String getEmbellecedorTemporal(String controladorTemporal){
        String embellecedor = "";
        switch (controladorTemporal) {
            case BansThreadController.MINUTOS:
                embellecedor = "minutos";
                break;
            case BansThreadController.DIAS:
                embellecedor = "dias";
                break;
            case BansThreadController.SEMANAS:
                embellecedor = "semanas";
                break;
            case BansThreadController.MESES:
                embellecedor = "meses";
                break;
            case BansThreadController.ANIOS:
                embellecedor = "años";
                break;
            default:
                break;
        }
        return embellecedor;
    }

}
