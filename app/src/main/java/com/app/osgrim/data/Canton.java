package com.app.osgrim.data;

/**
 * The Canton class defines a canton.
 * La classe Canton définit un canton.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Canton extends Element {

    /**
     * Creates a canton. A canton has an id and a name.
     * Créé un canton. Un canton a un id et un nom.
     * @param id The canton id. L'id du canton.
     * @param name The canton name. Le nom du canton.
     */
    public Canton(int id, String name) {
        super(id, name);
    }
}
