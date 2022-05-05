package org.darkrpa.discord.bots.june.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.darkrpa.discord.bots.june.exceptions.UnpairedArraysException;

public class UserNivel extends ObjetoGuardable{

    private Usuario idUsuario;
    private Servidor idServidor;
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
        if((this.idUsuario == null || this.idServidor == null )|| (this.idUsuario.getIdUsuario().trim().isEmpty() || this.idServidor.getIdServidor().trim().isEmpty())){
            return false;
        }

        if(!this.idUsuario.guardado){
            this.idUsuario.actualizar();
        }

        if(!this.idServidor.guardado){
            this.idServidor.actualizar();
        }

        String sentencia = "";
        if(!super.guardado){
            sentencia = String.format("INSERT INTO niveles VALUES ('%s', '%s', '%s', '%s');", this.idUsuario.getIdUsuario(), this.idServidor.getIdServidor(), this.mensajes, this.tarjetaNivel);
        }else{
            sentencia = String.format("UPDATE niveles SET idUsuario = '%s', idServidor = '%s', mensajes = '%s', tarjetaNivel = '%s' WHERE idUsuario = '%s' AND idServidor = '%s'", this.idUsuario.getIdUsuario(), this.idServidor.getIdServidor(), this.mensajes, this.tarjetaNivel, this.idUsuario.getIdUsuario(), this.idServidor.getIdServidor());
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
                            this.idUsuario = new Usuario((String)columnasObtenidas.get(key));
                            break;
                        case "idServidor":
                            this.idServidor = new Servidor((String)columnasObtenidas.get(key));
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
                this.idUsuario = new Usuario((String)id[0]);
                this.idServidor = new Servidor((String)id[1]);
            }
        } catch (UnpairedArraysException e) {
            e.printStackTrace();
            super.guardado = false;
        }
    }

    public void incrementarMensajes(){
        this.mensajes++;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Servidor getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(Servidor idServidor) {
        this.idServidor = idServidor;
    }

    public int getMensajes() {
        return mensajes;
    }

    public int getExpToNextLvl(){
        return (int)Math.floor(Math.pow((this.getNivel()+1)/0.75,2));
    }

    public int getNivel(){
        return (int)Math.floor(0.75*Math.sqrt(this.mensajes));
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
