package org.darkrpa.discord.bots.june.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.model.EnvOption;
import org.darkrpa.discord.bots.june.model.UserNivel;

import net.dv8tion.jda.api.entities.User;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Clase ImageEditor, esta es una de las clases más cruciales de la aplicacion pues va a ser la clase
 * que nos va a permitir realizar edición de imagenes con respecto a unas plantillas
 *
 * @author Daniel Caparros Duran
 * @version 1.0
 * @since 1.0
 */
public class ImageEditor {
    public static final int MAX_WIDTH_PROGRESS_BAR = 430;
    public static final int MAX_HEIGHT_PROGRESS_BAR = 58;

    private BufferedImage imagen;
    private Graphics graficos;

    public ImageEditor(File imagen){
        try {
            this.imagen = ImageIO.read(imagen);
            this.graficos = this.imagen.getGraphics();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageEditor addText(String texto, int x, int y){
        this.graficos.drawString(texto, x, y);
        return this;
    }

    public ImageEditor setDefaultColor(){
        EnvOption red = Main.getOption(EnvOption.EMBED_COLOR_RED);
        EnvOption green = Main.getOption(EnvOption.EMBED_COLOR_GREEN);
        EnvOption blue = Main.getOption(EnvOption.EMBED_COLOR_BLUE);

        this.graficos.setColor(new Color(Integer.parseInt(red.getValor()), Integer.parseInt(green.getValor()), Integer.parseInt(blue.getValor())));
        return this;
    }

    public ImageEditor setFont(Font font){
        this.graficos.setFont(font);
        return this;
    }

    public ImageEditor drawImage(Image imagen, int x, int y){
        this.graficos.drawImage(imagen, x, y, null);
        return this;
    }

    public File saveImagen(){
        File fichero = new File("");
        try{
            File imagenGuardada = File.createTempFile("june_", ".png");
            this.imagen.getGraphics().dispose();
            ImageIO.write(this.imagen, "png", imagenGuardada);
            fichero = imagenGuardada;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fichero;
    }


    public static ImageEditor generateDefaultNivelTemplate(UserNivel userNivel, User usuario){
        ImageEditor editor = new ImageEditor(new File("images/levels/imagen-nivel.png"));
        try {
            editor.setDefaultColor();
            BufferedImage avatar = ImageIO.read(new URL(usuario.getAvatarUrl()+"?size=128"));
            //Tenemos el avatar por lo que podemos seguir
            BufferedImage imagenFinal = ImageIO.read(new File("images/levels/imagen-nivel.png"));
            editor.drawImage(avatar, 26, 30);

            //Porcentaje
            int mensajes = userNivel.getMensajes();
            int nivel = (int) Math.floor(0.75*Math.sqrt(mensajes));
            double porcentaje = 100 - Math.floor(((0.75*Math.sqrt(mensajes) - Math.ceil(0.75*Math.sqrt(mensajes)))*-1)*100);
            if(porcentaje >= 100){
                porcentaje = 0;
            }
            int porcentajeDeBarra = (int) Math.ceil(((ImageEditor.MAX_WIDTH_PROGRESS_BAR/100F)*porcentaje));
            editor.graficos.fillRect(35, 210, porcentajeDeBarra, ImageEditor.MAX_HEIGHT_PROGRESS_BAR);
            editor.drawImage(imagenFinal, 0, 0);
            editor.setFont(new Font("Sans-serif", Font.BOLD, 24));
            editor.addText("Nivel: "+nivel, 45, 194);
            editor.setFont(new Font("Sans-serif", Font.PLAIN, 24));
            editor.graficos.setColor(Color.white);
            editor.addText(porcentaje+"%", 59, 245);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return editor;
    }

}
