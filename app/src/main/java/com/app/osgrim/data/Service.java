package com.app.osgrim.data;

/**
 * The Service class defines a service.
 * La classe Service définit un service.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Service extends Element {

    private boolean selected;

    /**
     * Creates a service. A service has an id and a name.
     * Créé un service. Un service a un id et un nom.
     * @param id The service id. L'id du service.
     * @param name The service name. Le nom du service.
     */
    public Service(int id, String name) {
        super(id, name);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }
}
