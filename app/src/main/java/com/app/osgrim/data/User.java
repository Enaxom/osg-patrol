package com.app.osgrim.data;

/**
 * The User class defines an Osgrim user. <br>
 * La classe User (Utilisateur) définit un utilisateur Osgrim.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class User extends Element {

    /**
     * Creates a user. A user has an id, a firstname and a lastname. <br>
     * Créé un utilisateur. Un utilisateur a un id, un prénom et un nom de famille.
     * @param id The user id. <br> L'id de l'utilisateur.
     * @param lname The user lastname. <br> Le nom de famille de l'utilisateur.
     * @param fname The user firstname. <br> Le prénom de l'utilisateur.
     */
    public User(int id, String lname, String fname) {
        super(id, fname + " " + lname);
    }
}
