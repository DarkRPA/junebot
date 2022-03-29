package org.darkrpa.discord.bots.june;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.security.auth.login.LoginException;

import org.darkrpa.discord.bots.june.exceptions.EnvFileDoesntExistException;
import org.darkrpa.discord.bots.june.model.EnvOption;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

/**
 * Hello world!
 */
public final class Main {
    private static ArrayList<EnvOption> listaOpciones;

    //Ahora que ya tenemos cargada la lista de opciones y el funcionamiento basico para recoger opciones especificas
    //tenemos que comenzar a crear el funcionamiento basico del bot

    static{
        try {
            Main.listaOpciones = Main.readEnvFile();
        } catch (EnvFileDoesntExistException | IOException e) {
            e.printStackTrace();
        }
    }

    private JDA bot;

    private Main() throws LoginException, IllegalArgumentException {
        JDABuilder consBuilder = JDABuilder.createDefault(Main.getOption(EnvOption.DISCORD_TOKEN).getValor());
        this.bot = consBuilder.build();
    }

    public static void main(String[] args) throws LoginException, IllegalArgumentException {
        Main main = new Main();
    }

    //Debemos de hacer un metodo que se encargue de leer el archivo .env en el que tenemos
    //toda la configuracion de conexion y demas

    public static ArrayList<EnvOption> readEnvFile() throws EnvFileDoesntExistException, IOException{
        //En principio el archivo env va a estar siempre en la carpeta raiz de la aplicacion
        //por lo que no hace falta que recibamos nada como parametro
        File fichero = new File(".env");
        ArrayList<EnvOption> listaResultado = new ArrayList<>();
        if(fichero.exists()){
            //sabemos que existe el fichero por lo que podemos comenzar a leer sus opciones
            BufferedReader lector = new BufferedReader(new FileReader(fichero));
            String linea = "";
            while((linea = lector.readLine()) != null){
                //tenemos la linea por lo que ahora hacemos un objeto del tipo
                StringTokenizer tokens = new StringTokenizer(linea, "=");
                //Ya tenemos el token por lo que ahora agarramos realmente el valor
                String titulo = tokens.nextToken();
                String valor = tokens.nextToken();

                EnvOption opcion = new EnvOption(titulo, valor);
                listaResultado.add(opcion);
            }
            lector.close();
        }else{
            throw new EnvFileDoesntExistException("Env file is not properly set");
        }

        return listaResultado;
    }

    public static EnvOption getOption(String opcion){
        //En principio la lista de opciones ya esta cargada por lo que ahora simplemente buscamos la opcion que necesitamos

        for(EnvOption opcionObtenida : Main.listaOpciones){
            if(opcionObtenida.getNombre().equals(opcion)){
                return opcionObtenida;
            }
        }

        //No se ha encontrado
        return null;
    }
}
