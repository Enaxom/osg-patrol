package com.app.osgrim.data;

import org.jetbrains.annotations.NotNull;

public class Element {
	private int id;
	private String name;

	/**
	 * Creates an element. An element has an id and a name.
	 * Créé un élément. Un élément a un id et un nom.
	 * @param id The element id. L'id de l'élément.
	 * @param name The element name. Le nom de l'élément.
	 */
	Element(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Permits to get the element id.
	 * Permet de récupérer l'id de l'élement.
	 * @return The element id. L'id de l'élement.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Defines a new element id.
	 * Définit un nouveau id d'élement.
	 * @param id The element id. L'id de l'élement.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Permits to get the element name.
	 * Permet de récupérer le nom de l'élement.
	 * @return The element name. Le nom de l'élement.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Defines a new element name.
	 * Définit un nouveau nom d'élement.
	 * @param name The element name to set. Le nom de l'élement à appliquer.
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	@NotNull
	public String toString() {
		return this.name;
	}
}
