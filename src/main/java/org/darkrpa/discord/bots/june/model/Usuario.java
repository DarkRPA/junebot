package org.darkrpa.discord.bots.june.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.darkrpa.discord.bots.june.exceptions.UnpairedArraysException;

public class Usuario extends ObjetoGuardable{
    private String idUsuario;
    private String nombreUsuario;

    private Usuario(){

    }

    public Usuario(String idUsuario){
        this.get(idUsuario);
    }

    @Override
    public boolean eliminar() {
        if(!super.guardado){
            return false;
        }

        String sentencia = String.format("DELETE FROM usuario WHERE idUsuario = '%s'", this.idUsuario);
        int resultado = this.controller.execute(sentencia);
        if(resultado >= 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean actualizar() {
        //A la hora de guardar un usuario debemos de confirmar que la id del usuario no es nula o no está vacia

        if(this.idUsuario == null || this.idUsuario.trim().isEmpty()){
            return false;
        }

        String sentencia = "";
        if(!super.guardado){
            sentencia = String.format("INSERT INTO usuario VALUES ('%s', '%s');", this.idUsuario, this.nombreUsuario);
        }else{
            sentencia = String.format("UPDATE usuario SET idUsuario = '%s', nombreUsuario = '%s' WHERE idUsuario = '%s'", this.idUsuario, this.nombreUsuario, this.idUsuario);
        }

        int resultado = this.controller.execute(sentencia);
        if(resultado >= 1){
            super.guardado = true;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void get(Object ... id) {
        ArrayList<String> columnas = new ArrayList<>();
        ArrayList<Object> ides = new ArrayList<>();

        columnas.add("idUsuario");
        ides.add(id[0]);


        try {
            if(super.controller.exist("usuario", columnas, ides)){
                super.guardado = true;

                String sentencia = "SELECT * FROM usuario WHERE idUsuario = '"+(String)id[0]+"'";

                ArrayList<HashMap<String, Object>> resultadoGet = super.controller.get(sentencia);
                HashMap<String, Object> columnasObtenidas = resultadoGet.get(0);

                for(String key : columnasObtenidas.keySet()){
                    switch(key){
                        case "idUsuario":
                            this.idUsuario = (String)columnasObtenidas.get(key);
                            break;
                        case "nombreUsuario":
                            this.nombreUsuario = (String)columnasObtenidas.get(key);
                            break;
                    }
                }
            }else{
                super.guardado = false;
                this.idUsuario = (String)id[0];
            }
        } catch (UnpairedArraysException e) {
            e.printStackTrace();
            super.guardado = false;
        }
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }



}
