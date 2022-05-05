package org.darkrpa.discord.bots.june.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.darkrpa.discord.bots.june.exceptions.UnpairedArraysException;

public class Servidor extends ObjetoGuardable{
    private String idServidor;
    private int cantUsuarios;
    private String idCanalBienvenida = "";

    public Servidor(String idServidor){
        //Con este constructor lo que vamos a tener en cuenta es que quiere obtener la instancia directamente
        // de la base de datos por lo que vamos a cargar los datos desde ahi
        this.get(idServidor);
    }

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

    @Override
    public boolean eliminar() {
        if(!super.guardado){
            return false;
        }

        String sentencia = String.format("DELETE FROM servidor WHERE idServidor = '%s'", this.idServidor);
        int resultado = this.controller.execute(sentencia);
        if(resultado >= 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean actualizar() {
        String sentencia = "";
        if(!super.guardado){
            sentencia = String.format("INSERT INTO servidor VALUES ('%s', '%d', '%s');", this.idServidor, this.cantUsuarios, this.idCanalBienvenida);
        }else{
            sentencia = String.format("UPDATE servidor SET idServidor = '%s', cantUsuarios = '%d', idCanalBienvenida = '%s' WHERE idServidor = '%s'", this.idServidor, this.cantUsuarios, this.idCanalBienvenida, this.idServidor);
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
        //Reemplaza las propiedades de este objeto con las propiedades del objeto que hay en la base de datos
        ArrayList<String> columnas = new ArrayList<>();
        columnas.add("idServidor");
        ArrayList<Object> keys = new ArrayList<>();
        keys.add((String)id[0]);
        try {
            if(super.controller.exist("servidor", columnas, keys)){
                //Existe por lo que debemos de conseguir los datos
                String sentencia = "SELECT * FROM servidor WHERE idServidor = '"+(String)id[0]+"'";
                ArrayList<HashMap<String, Object>> obtenido = this.controller.get(sentencia);
                //Debemos acceder a la primera fila pues es la unica que va a ver, o deberia haber
                HashMap<String, Object> fila = obtenido.get(0);
                Iterator<String> iterador = fila.keySet().iterator();
                while(iterador.hasNext()){
                    String opcion = iterador.next();
                    switch (opcion) {
                        case "idServidor":
                            //Es el id del servidor
                            this.idServidor = (String) fila.get(opcion);
                            break;
                        case "cantUsuarios":
                            //Es el id del servidor
                            this.cantUsuarios = (int) fila.get(opcion);
                            break;
                        case "idCanalBienvenida":
                            //Es el id del servidor
                            this.idCanalBienvenida = (String) fila.get(opcion);
                            break;
                        default:
                            break;
                    }
                }
                super.guardado = true;
            }else{
                //No existe por lo que lo unico que podemos hacer es asignar el id al objeto
                this.idServidor = (String) id[0];
                super.guardado = false;
            }
        } catch (UnpairedArraysException e) {
            e.printStackTrace();
        }
    }


}
