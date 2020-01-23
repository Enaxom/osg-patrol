package com.app.osgrim.data;

import java.util.ArrayList;
import java.util.List;

/**
 * The Detail class defines a detail. <br>
 * La classe Detail définit un détail.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Detail {

    /**
     * The detail title. <br>
     * Le titre du détail.
     */
    private String title;

    /**
     * The list of the possible answers of the detail. <br>
     * La liste des réponses possibles du détail.
     */
    private List<String> answers;

    /**
     * Creates a detail. A detail has a title and multiple answers. <br>
     * Créé un détail. Un détail a un titre et plusieurs réponses.
     * @param title The detail title. <br> Le titre du détail.
     */
    public Detail(String title) {
        this.title = title;
        this.answers = new ArrayList<>();
    }

    /**
     * Get the detail title. <br>
     * Permet de récupérer le titre du détail.
     * @return The detail title. <br> Le titre du détail.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Add an answer to the detail list of answers. <br>
     * Ajoute une réponse dans la liste de réponses du détail.
     * @param answer The answer to add. <br> La réponse à ajouter.
     */
    public void addAnswer(String answer) {
        if (!this.answers.contains(answer))
            this.answers.add(answer);
    }

    /**
     * Get all the details answers. <br>
     * Permet de récupérer toutes les réponses du détail.
     * @return The list of answers. <br> La liste des réponses.
     */
    public List<String> getAnswers() {
        return new ArrayList<>(this.answers);
    }
}
