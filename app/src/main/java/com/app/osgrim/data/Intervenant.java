package com.app.osgrim.data;

public class Intervenant {
	private boolean isSelected;
	private Team team;
	private User user;

	public Intervenant(Team team, User user) {
		this.team = team;
		this.user = user;
		this.isSelected = false;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
