package org.darkrpa.discord.bots.june.events;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.darkrpa.discord.bots.june.comandos.Ayuda;
import org.darkrpa.discord.bots.june.comandos.Comando;
import org.darkrpa.discord.bots.june.comandos.Nivel;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Clase CommandListener, esta es la clase principal y la que se encargara de todo el manejo de los comandos
 * en el bot, esta clase lo que hace es recibir un mensaje de texto y si se verifica la sintaxis correcta
 * entonces se procede analizar el contenido. Se mirará si es un comando valido y en caso de que lo sea se instanciara
 * y se ejecutará su metodo ejecutar()
 */
public class CommandListener extends AbstractEventListener {
    public static String COMMAND_REGEX = "^!([a-z]*)(( <[!@&a-z1-9]*>)+( \\d+[mdsMA]{1})?( [a-z1-9 ]*)?)?([a-z ]*)";
    private final HashMap<String, Class> COMMAND_CLASS_MAP = new HashMap<>();


    {
        this.COMMAND_CLASS_MAP.put("ayuda", Ayuda.class);
        this.COMMAND_CLASS_MAP.put("nivel", Nivel.class);
    }

    public CommandListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof MessageReceivedEvent){
            MessageReceivedEvent eventoReal = (MessageReceivedEvent) event;
            Message mensaje = eventoReal.getMessage();
            //Tenemos el mensaje, podemos observar si realmente es valido
            Pattern patron = Pattern.compile(CommandListener.COMMAND_REGEX);
            Matcher matcher = patron.matcher(mensaje.getContentRaw());
            if(matcher.matches()){
                //Es un comando valido
                String comando = matcher.group(1).toLowerCase();
                Class claseComando = null;
                Set<String> entries = this.COMMAND_CLASS_MAP.keySet();

                for(String key : entries){
                    if(key.equals(comando)){
                        claseComando = this.COMMAND_CLASS_MAP.get(key);
                    }
                }

                if(claseComando == null){
                    //El comando no se ha encontrado
                    return;
                }

                //Instanciamos la clase

                try {
                    Comando comandoReal = (Comando) claseComando.getConstructors()[0].newInstance();
                    comandoReal.ejecutar(eventoReal);
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
