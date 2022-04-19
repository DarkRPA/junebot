package org.darkrpa.discord.bots.june.model;

import java.util.ArrayList;

public class HelpOption {
    private String nombreComando;
    private String descripcion;
    private String casoDeUso;
    private ArrayList<HelpCategory> categories;

    public HelpOption(String nombreComando, String descripcion, String casoDeUso) {
        this.nombreComando = nombreComando;
        this.descripcion = descripcion;
        this.casoDeUso = casoDeUso;
        this.categories = new ArrayList<>();
    }

    public void addCategoria(HelpCategory category){
        this.categories.add(category);
    }

    public String getNombreComando() {
        return nombreComando;
    }

    public void setNombreComando(String nombreComando) {
        this.nombreComando = nombreComando;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCasoDeUso() {
        return casoDeUso;
    }

    public void setCasoDeUso(String casoDeUso) {
        this.casoDeUso = casoDeUso;
    }

    public ArrayList<HelpCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<HelpCategory> categories) {
        this.categories = categories;
    }

    public boolean hasCategory(String idCategory){
        for(HelpCategory category : this.categories){
            if(category.getIdCategoria().equals(idCategory)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getNombreComando();
    }



}
