package org.darkrpa.discord.bots.june.model.sanciones;

public class LightSancion {
    private byte tipo;
    private int idSancion;
    private long idServer;
    private long idUsuario;
    private long milisToEnd;

    public LightSancion(byte tipo, int idSancion, long idServer, long idUsuario, long milisToEnd) {
        this.tipo = tipo;
        this.idSancion = idSancion;
        this.idServer = idServer;
        this.idUsuario = idUsuario;
        this.milisToEnd = milisToEnd;
    }


    public int getIdSancion() {
        return idSancion;
    }


    public long getMilisToEnd() {
        return milisToEnd;
    }


    public void setMilisToEnd(long milisToEnd) {
        this.milisToEnd = milisToEnd;
    }


    public void setIdSancion(int idSancion) {
        this.idSancion = idSancion;
    }


    public byte getTipo() {
        return tipo;
    }

    public void setTipo(byte tipo) {
        this.tipo = tipo;
    }

    public long getIdServer() {
        return idServer;
    }

    public void setIdServer(long idServer) {
        this.idServer = idServer;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
