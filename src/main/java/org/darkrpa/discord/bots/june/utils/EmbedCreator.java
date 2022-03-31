package org.darkrpa.discord.bots.june.utils;

import java.awt.Color;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.model.EnvOption;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * Esta clase se va a encargar de poder crear mensajes con embeds incluidos
 * la haremos de manera que conectando metodos podamos crear mensajes con embeds de forma dinamica
 * y sencilla
 *
 * @author Daniel Caparros Duran
 * @version 1.0
 * @since 1.0
 */
public class EmbedCreator {
    private MessageEmbed embedBuilt;
    private EmbedBuilder builder;

    public EmbedCreator(){
        this.builder = new EmbedBuilder();
    }

    public EmbedCreator title(String titulo){
        this.builder.setTitle(titulo);
        return this;
    }

    public EmbedCreator thumbnail(String imgUrl){
        this.builder.setThumbnail(imgUrl);
        return this;
    }

    public EmbedCreator addField(String nombre, String texto){
        return this.addField(nombre, texto, false);
    }

    public EmbedCreator addField(String nombre, String texto, boolean inline){
        this.builder.addField(nombre, texto, inline);
        return this;
    }

    public EmbedCreator description(String description){
        this.builder.setDescription(description);
        return this;
    }

    public EmbedCreator footer(String footer){
        return this.footer(footer, null);
    }

    public EmbedCreator footer(String footer, String icono){
        if(icono == null){
            this.builder.setFooter(footer);
            return this;
        }else{
            this.builder.setFooter(footer, icono);
            return this;
        }
    }

    public EmbedCreator setColor(Color color){
        this.builder.setColor(color);
        return this;
    }

    public EmbedCreator setColor(int red, int green, int blue){
        this.builder.setColor(new Color(red, green, blue));
        return this;
    }

    public MessageEmbed build(){
        this.embedBuilt = this.builder.build();
        return this.embedBuilt;
    }

    public static EmbedCreator generateDefaultTemplate(){
        //Este metodo de lo que se va a encargar es de generar la platilla del embed automaticamente
        //De esta forma toda la aplicacion usa la misma plantilla y no ocurre como en el proyecto de El Tiempo
        //donde por no tener una generalizacion habia partes que no eran iguales entre si

        EnvOption red, green, blue, botName, botVersion;

        red = Main.getOption(EnvOption.EMBED_COLOR_RED);
        green = Main.getOption(EnvOption.EMBED_COLOR_GREEN);
        blue = Main.getOption(EnvOption.EMBED_COLOR_BLUE);

        botName = Main.getOption(EnvOption.BOT_NAME);
        botVersion = Main.getOption(EnvOption.BOT_VERSION);

        //Tenemos las variables de entorno, podemos proseguir

        EmbedCreator creator = new EmbedCreator();

        creator.setColor(Integer.parseInt(red.getValor()), Integer.parseInt(green.getValor()), Integer.parseInt(blue.getValor()));
        creator.footer(String.format("%s - v%s", botName.getValor(), botVersion.getValor()));
        return creator;
    }

    public static String getLogoURL(){
        return "attachment://"+Main.getOption(EnvOption.BOT_ICON).getValor();
    }
}
