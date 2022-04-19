package org.darkrpa.discord.bots.june.comandos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Locale.Category;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.model.EnvOption;
import org.darkrpa.discord.bots.june.model.HelpCategory;
import org.darkrpa.discord.bots.june.model.HelpOption;
import org.darkrpa.discord.bots.june.model.MensajeAyudaEfimero;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu.Builder;

/**
 * Comando encargado de poder mostrar la ayuda
 */
public class Ayuda implements Comando{

    //Para no tener que interpretar la ayuda cada vez que se ejecute el comando ayuda vamos a almacenarla de forma
    //estatica, asi todas las ayudas accederan al mismo array

    private static ArrayList<HelpCategory> categories;
    private static ArrayList<HelpOption> comandos;

    private String idMensaje;

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
            //Vamos a enviarle un embed
            String nombreLogo = Main.getOption(EnvOption.BOT_ICON).getValor();
            File logo = new File(nombreLogo);

            EmbedCreator designer = EmbedCreator.generateDefaultTemplate();
            designer.title("Ayuda.");
            designer.description("Comando de ayuda");
            designer.thumbnail(String.format("attachment://%s", logo));
            designer.addField("Descripción", "Utiliza el desplegable abajo de este mensaje para poder navegar por las diferentes categorias y comandos");
            MessageEmbed embed = designer.build();

            canal.sendMessageEmbeds(embed).addFile(logo).queue((mensaje)->{
                //Tenemos el mensaje, ahora le añadimos el buscador
                //Debemos de cargar en el menu todas las opciones de las categorias
                Message mensajeReal = mensaje;
                ActionRow layout = null;
                Builder selectMenuBuilder = SelectMenu.create("menu_ayuda_general");
                //Tenemos que tambien poner una opcion de ir hacia atras
                for(HelpCategory categoria : Ayuda.categories){
                    selectMenuBuilder.addOption(categoria.getNombreCategoria(), categoria.getIdCategoria());
                }
                SelectMenu menuListo = selectMenuBuilder.build();
                layout = ActionRow.of(menuListo);
                mensajeReal.editMessageComponents(layout).queue();

                //Añadimos el mensaje a la lista de timers
                MensajeAyudaEfimero mensajeTimer = new MensajeAyudaEfimero(mensajeReal);
                Main.getTimerVerifier().anadirOpcion(mensajeTimer);

            });

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

    /**
     * Este metodo se va aencargar de buscar un comando en base a un nombre dado
     * @param nombre
     * @return
     */
    public static HelpOption buscarComando(String nombre){
        for(HelpOption comando : Ayuda.comandos){
            if(comando.getNombreComando().equals(nombre.toLowerCase())){
                return comando;
            }
        }
        return null;
    }

    public static List<HelpOption> getComandosEnCategoria(HelpCategory categoria){
        return Ayuda.comandos.stream().filter(e -> e.getCategories().contains(categoria)).collect(Collectors.toList());
    }

    public static HelpCategory buscarCategory(String categoria){
        return Ayuda.categories.stream().filter(e->e.getNombreCategoria().equals(categoria)).collect(Collectors.toList()).get(0);
    }

    public static ArrayList<HelpCategory> getCategories(){
        return Ayuda.categories;
    }

    public static ArrayList<HelpOption> getComandos(){
        return Ayuda.comandos;
    }
}
