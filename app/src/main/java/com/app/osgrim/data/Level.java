package com.app.osgrim.data;

/**
 * The Level class defines a level (a floor).
 * La classe Level (Niveau) définit un niveau (un étage).
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Level extends Element {

    /**
     * Creates a level. A level has an id and a name.
     * Créé un niveau. Un niveau a un id et un nom.
     * @param id The level id. L'id du niveau.
     * @param name The level name. Le nom du niveau.
     */
    public Level(int id, String name) {
        super(id, name);
    }
}
