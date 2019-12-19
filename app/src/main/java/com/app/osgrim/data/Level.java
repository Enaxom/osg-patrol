package com.app.osgrim.data;

/**
 * The Level class defines a level (a floor).
 * La classe Level (Niveau) définit un niveau (un étage).
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Level {

    private int id;
    private String name;

    /**
     * Creates a level. A level has an id and a name.
     * Créé un niveau. Un niveau a un id et un nom.
     * @param id The level id. L'id du niveau.
     * @param name The level name. Le nom du niveau.
     */
    public Level(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Permits to get the level id.
     * Permet de récupérer l'id du niveau.
     * @return The level id. L'id du niveau.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Defines a new level id.
     * Définit un nouveau id de niveau.
     * @param id The level id. L'id du niveau.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Permits to get the level name.
     * Permet de récupérer le nom du niveau.
     * @return The level name. Le nom du niveau.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Defines a new level name.
     * Définit un nouveau nom de niveau.
     * @param name The level name to set. Le nom du niveau à appliquer.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
