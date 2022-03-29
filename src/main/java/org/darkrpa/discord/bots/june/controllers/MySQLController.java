package org.darkrpa.discord.bots.june.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.model.EnvOption;

/**
 * Controlador clave, este controlador es el que se va a encargar de todo lo relacionado con la base de datos
 * desde recoger los datos, convertir los modelos, encargarse de las actualizaciones y demas.
 *
 * @author Daniel Caparros Duran
 * @since 1.0
 * @version 1.0
 */
public class MySQLController {
    //Debemos de tener en cuenta
    //No necesitamos almacenar por ningun lado la instancia del bot pues no la vamos a utilizar

    //Conexion de la base de datos
    private Connection conexion;

    public MySQLController() throws SQLException{
        String host = Main.getOption(EnvOption.DATABASE_URL).getValor();
        String port = Main.getOption(EnvOption.DATABASE_PORT).getValor();
        String user = Main.getOption(EnvOption.DATABASE_USER).getValor();
        String password = Main.getOption(EnvOption.DATABASE_PASSWORD).getValor();
        String db = Main.getOption(EnvOption.DATABASE_MAIN_DATABASE).getValor();

        this.conexion = DriverManager.getConnection("jdbc:mysql:/"+host+":"+port+"/"+db, user, password);
    }

    //Siempre que vayamos a hacer alguna operacion crearemos un nuevo statement, asi nos quitamos de complicaciones
    //De que se pueda cerrar el unico que esta abierto

    //TODO: Modificar idColumn para momentos en los que pueda haber multiples id primarias
    public boolean exist(String tabla, String idColumn, Object id){
        //Este metodo se encargara de comprobar si existe un elemento en X tabla con Y id
        try {
            Statement estado = this.conexion.createStatement();
            ResultSet resultado = estado.executeQuery("SELECT COUNT(*) FROM "+tabla+" WHERE '"+idColumn+"' = "+(String)id);
            resultado.next();
            int numero = resultado.getInt("count");
            if(numero >= 1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
