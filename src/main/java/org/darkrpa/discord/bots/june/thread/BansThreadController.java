package org.darkrpa.discord.bots.june.thread;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.controllers.MySQLController;
import org.darkrpa.discord.bots.june.model.sanciones.LightSancion;
import org.darkrpa.discord.bots.june.model.sanciones.Sancion;

/**
 * Esta clase va a ser el nucleo del sistema de sanciones pues será el que no solo tenga todas las tareas sino quien
 * las cargue en cada reinicio de la aplicacion, para así no haber fallas de que ha dejado acceder a alguien
 */
public class BansThreadController {
    private ArrayList<Timer> tareas = new ArrayList<>();
    private MySQLController controlador;

    public BansThreadController(){
        this.controlador = Main.getMySQLController();
    }

    public void addSancion(Sancion sancion){
        //Vamos a comprimir la sancion
        LightSancion sancionLigera = new LightSancion((byte)sancion.getIdEvento(), sancion.getIdSancion(), Long.parseLong(sancion.getIdServidor()), Long.parseLong(sancion.getIdUsuarioSancionado()), sancion.getFechaVencimiento());
        this.addSancion(sancionLigera);
    }

    public void addSancion(LightSancion sancion){
        //Primero debemos de obtener la diferencia en milisegundos entre el momento actual y el pasado
        long momentoActual = Instant.now().toEpochMilli();
        long diferenciaMilis = sancion.getMilisToEnd() - momentoActual;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                //Cuando se termine, hacemos un objeto sancion y ejecutamos su revocar
                Sancion sancionFinal = new Sancion(sancion.getIdSancion(), String.valueOf(sancion.getIdServer()), String.valueOf(sancion.getIdUsuario()));
                sancionFinal.revocar();
                timer.cancel();
                tareas.remove(timer);
            }

        }, diferenciaMilis);
        tareas.add(timer);
    }

    /**
     * Metodo encargado de cargar todas las sanciones que aun son efectivas de la base de datos,
     * una vez cargadas son almacenadas en un arraylist para su posterior utilizacion
     */
    public void cargarTodasSanciones(){
        long momentoActual = Instant.now().toEpochMilli();
        String query = "SELECT idEvento, idBan, idServidor, idUsuarioSancionado, fechaVencimiento FROM bans WHERE fechaVencimiento > '"+momentoActual+"'";
        ArrayList<HashMap<String, Object>> listaFilas = this.controlador.get(query);

        for(HashMap<String, Object> fila : listaFilas){
            //Si o si debemos de tener lo que queremos pues sino tendriamos una lista vacia
            LightSancion sancion = new LightSancion(((Integer)fila.get("idEvento")).byteValue(), (int)fila.get("idBan"), Long.parseLong((String)fila.get("idServidor")), Long.parseLong((String)fila.get("idUsuarioSancionado")), (long) fila.get("fechaVencimiento"));
            //Tenemos la sancion, la insertamos
            this.addSancion(sancion);
        }
    }
}
