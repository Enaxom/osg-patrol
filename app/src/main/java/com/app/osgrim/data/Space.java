package com.app.osgrim.data;

/**
 * The Space class defines a space (a room). <br>
 * La classe Space (Local) définit un local.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Space extends Element {

    /**
     * Creates a space. A space has an id and a name. <br>
     * Créé un local. Un local a un id et un nom.
     * @param id The space id. <br> L'id du local.
     * @param name The space name. <br> Le nom du local.
     */
    public Space(int id, String name) {
        super(id, name);
    }
}
