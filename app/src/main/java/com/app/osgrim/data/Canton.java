package com.app.osgrim.data;

/**
 * The Canton class defines a canton. <br>
 * La classe Canton définit un canton.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Canton extends Element {

    /**
     * Creates a canton. A canton has an id and a name. <br>
     * Créé un canton. Un canton a un id et un nom.
     * @param id The canton id. <br> L'id du canton.
     * @param name The canton name. <br> Le nom du canton.
     */
    public Canton(int id, String name) {
        super(id, name);
    }
}
