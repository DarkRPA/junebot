package org.darkrpa.discord.bots.june.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.anotaciones.CampoGetter;
import org.darkrpa.discord.bots.june.anotaciones.CampoSetter;
import org.darkrpa.discord.bots.june.anotaciones.Guardable;

/**
 * Clase encargada de poder tener interpretados a los roles que pueden acceder y modificar tickets
 * dentro del bot. Usualmente solo podrán cerrarlo pues es la unica funcionalidad que tendremos actualmente
 */
@Guardable(nombreTabla = "rolestickets")
public class RolTicket extends ObjetoGuardable{
    private String idRol;
    private String idServidor;

    public RolTicket(String idRol, String idServidor){
        this.idRol = idRol;
        this.idServidor = idServidor;
        this.get();
    }

    @CampoGetter(nombreColumna = "idRol", isPrimary = true)
    public String getIdRol() {
        return idRol;
    }
    @CampoSetter(nombreColumna = "idRol")
    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    @CampoGetter(nombreColumna = "idServidor", isPrimary = true)
    public String getIdServidor() {
        return idServidor;
    }
    @CampoSetter(nombreColumna = "idServidor")
    public void setIdServidor(String idServidor) {
        this.idServidor = idServidor;
    }

    public static ArrayList<RolTicket> getRolsEnServer(String idServidor){
        //Vamos a obtener todos los roles que puede tener un servidor en concreto
        //y los vamos a devolver
        String query = String.format("SELECT idRol FROM rolestickets WHERE idServidor = '%s'", idServidor);
        ArrayList<HashMap<String, Object>> resultado = Main.getMySQLController().get(query);
        ArrayList<RolTicket> roles = new ArrayList<>();

        for(HashMap<String, Object> mapa : resultado){
            String idRol = (String)mapa.get("idRol");
            RolTicket encontrado = new RolTicket(idRol, idServidor);
            encontrado.actualizar();
            roles.add(encontrado);
        }

        return roles;
    }
}
