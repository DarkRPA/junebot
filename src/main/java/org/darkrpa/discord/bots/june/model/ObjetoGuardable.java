package org.darkrpa.discord.bots.june.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.anotaciones.CampoGetter;
import org.darkrpa.discord.bots.june.anotaciones.CampoSetter;
import org.darkrpa.discord.bots.june.anotaciones.Guardable;
import org.darkrpa.discord.bots.june.controllers.MySQLController;

/**
 * Esta clase va a ser el controlador que maneje las anotaciones de las clases hijas cuando
 * alguna quiera realizar alguna operacion
 */
public class ObjetoGuardable {
    protected MySQLController controller;
    protected boolean guardado;

    public ObjetoGuardable(){
        this.controller = Main.getMySQLController();
    }

    //TODO Revisar documentacion

    /**
     * Metodo eliminar, este metodo va a ser el encargado de eliminar la instancia del objeto de la base de datos
     * cada clase especificara el funcionamiento para eliminar su objeto
     * @return
     */
    public boolean eliminar(){
        Class claseActual = this.getClass();

        if(claseActual.isAnnotationPresent(Guardable.class)){

            //Sabemos que esta anotada, podemos empezar la mágia
            //Primero agarramos todos los métodos que pueda contener -4 que es la cantidad de métodos
            //Heredados por Object
            //Este array de valores tendrá todo lo que necesitamos
            try {
                Method[] metodos = claseActual.getMethods();
                ArrayList<HashMap<String, Object>> valoresGet = this.getValores();
                String nombreTabla = this.getNombreTabla();

                //Tenemos todos los valores bien obtenidos :) Ahora podemos proseguir con el conseguir los datos, en base a las claves primarias
                //que hemos obtenido vamos a hacer un get a la tabla

                long contarPrimarias = valoresGet.stream().filter(e -> (boolean)e.get("isPrimary")).count();

                String query = "DELETE FROM "+nombreTabla;
                if(contarPrimarias > 0){
                    //Sabemos que hay primarias
                    List<HashMap<String, Object>> primarias = valoresGet.stream().filter(e -> (boolean)e.get("isPrimary")).collect(Collectors.toList());
                    query += " WHERE ";
                    for(int i = 0; i < primarias.size(); i++){
                        HashMap<String, Object> valor = primarias.get(i);

                        query += valor.get("nombreColumna")+" = '"+valor.get("valor")+"'";

                        if(!(i + 1 >= primarias.size())){
                            query += " AND ";
                        }
                    }
                }else{
                    return false;
                }

                if(this.controller.execute(query) >= 1){
                    this.guardado = false;
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            return false;
        }
        return false;
    }
    /**
     * Actualizar es un metodo el cual debera de poder actualizar el objeto de la tabla en la base de datos
     * @return
     */
    public boolean actualizar(){
        Class claseActual = this.getClass();

        if(claseActual.isAnnotationPresent(Guardable.class)){

            //Sabemos que esta anotada, podemos empezar la mágia
            //Primero agarramos todos los métodos que pueda contener -4 que es la cantidad de métodos
            //Heredados por Object
            //Este array de valores tendrá todo lo que necesitamos
            try {
                String nombreTabla = this.getNombreTabla();
                ArrayList<HashMap<String, Object>> valoresGet = this.getValores();

                //Tenemos todos los valores bien obtenidos :) Ahora podemos proseguir con el conseguir los datos, en base a las claves primarias
                //que hemos obtenido vamos a hacer un get a la tabla

                long contarPrimarias = valoresGet.stream().filter(e -> (boolean)e.get("isPrimary")).count();

                String query = "";
                if(contarPrimarias > 0){
                    List<HashMap<String, Object>> primarias = valoresGet.stream().filter(e -> (boolean)e.get("isPrimary")).collect(Collectors.toList());
                    //Sabemos que hay primarias
                    if(this.guardado){
                        //Esta guardado de ante mano
                        query = "UPDATE "+nombreTabla+" SET ";

                        for(int i = 0; i < valoresGet.size(); i++){
                            HashMap<String, Object> valoresFinales = valoresGet.get(i);
                            Object valor = valoresFinales.get("valor")==null?"null":"'"+valoresFinales.get("valor")+"'";
                            if(i == 0){
                                //Es el primero
                                query += valoresFinales.get("nombreColumna")+" = "+valor;
                            }else{
                                query += ", "+valoresFinales.get("nombreColumna")+" = "+valor;
                            }
                        }

                        query += " WHERE ";
                        for(int i = 0; i < primarias.size(); i++){
                            HashMap<String, Object> valor = primarias.get(i);

                            query += valor.get("nombreColumna")+" = '"+valor.get("valor")+"'";

                            if(!(i + 1 >= primarias.size())){
                                query += " AND ";
                            }
                        }
                    }else{
                        query = "INSERT INTO "+nombreTabla+" ";
                        String columnas = "(";
                        String valores = "(";
                        for(int i = 0; i < valoresGet.size(); i++){
                            HashMap<String, Object> valoresFinales = valoresGet.get(i);
                            Object valor = valoresFinales.get("valor")==null?"null":"'"+valoresFinales.get("valor")+"'";
                            if(i == 0){
                                //Es el primero
                                columnas += valoresFinales.get("nombreColumna");
                                valores += valor;
                            }else if(i + 1 >= valoresGet.size()){
                                //Es el ultimo
                                columnas +=", "+valoresFinales.get("nombreColumna")+")";
                                valores +=", "+valor+")";
                            }else{
                                //Es uno del medio
                                columnas +=", "+valoresFinales.get("nombreColumna");
                                valores +=", "+valor;
                            }
                        }

                        query += columnas+" VALUES "+valores;
                    }


                }else{
                    return false;
                }

                //Tenemos la query de la consulta, podemos proceder

                if(this.controller.execute(query) >= 1){
                    this.guardado = true;
                    return true;
                }else{
                    this.guardado = false;
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            this.guardado = false;
        }
        return false;
    }
    /**
     * Metodo get, este metodo get lo que hara sera buscar en la tabla de la clase un objeto con la id especifica
     * sino no lo encuentra lo creara con esa id y vacio, hasta que no se actualice no se insertará en la base de datos
     */
    public void get(){
        //Primero vamos a hacer la prueba con el metodo get.
        //Para ello lo que tendremos que hacer será descubir cuantos metodos getter tenemos, almacenarlos
        //en un HashMap y luego hacer la consulta SQL.
        Class claseActual = this.getClass();

        if(claseActual.isAnnotationPresent(Guardable.class)){
            ArrayList<HashMap<String, Object>> valoresGet = this.getValores();
            String nombreTabla = this.getNombreTabla();
            Method[] metodos = claseActual.getMethods();
            //Sabemos que esta anotada, podemos empezar la mágia
            //Primero agarramos todos los métodos que pueda contener -4 que es la cantidad de métodos
            //Heredados por Object
            //Este array de valores tendrá todo lo que necesitamos
            try {
                //Tenemos todos los valores bien obtenidos :) Ahora podemos proseguir con el conseguir los datos, en base a las claves primarias
                //que hemos obtenido vamos a hacer un get a la tabla

                long contarPrimarias = valoresGet.stream().filter(e -> (boolean)e.get("isPrimary")).count();

                String query = "SELECT * FROM "+nombreTabla;
                if(contarPrimarias > 0){
                    //Sabemos que hay primarias
                    List<HashMap<String, Object>> primarias = valoresGet.stream().filter(e -> (boolean)e.get("isPrimary")).collect(Collectors.toList());
                    query += " WHERE ";
                    for(int i = 0; i < primarias.size(); i++){
                        HashMap<String, Object> valor = primarias.get(i);

                        query += valor.get("nombreColumna")+" = '"+valor.get("valor")+"'";

                        if(!(i + 1 >= primarias.size())){
                            query += " AND ";
                        }
                    }
                }else{
                    return;
                }

                //Tenemos la query de la consulta, podemos proceder

                ArrayList<HashMap<String, Object>> resultadoDB = this.controller.get(query);

                //Tenemos los datos
                //Esta guardado
                if(resultadoDB.size() == 0){
                    this.guardado = false;
                }else{
                    this.guardado = true;
                }

                for(HashMap<String, Object> mapa : resultadoDB){
                    for(int indexMetodo = 0; indexMetodo < metodos.length; indexMetodo++){
                        Method metodoActual = metodos[indexMetodo];
                        if(metodoActual.isAnnotationPresent(CampoSetter.class)){
                            //Sabemos que es un metodo para hacer un set
                            Annotation annotation = metodoActual.getAnnotation(CampoSetter.class);
                            //Sabemos que solo tiene un metodo la anotacion por lo que no la recorremos con un bucle
                            Method nombreColumnaMethod = annotation.annotationType().getMethod("nombreColumna");
                            String nombreColumna = (String) nombreColumnaMethod.invoke(annotation);

                            if(mapa.containsKey(nombreColumna)){
                                //Es la misma columna
                                //Le damos su valor :)
                                metodoActual.invoke(this, mapa.get(nombreColumna));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            this.guardado = false;
        }
    }

    private ArrayList<HashMap<String, Object>> getValores(){
        ArrayList<HashMap<String, Object>> valoresGet = new ArrayList<>();
        try {
            Class claseActual = this.getClass();
            Method metodoTabla = claseActual.getAnnotation(Guardable.class).annotationType().getMethod("nombreTabla");
            String nombreTabla = (String)metodoTabla.invoke(claseActual.getAnnotation(Guardable.class));

            Method[] metodos = claseActual.getMethods();
            for(int indexMetodo = 0; indexMetodo < metodos.length; indexMetodo++){
                //Como vamos a tener multiples bucles for anidados es mejor identificar bien los indexes
                Method metodo = metodos[indexMetodo];
                if(metodo.isAnnotationPresent(CampoGetter.class)){
                    //Sabemos que es un metodo getter
                    Annotation anotacion = metodo.getAnnotation(CampoGetter.class);
                    //Tenemos la informacion que requerimos
                    Class tipoAnotacion = anotacion.annotationType();
                    HashMap<String, Object> valoresAnotacion = new HashMap<>();
                    Method[] metodosAnotacion = tipoAnotacion.getMethods();
                    for(int indexAnotacion = 0; indexAnotacion < metodosAnotacion.length-4; indexAnotacion++){
                        Method metodoAnotacion = metodosAnotacion[indexAnotacion];
                        valoresAnotacion.put(metodoAnotacion.getName(), metodoAnotacion.invoke(anotacion));
                    }

                    //Ya hemos ejecutado los metodos de la anotacion, ahora sacamos el valor del metodo en si
                    valoresAnotacion.put("valor", metodo.invoke(this));
                    valoresGet.add(valoresAnotacion);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return valoresGet;
    }

    private String getNombreTabla(){
        String resultado = "";
        try {
            Class claseActual = this.getClass();
            Method metodoTabla = claseActual.getAnnotation(Guardable.class).annotationType().getMethod("nombreTabla");
            String nombreTabla = (String)metodoTabla.invoke(claseActual.getAnnotation(Guardable.class));
            resultado = nombreTabla;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
}
