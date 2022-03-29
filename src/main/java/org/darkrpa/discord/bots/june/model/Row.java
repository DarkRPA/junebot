package org.darkrpa.discord.bots.june.model;

public class Row {
    private String nombreColumna;
    private Object valor;
    public Row(String nombreColumna, Object valor) {
        this.nombreColumna = nombreColumna;
        this.valor = valor;
    }
    public String getNombreColumna() {
        return nombreColumna;
    }
    public void setNombreColumna(String nombreColumna) {
        this.nombreColumna = nombreColumna;
    }
    public Object getValor() {
        return valor;
    }
    public void setValor(Object valor) {
        this.valor = valor;
    }


}
