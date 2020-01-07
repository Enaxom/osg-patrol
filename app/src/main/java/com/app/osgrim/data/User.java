package com.app.osgrim.data;

/**
 * The User class define an Osgrim user.
 * La classe User (Utilisateur) définit un utilisateur Osgrim.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class User extends Element {

    private String lname, fname;

    /**
     * Creates a user. A user has an id, a firstname and a lastname.
     * Créé un utilisateur. Un utilisateur a un id, un prénom et un nom de famille.
     * @param id The user id. L'id de l'utilisateur.
     * @param lname The user lastname. Le nom de famille de l'utilisateur.
     * @param fname The user firstname. Le prénom de l'utilisateur.
     */
    public User(int id, String lname, String fname) {
        super(id, fname + " " + lname);
        this.lname = lname;
        this.fname = fname;
    }

    /**
     * Permits to get the user lastname.
     * Permet de récupérer le nom de famille de l'utilisateur.
     * @return The user lastname. Le nom de l'utilisateur.
     */
    public String getLname() {
       return this.lname;
    }

    /**
     * Defines a user lastname.
     * Déinit un nom de famille pour l'utilisateur.
     * @param lname The lastname to set. Le nom de famille à appliquer.
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Permits to get the user firstname.
     * Permet de récupérer le prénom de l'utilisateur.
     * @return The user firstname. Le prénom de l'utilisateur.
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * Defines a user firstname.
     * Déinit un prénom pour l'utilisateur.
     * @param fname The firstname to set. Le prénom à appliquer.
     */
    public void setFname(String fname) {
        this.fname = fname;
    }
}
