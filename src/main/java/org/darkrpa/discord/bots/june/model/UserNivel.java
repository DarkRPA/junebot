package org.darkrpa.discord.bots.june.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.darkrpa.discord.bots.june.anotaciones.CampoGetter;
import org.darkrpa.discord.bots.june.anotaciones.CampoSetter;
import org.darkrpa.discord.bots.june.anotaciones.Guardable;
import org.darkrpa.discord.bots.june.exceptions.UnpairedArraysException;

@Guardable(nombreTabla = "niveles")
public class UserNivel extends ObjetoGuardable{

    private String idUsuario;
    private String idServidor;
    private int mensajes;
    private String tarjetaNivel = "default";

    public UserNivel(String idUsuario, String idServidor){
        this.idServidor = idServidor;
        this.idUsuario = idUsuario;
        this.get();
    }

    public void incrementarMensajes(){
        this.mensajes++;
    }

    public void incrementarMensajes(int cantidad){
        this.mensajes += cantidad;
    }

    public void decrementarMensajes(int cantidad){
        this.mensajes -= cantidad;
    }

    @CampoGetter(nombreColumna = "idUsuario", isPrimary = true)
    public String getIdUsuario() {
        return idUsuario;
    }
    @CampoSetter(nombreColumna = "idUsuario")
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    @CampoGetter(nombreColumna = "idServidor", isPrimary = true)
    public String getIdServidor() {
        return idServidor;
    }
    @CampoSetter(nombreColumna = "idServidor")
    public void setIdServidor(String idServidor) {
        this.idServidor = idServidor;
    }
    @CampoGetter(nombreColumna = "mensajes")
    public int getMensajes() {
        return mensajes;
    }

    public int getExpToNextLvl(){
        return (int)Math.floor(Math.pow((this.getNivel()+1)/0.75,2));
    }

    public int getNivel(){
        return (int)Math.floor(0.75*Math.sqrt(this.mensajes));
    }
    @CampoSetter(nombreColumna = "mensajes")
    public void setMensajes(int mensajes) {
        this.mensajes = mensajes;
    }
    @CampoGetter(nombreColumna = "tarjetaNivel")
    public String getTarjetaNivel() {
        return tarjetaNivel;
    }
    @CampoSetter(nombreColumna = "tarjetaNivel")
    public void setTarjetaNivel(String tarjetaNivel) {
        this.tarjetaNivel = tarjetaNivel;
    }


}
