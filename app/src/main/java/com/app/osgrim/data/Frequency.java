package com.app.osgrim.data;

/**
 * The Frequency class defines a frequency.
 * La classe Frequency (Périodicité) définit une périodicité.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Frequency {

    private int id;
    private String name;

    /**
     * Creates a frequency. A frequency has an id and a name.
     * Créé une périodicité. Une périodicité a un id et un nom.
     * @param id The frequency id. L'id de la périodicité.
     * @param name The frequency name. Le nom de la périodicité.
     */
    public Frequency(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Permits to get the frequency id.
     * Permet de récupérer l'id de la périodicité.
     * @return The frequency id. L'id de la périodicité.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Defines a new frequency id.
     * Définit un nouveau id de périodicité.
     * @param id The frequency id. L'id de la périodicité.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Permits to get the frequency name.
     * Permet de récupérer le nom de la périodicité.
     * @return The frequency name. Le nom de la périodicité.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Defines a new frequency name.
     * Définit un nouveau nom de la périodicité.
     * @param name The frequency name to set. Le nom de la périodicité à appliquer.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
