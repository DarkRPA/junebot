package org.darkrpa.discord.bots.june.thread;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.model.AccionEjecutable;
import org.darkrpa.discord.bots.june.model.EnvOption;

/**
 * Esta clase de lo que se encargará será de controlar cuando los mensajes pueden dejar de recibir
 * interacciones, esto lo hacemos para que mensajes que tengan mucha antiguedad no puedan seguir siendo interactuables
 *
 *
 */
public class TimerVerifier {
    //Lista de acciones, iremos añadiendo todas las acciones para tener un control de las mismas
    public static final long DEFAULT_TIME=Long.parseLong(Main.getOption(EnvOption.DEFAULT_EXECTUABLE_TIME).getValor());
    private ArrayList<AccionEjecutable> acciones;

    public TimerVerifier(){
        this.acciones = new ArrayList<>();
    }

    public void anadirOpcion(AccionEjecutable accion){
        this.anadirOpcion(accion, TimerVerifier.DEFAULT_TIME);
    }

    public void anadirOpcion(AccionEjecutable accion, long tiempo){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                accion.ejecutar();
            }

        }, tiempo);

        this.acciones.add(accion);
    }
}
