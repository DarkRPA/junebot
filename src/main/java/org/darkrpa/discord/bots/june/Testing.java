package org.darkrpa.discord.bots.june;

import java.awt.Font;
import java.io.File;

import org.darkrpa.discord.bots.june.comandos.Ayuda;
import org.darkrpa.discord.bots.june.controllers.MySQLController;
import org.darkrpa.discord.bots.june.model.Logging;
import org.darkrpa.discord.bots.june.model.Servidor;
import org.darkrpa.discord.bots.june.model.StaffRol;
import org.darkrpa.discord.bots.june.model.UserNivel;
import org.darkrpa.discord.bots.june.model.Usuario;
import org.darkrpa.discord.bots.june.utils.ImageEditor;

public class Testing {
    public static void main(String[] args) throws Exception{
        MySQLController controller = new MySQLController();
        Main.setMySQLController(controller);
        /*Servidor server = new Servidor("1234");
        server.setCantUsuarios(20);
        server.eliminar();
        System.out.println();*/

        //UserNivel nivel = new UserNivel("1234", "941302117468110879");

        Servidor servidor = new Servidor("djah");
        Logging logging = new Logging("djah");
        logging.setPermisos(35);
        //UserNivel userNivel = new UserNivel("311242525598023691", "880403638072262677");
        Long binario = logging.getBinary();
        System.out.println();

    }
}
