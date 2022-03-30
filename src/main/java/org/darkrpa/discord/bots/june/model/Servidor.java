package org.darkrpa.discord.bots.june.model;

import org.darkrpa.discord.bots.june.controllers.MySQLController;

public class Servidor extends ObjetoGuardable{
    private String idServidor;
    private int cantUsuarios;
    private String idCanalBienvenida;

    public Servidor(String idServidor){
        //Con este constructor lo que vamos a tener en cuenta es que quiere obtener la instancia directamente
        // de la base de datos por lo que vamos a cargar los datos desde ahi
    }

    public Servidor(String idServidor, int cantUsuarios) {
        this.idServidor = idServidor;
        this.cantUsuarios = cantUsuarios;
    }

    public String getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(String idServidor) {
        this.idServidor = idServidor;
    }

    public int getCantUsuarios() {
        return cantUsuarios;
    }

    public void setCantUsuarios(int cantUsuarios) {
        this.cantUsuarios = cantUsuarios;
    }

    public String getIdCanalBienvenida() {
        return idCanalBienvenida;
    }

    public void setIdCanalBienvenida(String idCanalBienvenida) {
        this.idCanalBienvenida = idCanalBienvenida;
    }

    @Override
    public boolean eliminar() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean actualizar() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void get(Object id) {
        // TODO Auto-generated method stub

    }


}
