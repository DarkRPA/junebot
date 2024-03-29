package org.darkrpa.discord.bots.june.model;

import org.darkrpa.discord.bots.june.anotaciones.CampoGetter;
import org.darkrpa.discord.bots.june.anotaciones.CampoSetter;
import org.darkrpa.discord.bots.june.anotaciones.Guardable;

@Guardable(nombreTabla = "servidor")
public class Servidor extends ObjetoGuardable{
    private String idServidor;
    private int cantUsuarios;
    private int bienvenidasHabilitado = 0;
    private String idCanalBienvenida = "";
    private String idCanalTickets = "";
    private int ticketsHabilitado = 0;
    private int eliminarTicketsCerrados = 0;
    private String rolMute = "";

    public Servidor(String idServidor){
        //Con este constructor lo que vamos a tener en cuenta es que quiere obtener la instancia directamente
        // de la base de datos por lo que vamos a cargar los datos desde ahi
        this.idServidor = idServidor;
        this.get();
    }

    public Servidor(String idServidor, int cantUsuarios) {
        this.idServidor = idServidor;
        this.cantUsuarios = cantUsuarios;
    }

    @CampoGetter(nombreColumna = "idServidor", isPrimary = true)
    public String getIdServidor() {
        return idServidor;
    }

    @CampoSetter(nombreColumna = "idServidor")
    public void setIdServidor(String idServidor) {
        this.idServidor = idServidor;
    }
    @CampoGetter(nombreColumna = "eliminarTicketsCerrados")
    public int getEliminarTicketsCerrados() {
        return eliminarTicketsCerrados;
    }

    @CampoGetter(nombreColumna = "bienvenidasHabilitado")
    public int getBienvenidasHabilitado() {
        return bienvenidasHabilitado;
    }
    @CampoSetter(nombreColumna = "bienvenidasHabilitado")
    public void setBienvenidasHabilitado(int bienvenidasHabilitado) {
        this.bienvenidasHabilitado = bienvenidasHabilitado;
    }

    @CampoSetter(nombreColumna = "eliminarTicketsCerrados")
    public void setEliminarTicketsCerrados(int eliminarTicketsCerrados) {
        this.eliminarTicketsCerrados = eliminarTicketsCerrados;
    }

    @CampoGetter(nombreColumna = "cantUsuarios")
    public int getCantUsuarios() {
        return cantUsuarios;
    }
    @CampoSetter(nombreColumna = "cantUsuarios")
    public void setCantUsuarios(int cantUsuarios) {
        this.cantUsuarios = cantUsuarios;
    }

    @CampoGetter(nombreColumna = "idCanalBienvenida")
    public String getIdCanalBienvenida() {
        return idCanalBienvenida;
    }
    @CampoSetter(nombreColumna = "idCanalBienvenida")
    public void setIdCanalBienvenida(String idCanalBienvenida) {
        this.idCanalBienvenida = idCanalBienvenida;
    }

    @CampoGetter(nombreColumna = "idCanalTickets")
    public String getIdCanalTickets() {
        return idCanalTickets;
    }
    @CampoSetter(nombreColumna = "idCanalTickets")
    public void setIdCanalTickets(String idCanalTickets) {
        this.idCanalTickets = idCanalTickets;
    }
    @CampoGetter(nombreColumna = "ticketsHabilitado")
    public int getTicketsHabilitado() {
        return ticketsHabilitado;
    }
    @CampoSetter(nombreColumna = "ticketsHabilitado")
    public void setTicketsHabilitado(int ticketsHabilitado) {
        this.ticketsHabilitado = ticketsHabilitado;
    }

    @CampoGetter(nombreColumna = "rolMute")
    public String getRolMute() {
        return rolMute;
    }
    @CampoSetter(nombreColumna = "rolMute")
    public void setRolMute(String rolMute) {
        this.rolMute = rolMute;
    }


}
