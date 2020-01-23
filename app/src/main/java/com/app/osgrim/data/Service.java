package com.app.osgrim.data;

/**
 * The Service class defines a service. <br>
 * La classe Service définit un service.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Service extends Element {

    /**
     * True if the service is selected, false if not.
     * Vrai si le service est sélectionné, faux sinon.
     */
    private boolean selected;

    /**
     * Creates a service. A service has an id and a name. <br>
     * Créé un service. Un service a un id et un nom.
     * @param id The service id. <br> L'id du service.
     * @param name The service name. <br> Le nom du service.
     */
    public Service(int id, String name) {
        super(id, name);
    }

    /**
     * Set the service selected or deselected. Used for the report input. <br> Fixe le service
     * comme sélectionné ou désélectionné. Utilisé pour la saisie de rapport.
     * @param selected The selected value. True if it's selected, false if not. <br> La valeur de la
     *                 selection. Vrai si c'est sélectionné, faux sinon.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Allows to know if the service is selected. <br> Permet de savoir si le service est sélectionné.
     * @return True if the service is selected, false if not. <br> Vrai si le service est sélectionné,
     * faux sinon.
     */
    public boolean isSelected() {
        return this.selected;
    }
}
