package com.app.osgrim.data;

/**
 * The Incident class defines an incident.
 * La classe Incident définit un incident.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Incident {

    private int id;
    private String name;
    private boolean isSelected;
    private int nbrIncident;

    /**
     * Creates an incident. An incident has an id and a name.
     * Créé un incident. Un incident a un id et un nom.
     * @param id The incident id. L'id de l'incident.
     * @param name The incident name. Le nom de l'incident.
     */
    public Incident(int id, String name) {
        this(id, name, false, 0);
    }

    Incident(int id, String name, boolean isSelected, int nbrIncident) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
        this.nbrIncident = nbrIncident;
    }

    /**
     * Permits to get the incident id.
     * Permet de récupérer l'id de l'incident.
     * @return The canton id. L'id du canton.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Defines a new incident id.
     * Définit un nouveau id d'incident.
     * @param id The incident id. L'id de l'incident.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Permits to get the incident name.
     * Permet de récupérer le nom de l'incident.
     * @return The canton name. Le nom du canton.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Defines a new incident name.
     * Définit un nouveau nom de l'incident.
     * @param name The incident name to set. Le nom de l'incident à appliquer.
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setNbrIncident(int nbrIncident) {
        this.nbrIncident = nbrIncident;
    }

    public int getNbrIncident() {
        return this.nbrIncident;
    }
}
