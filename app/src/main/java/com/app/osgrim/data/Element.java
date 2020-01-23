package com.app.osgrim.data;

import org.jetbrains.annotations.NotNull;

/**
 * The class Element is the superclass of many classes in the data packages. All the classes that
 * have an id and a name are the subclasses of Element. <br>
 * La classe Element est la superclasse de beaucoup de classes dans le paquet data. Toutes les
 * classes qui ont un id et un nom sont des sous-classes de Element.
 */
public class Element {

	/**
	 * The Element id. <br>
	 * L'id de l'Element.
	 */
	private int id;

	/**
	 * The Element name. <br>
	 * Le nom de l'Element.
	 */
	private String name;

	/**
	 * Creates an element. An element has an id and a name. <br>
	 * Créé un élément. Un élément a un id et un nom.
	 * @param id The element id. <br> L'id de l'élément.
	 * @param name The element name. <br> Le nom de l'élément.
	 */
	Element(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Get the element id. <br>
	 * Permet de récupérer l'id de l'élement.
	 * @return The element id. <br> L'id de l'élement.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Defines a new element id. <br>
	 * Définit un nouveau id d'élement.
	 * @param id The element id. <br> L'id de l'élement.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the element name. <br>
	 * Permet de récupérer le nom de l'élement.
	 * @return The element name. <br> Le nom de l'élement.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Defines a new element name. <br>
	 * Définit un nouveau nom d'élement.
	 * @param name The element name to set. <br> Le nom de l'élement à appliquer.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the String of the Element's name. <br>
	 * Renvoie la chaîne du caractère du nom de l'Element.
	 * @return The String Element's name. <br> La chaîne de caractère du nom de l'Element.
	 */
	@Override
	@NotNull
	public String toString() {
		return this.name;
	}
}
