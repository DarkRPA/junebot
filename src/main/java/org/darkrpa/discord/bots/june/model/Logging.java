package org.darkrpa.discord.bots.june.model;

import org.darkrpa.discord.bots.june.anotaciones.CampoGetter;
import org.darkrpa.discord.bots.june.anotaciones.CampoSetter;
import org.darkrpa.discord.bots.june.anotaciones.Guardable;

@Guardable(nombreTabla = "logging")
public class Logging extends ObjetoGuardable{
    private String idServidor;
    private int permisos;
    private String canalLogging;
    private int habilitado;

    public Logging(String idServidor){
        this.idServidor = idServidor;
        this.get();
    }

    @CampoGetter(nombreColumna = "idServidor", isPrimary = true)
    public String getIdServidor() {
        return idServidor;
    }
    @CampoSetter(nombreColumna = "idServidor")
    public void setIdServidor(String idServidor) {
        this.idServidor = idServidor;
    }
    @CampoGetter(nombreColumna = "permisos")
    public int getPermisos() {
        return permisos;
    }
    @CampoSetter(nombreColumna = "permisos")
    public void setPermisos(int permisos) {
        this.permisos = permisos;
    }
    @CampoGetter(nombreColumna = "canalLogging")
    public String getCanalLogin() {
        return canalLogging;
    }
    @CampoSetter(nombreColumna = "canalLogging")
    public void setCanalLogin(String canalLogging) {
        this.canalLogging = canalLogging;
    }

    @CampoGetter(nombreColumna = "habilitado")
    public int getHabilitado() {
        return habilitado;
    }

    @CampoSetter(nombreColumna = "habilitado")
    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }

    //TODO Hacer la conversion de decimal a binario
    public int getBinary(){
        return 0;
    }
}
