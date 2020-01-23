package com.app.osgrim.data;

import java.util.ArrayList;
import java.util.List;

/**
 * The Team class defines a team of Osgrim users. <br>
 * La classe Team (Equipe) définit une équipe d'utilisateurs Osgrim.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Team extends Element {

    /**
     * The list of users who are members of the team. <br>
     * La liste des utilisateurs qui sont membres de l'équipe.
     */
    private List<User> members;

    /**
     * Creates a team. A team has an id, a name and a list of members. <br>
     * Créé une équipe. Une équipe a un id, un nom et une liste de membres.
     * @param id The team id. <br> L'id de l'équipe.
     * @param name The team name. <br> Le nom de l'équipe.
     */
    public Team(int id, String name) {
        super(id, name);
        // The members list is initialized. La liste des membres est initialisée.
        this.members = new ArrayList<>();
    }

    /**
     * Add a member (a User object) to the team. <br>
     * Ajoute un membre (un objet User) à l'équipe.
     * @param user The user to add. <br> L'utilisateur à ajouter.
     */
    public void addMember(User user) {
        // The user can't be add if he's already in the team.
        // L'utilisateur ne peut pas être ajouté si il est déjà dans l'équipe.
        if (!this.members.contains(user))
            this.members.add(user);
    }

    /**
     * Get all the members of the team. <br>
     * Récupère tous les membres de l'équipe.
     * @return The team users list. <br> La liste des utilisateurs de l'équipe.
     */
    public List<User> getMembers() {
        return new ArrayList<>(this.members);
    }
}
