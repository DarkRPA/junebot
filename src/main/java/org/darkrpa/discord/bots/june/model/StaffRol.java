package org.darkrpa.discord.bots.june.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.darkrpa.discord.bots.june.exceptions.UnpairedArraysException;

public class StaffRol extends ObjetoGuardable{
    public static final String MODERADOR = "moderador";
    public static final String ADMINISTRADOR = "admin";
    public static final String SUPER_ADMIN = "super_admin";

    private String idServidor;
    private String idRol;
    private String tipoPermiso;

    public StaffRol(String idRol, String idServidor){
        this.get(idRol, idServidor);
    }

    @Override
    public boolean eliminar() {
        if(!super.guardado){
            return false;
        }

        String sentencia = String.format("DELETE FROM staffRoles WHERE idRol = '%s' AND idServidor = '%s'", this.idRol, this.idServidor);
        int resultado = this.controller.execute(sentencia);
        if(resultado >= 1){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public boolean actualizar() {
        if((this.idRol == null || this.idServidor == null )|| (this.idRol.trim().isEmpty() || this.idServidor.trim().isEmpty())){
            return false;
        }

        String sentencia = "";
        if(!super.guardado){
            sentencia = String.format("INSERT INTO staffRoles VALUES ('%s', '%s', '%s');", this.idRol, this.idServidor, this.tipoPermiso);
        }else{
            sentencia = String.format("UPDATE staffRoles SET idRol = '%s', idServidor = '%s', tipo_permiso = '%s' WHERE idRol = '%s' AND idServidor = '%s'", this.idRol, this.idServidor, this.tipoPermiso, this.idRol, this.idServidor);
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

        columnas.add("idRol");
        columnas.add("idServidor");
        ides.add(id[0]);
        ides.add(id[1]);


        try {
            if(super.controller.exist("staffRoles", columnas, ides)){
                super.guardado = true;

                String sentencia = String.format("SELECT * FROM staffRoles WHERE idRol = '%s' AND idServidor = '%s' ", id[0], id[1]);

                ArrayList<HashMap<String, Object>> resultadoGet = super.controller.get(sentencia);
                HashMap<String, Object> columnasObtenidas = resultadoGet.get(0);

                for(String key : columnasObtenidas.keySet()){
                    switch(key){
                        case "idRol":
                            this.idRol = (String)columnasObtenidas.get(key);
                            break;
                        case "idServidor":
                            this.idServidor = (String)columnasObtenidas.get(key);
                            break;
                        case "tipo_permiso":
                            this.tipoPermiso = (String)columnasObtenidas.get(key);
                            break;
                    }
                }
            }else{
                super.guardado = false;
                this.idRol = (String)id[0];
                this.idServidor = (String)id[1];
            }
        } catch (UnpairedArraysException e) {
            e.printStackTrace();
            super.guardado = false;
        }
    }
}
