package org.darkrpa.discord.bots.june.model.sanciones;

import org.darkrpa.discord.bots.june.anotaciones.CampoGetter;
import org.darkrpa.discord.bots.june.anotaciones.CampoSetter;
import org.darkrpa.discord.bots.june.anotaciones.Guardable;
import org.darkrpa.discord.bots.june.model.ObjetoGuardable;

@Guardable(nombreTabla = "bans")
public class Sancion extends ObjetoGuardable{

    public final byte WARN=1, MUTE=2, KICK=3, BAN=4, PERM_BAN=5;

    private int idSancion = -1;
    private String idServidor;
    private String idUsuarioSancionado;
    private String idUsuarioAdmin;
    private String motivo;
    private int idEvento;
    private double fechaEvento;
    private double fechaVencimiento;

    public Sancion(String idServidor, String idUsuarioSancionado){
        this.idServidor = idServidor;
        this.idUsuarioSancionado = idUsuarioSancionado;
    }

    public Sancion(int idSancion, String idServidor, String idUsuarioSancionado){
        this.idSancion = idSancion;
        this.idServidor = idServidor;
        this.idUsuarioSancionado = idUsuarioSancionado;

        this.get();
    }

    @CampoGetter(nombreColumna = "idBan", isPrimary = true, isAutoIncremental = true)
    public int getIdSancion() {
        return idSancion;
    }
    @CampoSetter(nombreColumna = "idBan", isAutoIncremental = true)
    public void setIdSancion(int idSancion) {
        this.idSancion = idSancion;
    }
    @CampoGetter(nombreColumna = "idServidor", isPrimary = true)
    public String getIdServidor() {
        return idServidor;
    }
    @CampoSetter(nombreColumna = "idServidor")
    public void setIdServidor(String idServidor) {
        this.idServidor = idServidor;
    }
    @CampoGetter(nombreColumna = "idUsuarioSancionado", isPrimary = true)
    public String getIdUsuarioSancionado() {
        return idUsuarioSancionado;
    }
    @CampoSetter(nombreColumna = "idUsuarioSancionado")
    public void setIdUsuarioSancionado(String idUsuarioSancionado) {
        this.idUsuarioSancionado = idUsuarioSancionado;
    }
    @CampoGetter(nombreColumna = "idUsuarioAdmin")
    public String getIdUsuarioAdmin() {
        return idUsuarioAdmin;
    }
    @CampoSetter(nombreColumna = "idUsuarioAdmin")
    public void setIdUsuarioAdmin(String idUsuarioAdmin) {
        this.idUsuarioAdmin = idUsuarioAdmin;
    }
    @CampoGetter(nombreColumna = "motivo")
    public String getMotivo() {
        return motivo;
    }
    @CampoSetter(nombreColumna = "motivo")
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    @CampoGetter(nombreColumna = "idEvento")
    public int getIdEvento() {
        return idEvento;
    }
    @CampoSetter(nombreColumna = "idEvento")
    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }
    @CampoGetter(nombreColumna = "fechaEvento")
    public double getFechaEvento() {
        return fechaEvento;
    }
    @CampoSetter(nombreColumna = "fechaEvento")
    public void setFechaEvento(double fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
    @CampoGetter(nombreColumna = "fechaVencimiento")
    public double getFechaVencimiento() {
        return fechaVencimiento;
    }
    @CampoSetter(nombreColumna = "fechaVencimiento")
    public void setFechaVencimiento(double fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void revocar(){
        //Aqui debemos de poner la logica para que cuando se tenga que revocar la sancion se identifica el tipo de sanción
        //y se actue en consecuencia
    }
}
