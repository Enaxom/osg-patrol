package com.app.osgrim.data;

/**
 * The Incident class defines an incident. <br>
 * La classe Incident définit un incident.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Incident extends Element {

    /**
     * True if the Incident is selected, false if not. <br>
     * Vrai si l'incident est sélectionné, faux sinon.
     */
    private boolean isSelected;

    /**
     * The number of incidents when the incident is selected. <br>
     * Le nombre d'anomalies lorsque l'incident est sélectionné.
     */
    private int nbrIncident;

    /**
     * Creates an incident. An incident has an id and a name. <br>
     * Créé un incident. Un incident a un id et un nom.
     * @param id The incident id. <br> L'id de l'incident.
     * @param name The incident name. <br> Le nom de l'incident.
     */
    public Incident(int id, String name) {
        this(id, name, false, 0);
    }

    /**
     * Creates an incident. The incident created with this constructor can be selected and has a
     * number of incidents. <br>
     * Créé un incident. L'incident créé avec ce constructeur peut être sélectionné et avoir un
     * nombre d'incidents.
     * @param id The incident id. <br> L'id de l'incident.
     * @param name The name id. <br> Le nom de l'incident.
     * @param isSelected True if the incident is selected, false if not. <br> Vrai si l'incident
     *                   est sélectionné, faux sinon.
     * @param nbrIncident The number of incidents. <br> Le nombre d'anomalies.
     */
    Incident(int id, String name, boolean isSelected, int nbrIncident) {
        super(id, name);
        this.isSelected = isSelected;
        this.nbrIncident = nbrIncident;
    }

    /**
     * Set the value of isSelected. <br>
     * Fixe la veleur de isSelected.
     * @param isSelected True if the incident is selected in the report. False if not. <br> Vrai si
     *                   l'incident est sélectionné dans le rapport. Faux sinon.
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * Get the value of isSelected. <br>
     * Renvoie la valeur de isSelected.
     * @return True if the incident is selected. False if not. <br> Vrai si l'incident est
     * sélectionné. Faux sinon.
     */
    public boolean isSelected() {
        return this.isSelected;
    }

    /**
     * Set the number of incidents when it's selected. <br>
     * Fixe le nombre d'incidents ci celui-ci est sélectionné.
     * @param nbrIncident The number of incidents. <br> Le nombre d'anomalies.
     */
    public void setNbrIncident(int nbrIncident) {
        this.nbrIncident = nbrIncident;
    }

    /**
     * Get the number of incidents. <br>
     * Renvoie le nombre d'anomalies liées à l'incident.
     * @return The number of incidents. <br> Le nombre d'anomalies.
     */
    public int getNbrIncident() {
        return this.nbrIncident;
    }
}
