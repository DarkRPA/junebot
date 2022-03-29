package org.darkrpa.discord.bots.june.model;

import org.darkrpa.discord.bots.june.controllers.MySQLController;

public abstract class ObjetoGuardable {
    private MySQLController controller;

    public ObjetoGuardable(MySQLController controller){
        this.controller = controller;
    }

    /**
     * Metodo eliminar, este metodo va a ser el encargado de eliminar la instancia del objeto de la base de datos
     * cada clase especificara el funcionamiento para eliminar su objeto
     * @return
     */
    public abstract boolean eliminar();
    /**
     * Actualizar es un metodo el cual debera de poder actualizar el objeto de la tabla en la base de datos
     * @return
     */
    public abstract boolean actualizar();
    /**
     * Metodo get, este metodo get lo que hara sera buscar en la tabla de la clase un objeto con la id especifica
     * sino no lo encuentra lo creara con esa id y vacio, hasta que no se actualice no se insertará en la base de datos
     */
    public abstract void get(Object id);
}
