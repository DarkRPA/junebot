package org.darkrpa.discord.bots.june.model;

import org.darkrpa.discord.bots.june.anotaciones.CampoGetter;
import org.darkrpa.discord.bots.june.anotaciones.CampoSetter;
import org.darkrpa.discord.bots.june.anotaciones.Guardable;

@Guardable(nombreTabla = "tickets")
public class Ticket extends ObjetoGuardable{
    public static final String CERRADO="CERRADO", ABIERTO="ABIERTO";
    private long idTicket;
    private String idUsuarioAbrioTicket;
    private String idUsuarioCerroTicket;
    private String idServidor;
    private String idChat;
    private String estado;
    private long fechaCreacion;
    private long fechaCierre;
    private String causaApertura;
    private String resolucion;

    public Ticket(long idTicket) {
        this.idTicket = idTicket;
        this.get();
    }

    @CampoGetter(nombreColumna = "idTicket", isPrimary = true)
    public long getIdTicket() {
        return idTicket;
    }
    @CampoSetter(nombreColumna = "idTicket")
    public void setIdTicket(long idTicket) {
        this.idTicket = idTicket;
    }
    @CampoGetter(nombreColumna = "idUsuarioAbrioTicket")
    public String getIdUsuarioAbrioTicket() {
        return idUsuarioAbrioTicket;
    }
    @CampoSetter(nombreColumna = "idUsuarioAbrioTicket")
    public void setIdUsuarioAbrioTicket(String idUsuarioAbrioTicket) {
        this.idUsuarioAbrioTicket = idUsuarioAbrioTicket;
    }
    @CampoGetter(nombreColumna = "idUsuarioCerroTicket")
    public String getIdUsuarioCerroTicket() {
        return idUsuarioCerroTicket;
    }
    @CampoSetter(nombreColumna = "idUsuarioCerroTicket")
    public void setIdUsuarioCerroTicket(String idUsuarioCerroTicket) {
        this.idUsuarioCerroTicket = idUsuarioCerroTicket;
    }
    @CampoGetter(nombreColumna = "idServidor")
    public String getIdServidor() {
        return idServidor;
    }
    @CampoSetter(nombreColumna = "idServidor")
    public void setIdServidor(String idServidor) {
        this.idServidor = idServidor;
    }
    @CampoGetter(nombreColumna = "idChat")
    public String getIdChat() {
        return idChat;
    }
    @CampoSetter(nombreColumna = "idChat")
    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }
    @CampoGetter(nombreColumna = "estado")
    public String getEstado() {
        return estado;
    }
    @CampoSetter(nombreColumna = "estado")
    public void setEstado(String estado) {
        this.estado = estado;
    }
    @CampoGetter(nombreColumna = "fechaCreacion")
    public long getFechaCreacion() {
        return fechaCreacion;
    }
    @CampoSetter(nombreColumna = "fechaCreacion")
    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    @CampoGetter(nombreColumna = "fechaCierre")
    public long getFechaCierre() {
        return fechaCierre;
    }
    @CampoSetter(nombreColumna = "fechaCierre")
    public void setFechaCierre(long fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
    @CampoGetter(nombreColumna = "causaApertura")
    public String getCausaApertura() {
        return causaApertura;
    }
    @CampoSetter(nombreColumna = "causaApertura")
    public void setCausaApertura(String causaApertura) {
        this.causaApertura = causaApertura;
    }
    @CampoGetter(nombreColumna = "resolucion")
    public String getResolucion() {
        return resolucion;
    }
    @CampoSetter(nombreColumna = "resolucion")
    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }


}
