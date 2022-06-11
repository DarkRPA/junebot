package org.darkrpa.discord.bots.june.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.model.EnvOption;
import org.darkrpa.discord.bots.june.model.UserNivel;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
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
    public static final int MAX_WIDTH_PROGRESS_BAR = 620;
    public static final int MAX_HEIGHT_PROGRESS_BAR = 45;

    public static final int AVATAR_X = 58;
    public static final int AVATAR_Y = 64;
    public static final int PROGRESS_X = 261;
    public static final int PROGRESS_Y = 163;

    public static final int NAME_X = 269;
    public static final int NAME_Y = 96;

    public static final int TAG_X = 415;
    public static final int TAG_Y = 98;

    public static final int LVL_X = 269;
    public static final int LVL_Y = 148;

    public static final int EXP_X = 703;
    public static final int EXP_Y = 148;


    private BufferedImage imagen;
    private Graphics2D graficos;

    public ImageEditor(File imagen){
        try {
            this.imagen = ImageIO.read(imagen);
            this.graficos = (Graphics2D) this.imagen.getGraphics();
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

    public ImageEditor draw(Shape forma){
        this.graficos.draw(forma);
        return this;
    }

    public ImageEditor fill(Shape forma){
        this.graficos.fill(forma);
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

    public ImageEditor setColor(Color color){
        this.graficos.setColor(color);
        return this;
    }

    public static ImageEditor generateDefaultWelcomeTemplate(User usuario, Guild server){
//ImageEditor editor = new ImageEditor(new File("images/levels/PrimeraCapaNivelV2Default.png"));
        //Debemos de saber si tiene una imagen de nivel establecida, sino le ponemos la imagen común predeterminada

        File imagenElegida = new File("images/BienvenidasV1.png");

        ImageEditor editor = new ImageEditor(imagenElegida);

        try {
            editor.setDefaultColor();
            BufferedImage avatarObtenido = ImageIO.read(new URL(usuario.getAvatarUrl()+"?size=256"));
            //Tenemos el avatar por lo que podemos seguir, aun asi vamos a intentar recortar el avatar para que sea totalmente
            //redondo y no cuadrado


            BufferedImage avatar = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graficosAvatar = avatar.createGraphics();
            graficosAvatar.setClip(new Ellipse2D.Float(0, 0, 256, 256));
            graficosAvatar.drawImage(avatarObtenido, 0, 0, 256, 256, null);

            editor.drawImage(avatar, 412, 62);

            //Porcentaje
            Color colorTag = new Color(255, 219, 194);

            Font fontName = new Font("Sans-serif", Font.BOLD, 40);

            String username = usuario.getAsTag();
            StringTokenizer tokens = new StringTokenizer(username, "#");
            tokens.nextToken();



            String textoFinal = "¡Bienvenido "+username+" a "+server.getName()+"!";


            editor.setColor(colorTag);
            editor.setFont(fontName);
            FontRenderContext contextoRenderizado = new FontRenderContext(new AffineTransform(), true, true);

            double tamanioString = fontName.getStringBounds(textoFinal, contextoRenderizado).getWidth();

            double posicion = (1080-tamanioString)/2;

            editor.addText(textoFinal, (int)Math.ceil(posicion), 450);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return editor;
    }

    public static ImageEditor generateDefaultNivelTemplate(UserNivel userNivel, User usuario){
        //ImageEditor editor = new ImageEditor(new File("images/levels/PrimeraCapaNivelV2Default.png"));
        //Debemos de saber si tiene una imagen de nivel establecida, sino le ponemos la imagen común predeterminada

        File imagenElegida = null;
        boolean desdeRed = false;
        if(userNivel.getTarjetaNivel().equals("default")){
            //No tiene imagen predeterminada
            imagenElegida = new File("images/levels/PrimeraCapaNivelV2Default.png");
        }else{
            //Si tenemos la imagen, la vamos a guardar de forma temporal solo para enviarla y luego la eliminamos
            //Asumimos que lo que está guardado en la tarejetaNivel es la URL a la imagen
            try {
                URL urlImagen = new URL(userNivel.getTarjetaNivel());
                //Vamos a abrir el stream y vamos a guardar el archivo, teoricamente todas las imagenes son recortadas para
                //caber bien en el marco, 950x256
                BufferedImage imagenBuffered = ImageIO.read(urlImagen);
                imagenElegida = File.createTempFile("june_", "png");
                ImageIO.write(imagenBuffered, "png", imagenElegida);
                desdeRed = true;
            } catch (IOException e) {
                e.printStackTrace();
                imagenElegida = new File("images/levels/PrimeraCapaNivelV2Default.png");
            }
        }

        ImageEditor editor = new ImageEditor(imagenElegida);

        try {
            editor.setDefaultColor();
            BufferedImage avatarObtenido = ImageIO.read(new URL(usuario.getAvatarUrl()+"?size=128"));
            //Tenemos el avatar por lo que podemos seguir, aun asi vamos a intentar recortar el avatar para que sea totalmente
            //redondo y no cuadrado


            BufferedImage avatar = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graficosAvatar = avatar.createGraphics();
            graficosAvatar.setClip(new Ellipse2D.Float(0, 0, 128, 128));
            graficosAvatar.drawImage(avatarObtenido, 0, 0, 128, 128, null);


            File imagenFinalFichero = null;

            if(desdeRed){
                //Es una imagen de la red
                imagenFinalFichero = new File("images/levels/CapaFinal_Trasparente.png");
            }else{
                //no es una imagen de la red por lo que ponemos la predeterminada
                imagenFinalFichero = new File("images/levels/PrimeraCapaNivelV2Default.png");
            }

            BufferedImage imagenFinal = ImageIO.read(imagenFinalFichero);
            editor.drawImage(avatar, ImageEditor.AVATAR_X, ImageEditor.AVATAR_Y);

            //Porcentaje
            int mensajes = userNivel.getMensajes();
            int nivel = (int) Math.floor(0.75*Math.sqrt(mensajes));
            double porcentaje = 100 - Math.floor(((0.75*Math.sqrt(mensajes) - Math.ceil(0.75*Math.sqrt(mensajes)))*-1)*100);
            if(porcentaje >= 100){
                porcentaje = 0;
            }
            int porcentajeDeBarra = (int) Math.ceil(((ImageEditor.MAX_WIDTH_PROGRESS_BAR/100F)*porcentaje));
            RoundRectangle2D recta = new RoundRectangle2D.Double(ImageEditor.PROGRESS_X, ImageEditor.PROGRESS_Y, porcentajeDeBarra, ImageEditor.MAX_HEIGHT_PROGRESS_BAR, 50, 50);

            //editor.graficos.fillRect(35, 210, porcentajeDeBarra, ImageEditor.MAX_HEIGHT_PROGRESS_BAR);
            editor.graficos.fill(recta);
            editor.drawImage(imagenFinal, 0, 0);
            Color colorTag = new Color(146, 126, 111);
            Color white = Color.WHITE;
            Color nivelExp = new Color(204, 140, 94);

            Font fontName = new Font("Sans-serif", Font.PLAIN, 32);
            Font fontTag = new Font("Sans-serif", Font.PLAIN, 24);
            Font fontNivel = new Font("Sans-serif", Font.PLAIN, 28);

            String username = usuario.getAsTag();
            StringTokenizer tokens = new StringTokenizer(username, "#");
            tokens.nextToken();
            String tag = tokens.nextToken();

            editor.setColor(white);
            editor.setFont(fontName);
            editor.addText(usuario.getName(), ImageEditor.NAME_X, ImageEditor.NAME_Y);
            editor.setColor(colorTag);
            editor.setFont(fontTag);
            editor.addText("#"+tag, ImageEditor.TAG_X, ImageEditor.TAG_Y);
            editor.setColor(nivelExp);
            editor.setFont(fontNivel);
            editor.addText("Lvl. "+nivel, ImageEditor.LVL_X, ImageEditor.LVL_Y);
            editor.addText(userNivel.getMensajes() + " / "+userNivel.getExpToNextLvl()+" EXP", ImageEditor.EXP_X, ImageEditor.EXP_Y);

            //Una vez subidas las borramos

            if(desdeRed){
                //Eliminamos solo cuando proviene de la red
                imagenElegida.delete();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return editor;
    }

}
