package com.app.osgrim.data;

/**
 * The Incident class defines an incident.
 * La classe Incident définit un incident.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Incident extends Element {

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
        super(id, name);
        this.isSelected = isSelected;
        this.nbrIncident = nbrIncident;
    }

    /**
     * Set the value of isSelected. Fixe la veleur de isSelected.
     * @param isSelected True if the incident is selected in the report. False if not. Vrai si
     *                   l'incident est sélectionné dans le rapport. Faux sinon.
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * Get the value of isSelected. Renvoie la valeur de isSelected.
     * @return True if the incident is selected. False if not. Vrai si l'incident est sélectionné
     * . Faux sinon.
     */
    public boolean isSelected() {
        return this.isSelected;
    }

    /**
     * Set the number of incidents when it's selected. Fixe le nombre d'incidents ci celui-ci est
     * sélectionné.
     * @param nbrIncident The number of incidents. Le nombre d'anomalies.
     */
    public void setNbrIncident(int nbrIncident) {
        this.nbrIncident = nbrIncident;
    }

    /**
     * Get the number of incidents. Renvoie le nombre d'anomalies liées à l'incident.
     * @return The number of incidents. Le nombre d'anomalies.
     */
    public int getNbrIncident() {
        return this.nbrIncident;
    }
}
