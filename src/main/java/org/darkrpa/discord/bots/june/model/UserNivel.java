package org.darkrpa.discord.bots.june.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.darkrpa.discord.bots.june.exceptions.UnpairedArraysException;

public class UserNivel extends ObjetoGuardable{

    private String idUsuario;
    private String idServidor;
    private int mensajes;
    private String tarjetaNivel;

    public UserNivel(String idUsuario, String idServidor){
        this.get(idUsuario, idServidor);
    }

    @Override
    public boolean eliminar() {
        if(!super.guardado){
            return false;
        }

        String sentencia = String.format("DELETE FROM staffRoles WHERE idRol = '%s' AND idServidor = '%s'", this.idUsuario, this.idServidor);
        int resultado = this.controller.execute(sentencia);
        if(resultado >= 1){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public boolean actualizar() {
        if((this.idUsuario == null || this.idServidor == null )|| (this.idUsuario.trim().isEmpty() || this.idServidor.trim().isEmpty())){
            return false;
        }

        String sentencia = "";
        if(!super.guardado){
            sentencia = String.format("INSERT INTO niveles VALUES ('%s', '%s', '%s', '%s');", this.idUsuario, this.idServidor, this.mensajes, this.tarjetaNivel);
        }else{
            sentencia = String.format("UPDATE niveles SET idUsuario = '%s', idServidor = '%s', mensajes = '%s', tarjetaNivel = '%s' WHERE idUsuario = '%s' AND idServidor = '%s'", this.idUsuario, this.idServidor, this.mensajes, this.tarjetaNivel, this.idUsuario, this.idServidor);
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
        columnas.add("idServidor");
        ides.add(id[0]);
        ides.add(id[1]);


        try {
            if(super.controller.exist("niveles", columnas, ides)){
                super.guardado = true;

                String sentencia = String.format("SELECT * FROM niveles WHERE idUsuario = '%s' AND idServidor = '%s' ", id[0], id[1]);

                ArrayList<HashMap<String, Object>> resultadoGet = super.controller.get(sentencia);
                HashMap<String, Object> columnasObtenidas = resultadoGet.get(0);

                for(String key : columnasObtenidas.keySet()){
                    switch(key){
                        case "idUsuario":
                            this.idUsuario = (String)columnasObtenidas.get(key);
                            break;
                        case "idServidor":
                            this.idServidor = (String)columnasObtenidas.get(key);
                            break;
                        case "mensajes":
                            this.mensajes = (int)columnasObtenidas.get(key);
                            break;
                        case "tarjetaNivel":
                            this.tarjetaNivel = (String)columnasObtenidas.get(key);
                            break;
                    }
                }
            }else{
                super.guardado = false;
                this.idUsuario = (String)id[0];
                this.idServidor = (String)id[1];
            }
        } catch (UnpairedArraysException e) {
            e.printStackTrace();
            super.guardado = false;
        }
    }

    public void incrementarMensajes(){
        this.mensajes++;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(String idServidor) {
        this.idServidor = idServidor;
    }

    public int getMensajes() {
        return mensajes;
    }

    public void setMensajes(int mensajes) {
        this.mensajes = mensajes;
    }

    public String getTarjetaNivel() {
        return tarjetaNivel;
    }

    public void setTarjetaNivel(String tarjetaNivel) {
        this.tarjetaNivel = tarjetaNivel;
    }


}
