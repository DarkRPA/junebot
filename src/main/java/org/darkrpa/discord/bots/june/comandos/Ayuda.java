package org.darkrpa.discord.bots.june.comandos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.darkrpa.discord.bots.june.model.HelpCategory;
import org.darkrpa.discord.bots.june.model.HelpOption;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Comando encargado de poder mostrar la ayuda
 */
public class Ayuda implements Comando{

    //Para no tener que interpretar la ayuda cada vez que se ejecute el comando ayuda vamos a almacenarla de forma
    //estatica, asi todas las ayudas accederan al mismo array

    private static ArrayList<HelpCategory> categories;
    private static ArrayList<HelpOption> comandos;

    static{
        //Cargamos las ayudas
        Ayuda.categories = new ArrayList<>();
        Ayuda.comandos = new ArrayList<>();
        Ayuda.cargarAyuda();
    }

    @Override
    public void ejecutar(GenericEvent evento) {
        //Ayuda cargada, podemos crear, debemos de enviar un mensaje
        if(evento instanceof MessageReceivedEvent){
            MessageReceivedEvent eventoReal = (MessageReceivedEvent) evento;
            MessageChannel canal = eventoReal.getChannel();

        }else{
            return;
        }
    }

    private static void cargarAyuda(){
        //Este comando se encargará de cargar toda la ayuda en los respectivos arrays
        File archivoAyuda = new File("HELP.txt");
        if(!archivoAyuda.exists()){
            //Error critico, no existe el fichero de ayuda
            return;
        }
        //Tenemos el fichero de ayuda, ahora debemos de interpretarlo
        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivoAyuda));
            String linea = "";
            while((linea = lector.readLine()) != null){
                if(linea.startsWith("#") || linea.isEmpty()) continue;
                //Tenemos una linea valida
                if(linea.startsWith("ctg")){
                    //Es una categoria
                    Ayuda.categories.add(Ayuda.interpretarCategoria(linea));
                }else if(linea.startsWith("cmd")){
                    Ayuda.comandos.add(Ayuda.interpretarComando(linea));
                }
            }
            lector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HelpCategory interpretarCategoria(String linea){
        //Esperamos recibir una linea con formato de categoria
        StringTokenizer tokens = new StringTokenizer(linea, ":");
        ArrayList<String> valores = new ArrayList<>();
        //Movemos el cursor en 1 para posicionarlo despues de ctg
        tokens.nextToken();
        while(tokens.hasMoreTokens()){
            valores.add(tokens.nextToken());
        }
        //Ya tenemos los tokens ahora hacemos el objeto
        HelpCategory categoria = new HelpCategory(valores.get(0), valores.get(1), valores.get(2));
        return categoria;
    }

    private static HelpOption interpretarComando(String linea){
        StringTokenizer tokens = new StringTokenizer(linea, ":");
        ArrayList<String> valores = new ArrayList<>();
        //Movemos el cursor en 1 para posicionarlo despues de cmd
        tokens.nextToken();
        while(tokens.hasMoreTokens()){
            valores.add(tokens.nextToken());
        }
        //Ya tenemos los tokens ahora hacemos el objeto
        //Teoricamente los comandos se van a cargar despues de las categorias por lo que no deberia de haber
        //problemas enlanzandolos
        HelpOption comando = new HelpOption(valores.get(0), valores.get(1), valores.get(2));
        if(valores.size() < 3){
            return null;
        }else{
            //Sabemos que si o si tiene mas de 3 valores por lo que asumimos que el resto despues del ultimos va
            //a ser las categorias
            for(int i = 3; i < valores.size(); i++){
                String valor = valores.get(i);
                //Teoricamente es una categoria
                boolean categoriaEncontrada = false;
                for(HelpCategory categoria : Ayuda.categories){
                    if(categoria.getIdCategoria().equals(valor)){
                        //Es la categoria que busca
                        comando.addCategoria(categoria);
                        categoriaEncontrada = true;
                    }
                }
                if(!categoriaEncontrada){
                    //No tiene categoria
                    HelpCategory categoryApoyo = new HelpCategory("Categoria predeterminada", "Cuando un comando no tiene categoria o su categoria no ha sido encontrada se colocara aqui", "-1");
                    comando.addCategoria(categoryApoyo);
                }
            }
        }
        return comando;
    }

}