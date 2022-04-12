package org.darkrpa.discord.bots.june.events;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.darkrpa.discord.bots.june.comandos.Ayuda;
import org.darkrpa.discord.bots.june.model.HelpOption;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu.Builder;

public class AyudaMenuListener extends AbstractEventListener{

    public AyudaMenuListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent o) {
        if(o instanceof SelectMenuInteractionEvent){
            SelectMenuInteractionEvent evento = (SelectMenuInteractionEvent)o;
            //Debemos de saber que el menu que se ha seleccionado es el menu de la ayuda, sino
            //Tenemos que dejarlo ir sin hacer nada
            SelectMenu menu = evento.getSelectMenu();
            if(menu.getId().startsWith("menu_ayuda")){
                Message mensaje = evento.getMessage();
                ArrayList<HelpOption> comandos = Ayuda.getComandos();
                if(menu.getId().equals("menu_ayuda_general")){
                    //Sabemos que es el menu de la ayuda por lo que podemos seguir con la operacion
                    //Y que concretamente es el general por lo que ahora hay que cargar el menu de la categoria
                    //seleccionada
                    //Guardaremos el menu de back para poder ir hacia atras
                    String nombreCategoria = evento.getSelectedOptions().get(0).getLabel();
                    String categoriaSeleccionada = evento.getSelectedOptions().get(0).getValue();
                    List<HelpOption> comandosConCategoria = comandos.stream().filter(e->e.hasCategory(categoriaSeleccionada)).collect(Collectors.toList());

                    //Debemos de hacer el embed con

                    EmbedCreator defaultEmbed = EmbedCreator.generateDefaultTemplate();
                    String aMostrar = "```";

                    for(HelpOption comando : comandosConCategoria){
                        aMostrar += "["+comando.getNombreComando()+"] ";
                    }

                    aMostrar += "```";

                    defaultEmbed.title(nombreCategoria);
                    defaultEmbed.thumbnail(EmbedCreator.getLogoURL());
                    defaultEmbed.addField("Comandos: ", aMostrar);

                    mensaje.editMessageEmbeds(defaultEmbed.build()).queue();

                    //Ahora debemos de poner el nuevo menu
                    ActionRow layout;
                    Builder builder = SelectMenu.create("menu_ayuda_categoria");
                    for(HelpOption comando : comandosConCategoria){
                        builder.addOption(comando.getNombreComando(), comando.getNombreComando());
                    }
                    layout = ActionRow.of(builder.build());
                    evento.editComponents(layout).queue();
                }else if(menu.getId().equals("menu_ayuda_categoria")){

                    SelectOption seleccionado = evento.getSelectedOptions().get(0);
                    String nombre = seleccionado.getLabel();
                    HelpOption comando = comandos.stream().filter(e->e.getNombreComando().equals(nombre)).collect(Collectors.toList()).get(0);
                    //tenemos el comando por lo que podemos editar el embed para mostrar su informacion

                    EmbedCreator creator = EmbedCreator.generateDefaultTemplate();
                    creator.title(comando.getNombreComando());
                    creator.thumbnail(EmbedCreator.getLogoURL());
                    creator.addField("Descripcion", comando.getDescripcion());
                    creator.addField("Como usar:", comando.getCasoDeUso());
                    mensaje.editMessageEmbeds(creator.build()).queue();
                    evento.editComponents().queue();
                }
            }
        }
    }

}
