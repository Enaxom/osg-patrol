package com.app.osgrim.data;

import java.util.ArrayList;
import java.util.List;

/**
 * The Building class defines a building.
 * La classe Building (Bâtiment) définit un bâtiment.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Building {
    
    private int id;
    private String name;
    private List<Level> levels;

    /**
     * Creates a building. A building has an id, a name and a list of levels.
     * Créé un bâtiment. Un bâtiment a un id, un nom et une liste de niveaux.
     * @param id The building id. L'id du bâtiment.
     * @param name The building name. Le nom du bâtiment.
     */
    public Building(int id, String name) {
        this.id = id;
        this.name = name;
        // The levels list is initialized. La liste des niveaux est initialisée.
        this.levels = new ArrayList<>();
    }

    /**
     * Permits to get the building id.
     * Permet de récupérer l'id du bâtiment.
     * @return The building id. L'id du bâtiment.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Defines a new building id.
     * Définit un nouveau id de bâtiment.
     * @param id The building id. L'id du bâtiment.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Permits to get the building name.
     * Permet de récupérer le nom du bâtiment.
     * @return The building name. Le nom du bâtiment.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Defines a new building name.
     * Définit un nouveau nom de bâtiment.
     * @param name The building name to set. Le nom du bâtiment à appliquer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Add a level (a Level object) to the building.
     * Ajoute un niveau (un objet Level) au bâtiment.
     * @param level The level to add. Le niveau à ajouter.
     */
    public void addLevel(Level level) {
        // The level can't be add if it's already in the building.
        // Le niveau ne peut pas être ajouté si il est déjà dans le bâtiment.
        if (!this.levels.contains(level))
            this.levels.add(level);
    }

    /**
     * Get all the levels of the building.
     * Récupère tous les niveaux du bâtiment.
     * @return The building levels list. La liste des niveaux du bâtiment.
     */
    public List<Level> getLevels() {
        return new ArrayList<>(this.levels);
    }

    @Override
    public String toString() {
        return name;
    }
}
