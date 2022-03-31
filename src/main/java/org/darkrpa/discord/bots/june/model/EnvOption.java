package org.darkrpa.discord.bots.june.model;

public class EnvOption {
    public final static String DATABASE_URL = "DATABASE_URL";
    public final static String DATABASE_PORT = "DATABASE_PORT";
    public final static String DATABASE_USER = "DATABASE_USER";
    public final static String DATABASE_PASSWORD = "DATABASE_PASSWORD";
    public final static String DATABASE_MAIN_DATABASE = "DATABASE_MAIN_DATABASE";
    public final static String BOT_VERSION = "BOT_VERSION";
    public final static String BOT_NAME = "BOT_NAME";
    public final static String BOT_ICON = "BOT_ICON";
    public final static String EMBED_COLOR_RED = "EMBED_COLOR_RED";
    public final static String EMBED_COLOR_GREEN = "EMBED_COLOR_GREEN";
    public final static String EMBED_COLOR_BLUE = "EMBED_COLOR_BLUE";

    private String nombre;
    private String valor;

    public EnvOption(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }


}
