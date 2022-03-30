package org.darkrpa.discord.bots.june;

import org.darkrpa.discord.bots.june.controllers.MySQLController;
import org.darkrpa.discord.bots.june.model.Servidor;

public class Testing {
    public static void main(String[] args) throws Exception{
        MySQLController controller = new MySQLController();
        Main.setMySQLController(controller);
        Servidor server = new Servidor("1234");
        server.setCantUsuarios(150);
        server.actualizar();
        System.out.println();
    }
}
