package org.darkrpa.discord.bots.june.events;

import java.io.File;
import java.util.List;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.comandos.Ayuda;
import org.darkrpa.discord.bots.june.model.EnvOption;
import org.darkrpa.discord.bots.june.model.HelpCategory;
import org.darkrpa.discord.bots.june.model.HelpOption;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
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
            Message mensaje = evento.getMessage();
            SelectOption opcionSeleccionada = evento.getSelectedOptions().get(0);

            if(menu.getId().equals("menu_ayuda_general")){
                //Sabemos que esta en el menu principal por lo que debemos de cargar la vista de la categoria
                HelpCategory categoria = Ayuda.buscarCategory(opcionSeleccionada.getLabel());
                //Tenemos la categoria por lo que generamos tanto el embed como el menu
                MessageEmbed mensajeCategoria = this.getCategoriaEmbed(categoria);
                Builder constructor = this.getMenuCategoria(categoria);

                //Editamos el mensaje

                mensaje.editMessageEmbeds(mensajeCategoria).queue(mensajeResultante -> {
                    evento.editComponents(ActionRow.of(constructor.build())).queue();
                });
            }else if(menu.getId().equals("menu_ayuda_categoria")){
                //Sabemos que esta en el menu principal por lo que debemos de cargar la vista de la categoria

                if(opcionSeleccionada.getValue().toLowerCase().equals("volver")){
                    //Sabemos que quiere volver
                    MessageEmbed embed = this.getGeneralEmbed();
                    Builder constructor = this.getMenuGeneral();

                    mensaje.editMessageEmbeds(embed).queue(mensajeResultante -> {
                        evento.editComponents(ActionRow.of(constructor.build())).queue();
                    });

                    return;
                }

                HelpOption comando = Ayuda.buscarComando(opcionSeleccionada.getLabel());
                //Tenemos la categoria por lo que generamos tanto el embed como el menu
                MessageEmbed mensajeComando = this.getComandoEmbed(comando);
                Builder constructor = this.getMenuGeneral();

                //Editamos el mensaje

                mensaje.editMessageEmbeds(mensajeComando).queue(mensajeResultante -> {
                    evento.editComponents(ActionRow.of(constructor.build())).queue();
                });
            }
        }
    }

    public Builder getMenuGeneral(){
        //Debemos de crear el menu general aqui, para ellos cogeremos todas las categorias y las pondremos
        //en un menu

        Builder selectMenuBuilder = SelectMenu.create("menu_ayuda_general");
                //Tenemos que tambien poner una opcion de ir hacia atras
        for(HelpCategory categoria : Ayuda.getCategories()){
            selectMenuBuilder.addOption(categoria.getNombreCategoria(), categoria.getIdCategoria());
        }

        return selectMenuBuilder;
    }

    public Builder getMenuCategoria(HelpCategory categoria){

        List<HelpOption> listaComandosCategoria = Ayuda.getComandosEnCategoria(categoria);

        Builder constructor = SelectMenu.create("menu_ayuda_categoria");

        constructor.addOption("Volver", "Volver");

        for(HelpOption comando : listaComandosCategoria){
            //Tenemos los comandos, ahora los convertimos en menu y le ponemos un boton de atras
            constructor.addOption(comando.getNombreComando(), comando.getNombreComando());
        }

        return constructor;
    }

    public MessageEmbed getGeneralEmbed(){
        String nombreLogo = Main.getOption(EnvOption.BOT_ICON).getValor();
        File logo = new File(nombreLogo);

        EmbedCreator designer = EmbedCreator.generateDefaultTemplate();
        designer.title("Ayuda.");
        designer.description("Comando de ayuda");
        designer.thumbnail(String.format("attachment://%s", logo));
        designer.addField("Descripción", "Utiliza el desplegable abajo de este mensaje para poder navegar por las diferentes categorias y comandos");
        MessageEmbed embed = designer.build();
        return embed;
    }

    public MessageEmbed getCategoriaEmbed(HelpCategory category){
        List<HelpOption> listaComandos = Ayuda.getComandosEnCategoria(category);



        EmbedCreator designer = EmbedCreator.generateDefaultTemplate();

        String nombreLogo = Main.getOption(EnvOption.BOT_ICON).getValor();
        File logo = new File(nombreLogo);
        designer.thumbnail(String.format("attachment://%s", logo));

        designer.title(category.getNombreCategoria());
        designer.description(category.getDescripcionCategoria());


        String resultadoComandos = "";
        for(HelpOption comando : listaComandos){
            resultadoComandos += "`"+comando+"` ";
        }

        designer.addField("Comandos:", resultadoComandos);

        return designer.build();
    }

    public MessageEmbed getComandoEmbed(HelpOption comando){
        EmbedCreator designer = EmbedCreator.generateDefaultTemplate();

        String nombreLogo = Main.getOption(EnvOption.BOT_ICON).getValor();
        File logo = new File(nombreLogo);
        designer.thumbnail(String.format("attachment://%s", logo));

        designer.title(comando.getNombreComando());
        designer.description(comando.getDescripcion());
        designer.addField("Como usar:", comando.getCasoDeUso());

        return designer.build();
    }

}
