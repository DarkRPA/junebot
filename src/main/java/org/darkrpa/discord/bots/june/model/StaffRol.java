package org.darkrpa.discord.bots.june.model;


import org.darkrpa.discord.bots.june.anotaciones.CampoGetter;
import org.darkrpa.discord.bots.june.anotaciones.CampoSetter;
import org.darkrpa.discord.bots.june.anotaciones.Guardable;

@Guardable(nombreTabla = "staffRoles")
public class StaffRol extends ObjetoGuardable{
    private String idServidor;
    private String idRol;
    private String tipoPermiso;

    public StaffRol(String idRol, String idServidor){
        this.idRol = idRol; this.idServidor = idServidor;
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

    @CampoGetter(nombreColumna = "idRol", isPrimary = true)
    public String getIdRol() {
        return idRol;
    }
    @CampoSetter(nombreColumna = "idRol")
    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }
    @CampoGetter(nombreColumna = "tipo_permiso")
    public String getTipoPermiso() {
        return tipoPermiso;
    }
    @CampoSetter(nombreColumna = "tipo_permiso")
    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }


}
