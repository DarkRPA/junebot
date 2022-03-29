package org.darkrpa.discord.bots.june.model;

public class Servidor {
    private String idServidor;
    private int cantUsuarios;
    private String idCanalBienvenida;

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


}
