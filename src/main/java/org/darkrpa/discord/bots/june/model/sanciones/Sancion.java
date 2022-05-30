package org.darkrpa.discord.bots.june.model.sanciones;

import java.util.List;

import org.darkrpa.discord.bots.june.Main;
import org.darkrpa.discord.bots.june.anotaciones.CampoGetter;
import org.darkrpa.discord.bots.june.anotaciones.CampoSetter;
import org.darkrpa.discord.bots.june.anotaciones.Guardable;
import org.darkrpa.discord.bots.june.events.LoggingListener;
import org.darkrpa.discord.bots.june.logging.discord.events.GenericGuildDiscordEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.ban.UnBanEvent;
import org.darkrpa.discord.bots.june.logging.discord.events.mute.UnmuteEvent;
import org.darkrpa.discord.bots.june.model.ObjetoGuardable;
import org.darkrpa.discord.bots.june.model.Servidor;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

@Guardable(nombreTabla = "bans")
public class Sancion extends ObjetoGuardable{

    public static final byte WARN=1, MUTE=2, KICK=3, BAN=4, PERM_BAN=5;

    private int idSancion = -1;
    private String idServidor;
    private String idUsuarioSancionado;
    private String idUsuarioAdmin;
    private String motivo;
    private int idEvento;
    private long fechaEvento;
    private long fechaVencimiento = -1;

    public Sancion(String idServidor, String idUsuarioSancionado){
        this.idServidor = idServidor;
        this.idUsuarioSancionado = idUsuarioSancionado;
    }

    public Sancion(int idSancion, String idServidor, String idUsuarioSancionado){
        this.idSancion = idSancion;
        this.idServidor = idServidor;
        this.idUsuarioSancionado = idUsuarioSancionado;

        this.get();
    }

    @CampoGetter(nombreColumna = "idBan", isPrimary = true, isAutoIncremental = true)
    public int getIdSancion() {
        return idSancion;
    }
    @CampoSetter(nombreColumna = "idBan", isAutoIncremental = true)
    public void setIdSancion(int idSancion) {
        this.idSancion = idSancion;
    }
    @CampoGetter(nombreColumna = "idServidor", isPrimary = true)
    public String getIdServidor() {
        return idServidor;
    }
    @CampoSetter(nombreColumna = "idServidor")
    public void setIdServidor(String idServidor) {
        this.idServidor = idServidor;
    }
    @CampoGetter(nombreColumna = "idUsuarioSancionado", isPrimary = true)
    public String getIdUsuarioSancionado() {
        return idUsuarioSancionado;
    }
    @CampoSetter(nombreColumna = "idUsuarioSancionado")
    public void setIdUsuarioSancionado(String idUsuarioSancionado) {
        this.idUsuarioSancionado = idUsuarioSancionado;
    }
    @CampoGetter(nombreColumna = "idUsuarioAdmin")
    public String getIdUsuarioAdmin() {
        return idUsuarioAdmin;
    }
    @CampoSetter(nombreColumna = "idUsuarioAdmin")
    public void setIdUsuarioAdmin(String idUsuarioAdmin) {
        this.idUsuarioAdmin = idUsuarioAdmin;
    }
    @CampoGetter(nombreColumna = "motivo")
    public String getMotivo() {
        return motivo;
    }
    @CampoSetter(nombreColumna = "motivo")
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    @CampoGetter(nombreColumna = "idEvento")
    public int getIdEvento() {
        return idEvento;
    }
    @CampoSetter(nombreColumna = "idEvento")
    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }
    @CampoGetter(nombreColumna = "fechaEvento")
    public double getFechaEvento() {
        return fechaEvento;
    }
    @CampoSetter(nombreColumna = "fechaEvento")
    public void setFechaEvento(long fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
    @CampoGetter(nombreColumna = "fechaVencimiento")
    public long getFechaVencimiento() {
        return fechaVencimiento;
    }
    @CampoSetter(nombreColumna = "fechaVencimiento")
    public void setFechaVencimiento(long fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void revocar(){
        //Aqui debemos de poner la logica para que cuando se tenga que revocar la sancion se identifica el tipo de sanción
        //y se actue en consecuencia
        try {
            Servidor serverDB = new Servidor(this.idServidor);
            Guild server = Main.getBot().getGuildById(this.idServidor);
            //Tenemos el servidor
            LoggingListener listener = Main.getLoggingListener();
            if(this.idEvento == Sancion.MUTE){
                //Es un mute por lo que debemos de eliminar el rol muteado del servidor
                if(serverDB.getRolMute().isEmpty()){
                    //Antigua version, esta vacio
                    return;
                }

                Role rolMute = server.getRoleById(serverDB.getRolMute());
                Member miembroServer = server.getMemberById(this.idUsuarioSancionado);

                server.removeRoleFromMember(miembroServer, rolMute).queue(e->{
                    //Vamos a enviar un evento sobre un desmuteo
                    UnmuteEvent evento = new UnmuteEvent(Main.getBot(), 200, server, Main.getBot().getSelfUser().getAsMention(), miembroServer.getId(), "Tiempo expirado");
                    listener.onEvent(evento);
                });
            }else if(this.idEvento == Sancion.BAN){
                server.unban(this.idUsuarioSancionado).queue(e->{
                    UnBanEvent evento = new UnBanEvent(Main.getBot(), 200, server, Main.getBot().getSelfUser().getAsMention(), this.idUsuarioSancionado, "Tiempo expirado");
                    listener.onEvent(evento);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
