package org.darkrpa.discord.bots.june;

import org.darkrpa.discord.bots.june.comandos.Ayuda;
import org.darkrpa.discord.bots.june.controllers.MySQLController;
import org.darkrpa.discord.bots.june.model.Servidor;

public class Testing {
    public static void main(String[] args) throws Exception{
        /*MySQLController controller = new MySQLController();
        Main.setMySQLController(controller);
        Servidor server = new Servidor("1234");
        server.setCantUsuarios(20);
        server.eliminar();
        System.out.println();*/
        Ayuda s = new Ayuda();
        s.ejecutar();
    }
}
