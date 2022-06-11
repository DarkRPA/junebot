package org.darkrpa.discord.bots.june;

import org.darkrpa.discord.bots.june.controllers.MySQLController;
import org.darkrpa.discord.bots.june.model.sanciones.Sancion;

public class Testing {
    public static void main(String[] args) throws Exception{
        MySQLController controller = new MySQLController();
        Main.setMySQLController(controller);
        /*Servidor server = new Servidor("1234");
        server.setCantUsuarios(20);
        server.eliminar();
        System.out.println();*/

        //UserNivel nivel = new UserNivel("1234", "941302117468110879");

        Sancion sancion = new Sancion(223, "123", "1234");


        Sancion sancion2 = new Sancion(223, "123", "1234");

        //BansThreadController bansThreadController = new BansThreadController();

        //bansThreadController.cargarTodasSanciones();


        System.out.println();

    }
}
