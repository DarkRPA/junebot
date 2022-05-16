package org.darkrpa.discord.bots.june.events;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.darkrpa.discord.bots.june.comandos.AbrirTicket;
import org.darkrpa.discord.bots.june.comandos.Ayuda;
import org.darkrpa.discord.bots.june.comandos.CerrarTicket;
import org.darkrpa.discord.bots.june.comandos.Comando;
import org.darkrpa.discord.bots.june.comandos.Nivel;
import org.darkrpa.discord.bots.june.model.Usuario;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
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
    public static String COMMAND_REGEX = "^!([a-zA-Z]*)(( <[!@&a-zA-Z1-9]*>)+( \\d+[mdsMA]{1})?( [a-zA-Z1-9¿?¡!*+-\\/ñáóéíú():;,.\\[\\]<> ]*)?)?([a-zA-Z¿?¡!*+-\\/ñáóéíú1-9():;,.\\[\\]<> ]*)";
    private final HashMap<String, Class> COMMAND_CLASS_MAP = new HashMap<>();


    {
        this.COMMAND_CLASS_MAP.put("ayuda", Ayuda.class);
        this.COMMAND_CLASS_MAP.put("nivel", Nivel.class);
        this.COMMAND_CLASS_MAP.put("abrirTicket", AbrirTicket.class);
        this.COMMAND_CLASS_MAP.put("cerrarTicket", CerrarTicket.class);

    }

    public CommandListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof MessageReceivedEvent){

            //Es un mensaje, por lo que ya de paso vamos a comprobar si existe en la base de datos
            //Si no, lo creamos

            MessageReceivedEvent eventoReal = (MessageReceivedEvent) event;

            Guild servidor = eventoReal.getGuild();
            Member miembro = eventoReal.getMember();

            Message mensaje = eventoReal.getMessage();
            //Tenemos el mensaje, podemos observar si realmente es valido
            Pattern patron = Pattern.compile(CommandListener.COMMAND_REGEX);
            Matcher matcher = patron.matcher(mensaje.getContentRaw());
            if(matcher.matches()){
                Usuario usuario = new Usuario(miembro.getId());
                usuario.setNombreUsuario(miembro.getUser().getName());
                usuario.actualizar();

                //Es un comando valido
                String comando = matcher.group(1).toLowerCase();
                Class claseComando = null;
                String nombreComando = "";
                Set<String> entries = this.COMMAND_CLASS_MAP.keySet();

                for(String key : entries){
                    if(key.equalsIgnoreCase(comando)){
                        claseComando = this.COMMAND_CLASS_MAP.get(key);
                        nombreComando = key;
                        break;
                    }
                }

                if(claseComando == null){
                    //El comando no se ha encontrado
                    return;
                }

                //Instanciamos la clase

                try {
                    Comando comandoReal = (Comando) claseComando.getConstructors()[0].newInstance(nombreComando, matcher);
                    //Verificamos que puede ejecutar el comando

                    if(comandoReal.puedeEjecutar(servidor, miembro)){
                        comandoReal.ejecutar(eventoReal);
                    }else{
                        EmbedCreator creator = EmbedCreator.generateDefaultTemplate();
                        creator.description("No puede ejecutar este comando");
                        mensaje.replyEmbeds(creator.build()).queue();
                    }

                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
