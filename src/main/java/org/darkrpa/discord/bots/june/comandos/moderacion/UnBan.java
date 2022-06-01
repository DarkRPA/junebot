package org.darkrpa.discord.bots.june.comandos.moderacion;

import java.time.Instant;
import java.util.List;
import java.util.regex.Matcher;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.logging.discord.events.ban.UnBanEvent;
import org.darkrpa.discord.bots.june.model.sanciones.LightSancion;
import org.darkrpa.discord.bots.june.model.sanciones.Sancion;
import org.darkrpa.discord.bots.june.thread.BansThreadController;
import org.darkrpa.discord.bots.june.utils.EmbedCreator;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class UnBan extends GenericModerationCommand{

    public UnBan(String nombre, Matcher matcher) {
        super(nombre, matcher);
    }

    @Override
    public void ejecutar(GenericEvent o) {
        if(o instanceof MessageReceivedEvent){
            //Teoricamente vamos a recibirlo de dos formas como una mencion o como el id solo
            MessageReceivedEvent evento = (MessageReceivedEvent)o;

            if(evento.getChannelType() == ChannelType.TEXT){
                //Vamos a comprobar que no tiene ninguna mencion
                Message mensaje = evento.getMessage();
                List<Member> menciones = mensaje.getMentionedMembers();
                String idUsuarioDesbanear = this.matcher.group(6).trim();

                boolean sonDigitos = true;

                if(idUsuarioDesbanear.isEmpty()){
                    sonDigitos = false;
                }

                for(int i = 0; i < idUsuarioDesbanear.length(); i++){
                    char caracter = idUsuarioDesbanear.charAt(i);
                    if(!Character.isDigit(caracter)){
                        sonDigitos = false;
                    }
                }

                EmbedCreator creator = EmbedCreator.generateDefaultTemplate();

                String finalDesbanear = "";

                if(menciones.size() == 0 && !sonDigitos){
                    //No ha especificado ningun usuario no esta bien definidio
                    creator.description("No se ha especificado ningún usuario para desbanear");
                    mensaje.replyEmbeds(creator.build()).queue();
                    return;
                }

                if(menciones.size() == 0){
                    finalDesbanear = idUsuarioDesbanear;
                }else{
                    finalDesbanear = menciones.get(0).getId();
                }

                Guild server = mensaje.getGuild();
                Instant instanteActual = Instant.now();

                //Tenemos que ir a la base de datos y eliminar el registro en el cual el usuario estaba baneado (el registro y los registros en caso de que por algun casual hayan puesto más de uno)

                int resultado = Main.getMySQLController().execute(String.format("UPDATE bans SET fechaVencimiento = '-1' WHERE idServidor = '%s' AND idUsuarioSancionado = '%s' AND fechaVencimiento >= '%d' AND (idEvento = '4' || idEvento = '5')", server.getId(), finalDesbanear, instanteActual.toEpochMilli()));
                if(resultado >= 1){
                    creator.description("Se ha desbaneado un usuario");
                    mensaje.replyEmbeds(creator.build()).queue();

                    BansThreadController bansThreadController = Main.getControladorBans();
                    //Debemos de ir al controlador del timer, encontrar los bans de este usuario y eliminarlos

                    LightSancion sancionLigeraBanComun = new LightSancion(Sancion.BAN, -1, server.getIdLong(), Long.parseLong(finalDesbanear), -1);
                    LightSancion sancionLigeraPermBan = new LightSancion(Sancion.PERM_BAN, -1, server.getIdLong(), Long.parseLong(finalDesbanear), -1);

                    bansThreadController.eliminarTarea(sancionLigeraBanComun);
                    bansThreadController.eliminarTarea(sancionLigeraPermBan);

                    UnBanEvent eventoUnBan = new UnBanEvent(Main.getBot(), 200, server, mensaje.getAuthor().getId(), finalDesbanear, "");
                    Main.getLoggingListener().onEvent(eventoUnBan);
                }else{
                    creator.description("¡No se ha podido desbanear al usuario, es posible que no este baneado!");
                    mensaje.replyEmbeds(creator.build()).queue();
                }
            }
        }
    }
}
