package com.app.osgrim.data;

public class Destination extends Element {

    private int pos;

    /**
     * Creates an element. An element has an id and a name. <br>
     * Créé un élément. Un élément a un id et un nom.
     *
     * @param id   The element id. <br> L'id de l'élément.
     * @param name The element name. <br> Le nom de l'élément.
     */
    public Destination(int id, String name, int pos) {
        super(id, name);
        this.pos = pos;
    }
}
