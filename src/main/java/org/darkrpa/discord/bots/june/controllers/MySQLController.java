package org.darkrpa.discord.bots.june.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.exceptions.UnpairedArraysException;
import org.darkrpa.discord.bots.june.model.EnvOption;

/**
 * Controlador clave, este controlador es el que se va a encargar de todo lo relacionado con la base de datos
 * desde recoger los datos, convertir los modelos, encargarse de las actualizaciones y demas.
 * Deberemos de tener como propiedad estatica la conexion, para que asi las clases que requieran de hacer operaciones
 * no tengan que guardar una instancia del bot directamente
 *
 * @author Daniel Caparros Duran
 * @since 1.0
 * @version 1.0
 */
public class MySQLController {
    public static final int AUTO_INCREMENT = -1;
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

        this.conexion = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+db, user, password);
    }


    public ArrayList<HashMap<String, Object>> get(String sentencia){
        //Dada una sentencia nosotros lo que haremos será obtener e interpretar el resultado en una estructura de
        //ArrayList<ArrayList<HashMap<String, Object>>>, luego quien haya llamado al metodo se encargara
        //de interpretar esos datos, este metodo sirve tanto para cualquier consulta, da igual cual sea
        //solo necesita la sentencia SQL a ejecutar

        ArrayList<HashMap<String, Object>> resultado = new ArrayList<>();

        try (Statement estado = this.conexion.createStatement()) {
            ResultSet datosObtenidos = estado.executeQuery(sentencia);
            while(datosObtenidos.next()){
                ResultSetMetaData metadata = datosObtenidos.getMetaData();
                int cantColumnas = metadata.getColumnCount();
                //MySQL no empieza por el index 0 sino que empieza por 1
                HashMap<String, Object> dato = new HashMap<>();
                for(int i = 1; i <= cantColumnas; i++){
                    String nombreColumna = metadata.getColumnName(i);
                    Object valor = datosObtenidos.getObject(i);
                    dato.put(nombreColumna, valor);
                }
                resultado.add(dato);
            }
            estado.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    //Este metodo de lo que se va a encargar será de ejecutar cualquier sentencia que no sea una consulta
    //y devolvera la cantidad de filas afectadas
    public int execute(String sentencia){
        Statement estado;
        try {
            estado = this.conexion.createStatement();
            return estado.executeUpdate(sentencia);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }

    public ResultSet executeGetKeys(String sentencia){
        try {
            Statement estado = this.conexion.createStatement();

            estado.executeUpdate(sentencia, Statement.RETURN_GENERATED_KEYS);
            return estado.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Siempre que vayamos a hacer alguna operacion crearemos un nuevo statement, asi nos quitamos de complicaciones
    //De que se pueda cerrar el unico que esta abierto

    public boolean exist(String tabla, ArrayList<String> primaryKeys, ArrayList<Object> ids) throws UnpairedArraysException{
        //Este metodo se encargara de comprobar si existe un elemento en X tabla con Y id
        try {
            //Primero debemos de generar la sentencia SQL teniendo en cuenta la cantidad de claves primarias que hay
            String sentencia = "SELECT COUNT(*) FROM "+tabla+" WHERE ";
            if(primaryKeys.size() != ids.size()){
                throw new UnpairedArraysException("La cantidad de claves primarias no es la misma que ids o viceversa");
            }
            for(int i = 0; i < primaryKeys.size(); i++){
                Object idSeleccionada = ids.get(i);
                String columna = primaryKeys.get(i);

                //Debemos de concatenar un and al final de cada iteracion pero antes debemos de comprobar que no sea el ultimo

                sentencia += columna+" = '"+idSeleccionada+"'";

                if(!(i+1 >= primaryKeys.size())){
                    //Añadimos el and
                    sentencia += " and ";
                }
            }

            //En principio ya tenemos la sentencia por lo que vamos a hacer la consulta

            Statement estado = this.conexion.createStatement();
            ResultSet resultadoConsulta = estado.executeQuery(sentencia);
            resultadoConsulta.next();
            int cantidad = resultadoConsulta.getInt(1);
            estado.close();
            if(cantidad <= 0){
                return false;
            }else{
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
