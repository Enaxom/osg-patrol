package com.app.osgrim.data;

/**
 * The Frequency class defines a frequency.
 * La classe Frequency (Périodicité) définit une périodicité.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Frequency extends Element {

    /**
     * Creates a frequency. A frequency has an id and a name.
     * Créé une périodicité. Une périodicité a un id et un nom.
     * @param id The frequency id. L'id de la périodicité.
     * @param name The frequency name. Le nom de la périodicité.
     */
    public Frequency(int id, String name) {
        super(id, name);
    }
}
