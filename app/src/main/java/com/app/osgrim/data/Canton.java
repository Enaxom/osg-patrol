package com.app.osgrim.data;

/**
 * The Canton class defines a canton.
 * La classe Canton définit un canton.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Canton {

    private int id;
    private String name;

    /**
     * Creates a canton. A canton has an id and a name.
     * Créé un canton. Un canton a un id et un nom.
     * @param id The canton id. L'id du canton.
     * @param name The canton name. Le nom du canton.
     */
    public Canton(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Permits to get the canton id.
     * Permet de récupérer l'id du canton.
     * @return The canton id. L'id du canton.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Defines a new canton id.
     * Définit un nouveau id de canton.
     * @param id The canton id. L'id du canton.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Permits to get the canton name.
     * Permet de récupérer le nom du canton.
     * @return The canton name. Le nom du canton.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Defines a new canton name.
     * Définit un nouveau nom de canton.
     * @param name The canton name to set. Le nom du canton à appliquer.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
