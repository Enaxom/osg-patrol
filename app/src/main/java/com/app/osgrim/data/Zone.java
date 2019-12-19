package com.app.osgrim.data;

import java.util.ArrayList;
import java.util.List;

/**
 * The Zone class defines a zone.
 * La classe Zone définit une zone.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Zone extends Element {

    private List<Building> buildings;

    /**
     * Creates a zone. A zone has an id, a name and a list of buildings.
     * Créé un zone. Un zone a un id, un nom et une liste de bâtiments.
     * @param id The zone id. L'id de la zone.
     * @param name The zone name. Le nom de la zone.
     */
    public Zone(int id, String name) {
        super(id, name);
        // The buildings list is initialized. La liste des bâtiments est initialisée.
        this.buildings = new ArrayList<>();
    }

    /**
     * Add a Building (a Building object) to the zone.
     * Ajoute un niveau (un objet Building) à la zone.
     * @param building The Building to add. Le bâtiment à ajouter.
     */
    public void addBuilding(Building building) {
        // The building can't be add if it's already in the zone.
        // Le bâtiment ne peut pas être ajouté si il est déjà dans le zone.
        if (!this.buildings.contains(building))
            this.buildings.add(building);
    }

    /**
     * Get all the buildings of the zone.
     * Récupère tous les bâtiments de la zone.
     * @return The zone buildings list. La liste des bâtiments de la zone.
     */
    public List<Building> getBuildings() {
        return new ArrayList<>(this.buildings);
    }
}
