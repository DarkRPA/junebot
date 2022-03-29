package org.darkrpa.discord.bots.june.model;

public class EnvOption {
    public static String DATABASE_URL = "DATABASE_URL";
    public static String DATABASE_PORT = "DATABASE_PORT";
    public static String DATABASE_USER = "DATABASE_USER";
    public static String DATABASE_PASSWORD = "DATABASE_PASSWORD";
    public static String DATABASE_MAIN_DATABASE = "DATABASE_MAIN_DATABASE";
    public static String DISCORD_TOKEN = "DISCORD_TOKEN";

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
