package org.darkrpa.discord.bots.june.comandos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.controllers.MySQLController;
import org.darkrpa.discord.bots.june.exceptions.UnpairedArraysException;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;

public abstract class Comando {
    private String nombreComando;
    protected Matcher matcher;

    public abstract void ejecutar(GenericEvent evento);

    public Comando(String nombre, Matcher matcher){
        this.nombreComando = nombre;
        this.matcher = matcher;
    }

    public String getCommandName(){
        return this.nombreComando;
    }

    public boolean puedeEjecutar(Guild servidor, Member usuario){
        //Vamos a consultar a la base de datos que tipo de permiso tiene el comando
        MySQLController controlador = Main.getMySQLController();
        List<Role> rolesUsuario = usuario.getRoles();
        //El rol everyone puede?

        if(usuario.isOwner()){
            return true;
        }

        if(rolesUsuario.size() == 0){
            //No tiene roles
            // 1º Comprobar si esta en la lista de roles
            // 2º Si no está, comprobar que esta en la lista de comandos
            // 3º Si esta en la lista de comandos comprobar que tiene el rol de everyone
            // 4º Si estaba en la lista de roles entonces ver si tenia el rol de everyone
            ArrayList<String> listaKey = new ArrayList<>();
            listaKey.add("idcomando");
            listaKey.add("idServidor");
            ArrayList<Object> listaId = new ArrayList<>();
            listaId.add(this.nombreComando);
            listaId.add(servidor.getId());
            try {
                if(controlador.exist("rolcomandoservidor", listaKey, listaId)){
                    //Sabemos que existe
                    listaKey.add("idRol");
                    listaId.add(servidor.getId());
                    if(controlador.exist("rolcomandoservidor", listaKey, listaId)){
                        //Tiene permiso para ejecutar
                        return true;
                    }else{
                        return false;
                    }

                }else{
                    //No existe, miramos directamente en la lista de comandos
                    ArrayList<HashMap<String, Object>> comandoEncontrado = controlador.get("SELECT * FROM comando WHERE `nombreComando` = '"+this.nombreComando+"'");
                    if(comandoEncontrado.size() == 0){
                        //No hemos recibido nada, por lo que no esta registrado
                        return false;
                    }else{
                        //Sabemos que esta
                        if(comandoEncontrado.get(0).get("tipoPermiso").equals("@everyone")){
                            return true;
                        }else{
                            return false;
                        }
                    }
                }
            } catch (UnpairedArraysException e) {
                e.printStackTrace();
            }
        }

        for(Role rol : rolesUsuario){
            String query = String.format("SELECT * FROM rolcomandoservidor WHERE `idcomando` = '%s' AND `idRol` = '%s' AND `idServidor` = '%s'", this.nombreComando, rol.getId(), servidor.getIdLong());
            ArrayList<HashMap<String, Object>> listaFilas = controlador.get(query);
            if(listaFilas.size() == 0){
                //Esta vacio, por lo que no hay comandos programados, verificamos el valor por defecto, si no tiene permiso significa que no puede ejecutarlo
                ArrayList<HashMap<String, Object>> listaFilasComando = controlador.get("SELECT * FROM comando WHERE `nombreComando` = '"+this.nombreComando+"'");
                if(listaFilasComando.size() == 0){
                    //._.
                    return false;
                }

                HashMap<String, Object> comandoEncontrado = listaFilasComando.get(0);
                if(comandoEncontrado.get("tipoPermiso").equals("@everyone")){
                    return true;
                }
            }else{
                //El comando tiene permisos en la base de datos
                for(int i = 0; i < listaFilas.size(); i++){
                    HashMap<String, Object> fila = listaFilas.get(i);
                    String rolObtenidoBD = (String)fila.get("idRol");
                    if(rolObtenidoBD.equals(rol.getId())){
                        //Puede usarlo
                        return true;
                    }

                    if(rolObtenidoBD.equals(servidor.getId())){
                        //Puede ser ejecutado por todos los usuarios ya que tiene @everyone
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
