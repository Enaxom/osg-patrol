package com.app.osgrim.data;

import java.util.ArrayList;
import java.util.List;

/**
 * The SpaceCat class defines a space category (room category). <br>
 * La classe SpaceCat (catégorie local) définit une catégorie de local.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class SpaceCat extends Element {

    /**
     * The list of spaces associated to the space category. <br>
     * La liste des objets local associés à la catégorie de local.
     */
    private List<Space> spaces;

    /**
     * Creates a spaceCat. A spaceCat has an id, a name and a list of spaces. <br>
     * Créé une catégorie de local. Une catégorie de local a un id, un nom et une liste de locaux.
     * @param id The space category id. <br> L'id de la catégorie de local.
     * @param name The space category name. <br> Le nom de la catégorie de local.
     */
    public SpaceCat(int id, String name) {
        super(id, name);
        // The spaces list is initialized. La liste des locaux est initialisée.
        this.spaces = new ArrayList<>();
    }

    /**
     * Add a space (a Space object) to the space category. <br>
     * Ajoute un local (un objet Space) à la catégorie de local.
     * @param space The space to add. <br> Le local à ajouter.
     */
    public void addSpace(Space space) {
        // The space can't be add if it's already in the spaceCat.
        // Le local ne peut pas être ajouté si il est déjà dans la catégorie de local.
        if (!this.spaces.contains(space))
            this.spaces.add(space);
    }

    /**
     * Get all the spaces of the spaceCat. <br>
     * Récupère tous les locaux de la catégorie de local.
     * @return The spaceCat spaces list. <br> La liste des locaux de la catégorie de local.
     */
    public List<Space> getSpaces() {
        return new ArrayList<>(this.spaces);
    }
}
