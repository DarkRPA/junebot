package org.darkrpa.discord.bots.june.model;

import org.darkrpa.discord.bots.june.anotaciones.CampoGetter;
import org.darkrpa.discord.bots.june.anotaciones.CampoSetter;
import org.darkrpa.discord.bots.june.anotaciones.Guardable;

@Guardable(nombreTabla = "usuario")
public class Usuario extends ObjetoGuardable{
    private String idUsuario;
    private String nombreUsuario;

    private Usuario(){

    }

    public Usuario(String idUsuario){
        this.idUsuario = idUsuario;
        this.get();
    }

    @CampoGetter(nombreColumna = "idUsuario", isPrimary = true)
    public String getIdUsuario() {
        return idUsuario;
    }
    @CampoSetter(nombreColumna = "idUsuario")
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    @CampoGetter(nombreColumna = "nombreUsuario")
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    @CampoSetter(nombreColumna = "nombreUsuario")
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }



}
