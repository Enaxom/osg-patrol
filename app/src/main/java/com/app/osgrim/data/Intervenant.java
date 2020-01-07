package com.app.osgrim.data;

/**
 * The Intervenant (Contributor) class defines a contributor, who's a user belonging to a team.
 * La classe Intervenant définie un intervenant, qui est un utilisateur appartenant à une équipe.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Intervenant {
	private boolean isSelected;
	private Team team;
	private User user;

	/**
	 * Creates an Intervenant. An intervenant has a team and a user (himself). Créé un intervenant.
	 * Un
	 * intervenant a une équipe et un utilisateur (lui-même).
	 * @param team The intervenant team. L'équipe de l'intervenant.
	 * @param user The user associated to the intervenant. L'utilisateur associé à l'intervenant.
	 */
	public Intervenant(Team team, User user) {
		this.team = team;
		this.user = user;
		this.isSelected = false;
	}

	/**
	 * Get the value of isSelected. Renvoie la valeur de isSelected.
	 * @return True if the intervenant is selected for a report. False if not. Vrai si
	 * l'intervenant est sélectionné pour un rapport. Faux sinon.
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * Set the value of isSelected. Fixe la veleur de isSelected.
	 * @param selected True if the intervenant is selected in the report. False if not. Vrai si
	 *                   l'intervenant est sélectionné dans le rapport. Faux sinon.
	 */
	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	/**
	 * Get the team of the intervenant. A user can belong to several teams but an intervenant is
	 * associated to one team only. Renvoie l'équipe de l'intervenant. Un utilisateur peut
	 * appartenir à plusieurs équipes mais un intervenant est associé à qu'une équipe seulement.
	 * @return The intervenant's team. L'équipe de l'intervenant.
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Set the team of the intervenant. Fixe l'équipe de l'intervenant.
	 * @param team The team associated to the intervenant. L'équipe associée à l'intervenant.
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * Get the user of the intervenant. Renvoie l'utilisateur associé à l'intervenant.
	 * @return The intervenant user object. L'objet utilisateur (User) de l'intervenant.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the user associated to the intervenant. Fixe l'utilisateur associé à l'intervenant.
	 * @param user The user associated to the intervenant. L'utilisateur associé à l'intervenant.
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
