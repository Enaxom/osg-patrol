package com.app.osgrim.data;

import java.util.ArrayList;
import java.util.List;

/**
 * The Detail class defines a detail.
 * La classe Detail définit un détail.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Detail {

    private String title;
    private List<String> answers;

    /**
     * Creates a detail. A detail has a title and multiple answers.
     * Créé un détail. Un détail a un titre et plusieurs réponses.
     * @param title The detail title. Le titre du détail.
     */
    public Detail(String title) {
        this.title = title;
        this.answers = new ArrayList<>();
    }

    /**
     * Permits to get the detail title.
     * Permet de récupérer le titre du détail.
     * @return The detail title. Le titre du détail.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Defines a new detail title.
     * Définit un nouveau titre de détaiL.
     * @param title The detail title. Le titre du détail.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Add an answer to the detail list of answers.
     * Ajoute une réponse dans la liste de réponses du détail.
     * @param answer The answer to add. La réponse à ajouter.
     */
    public void addAnswer(String answer) {
        if (!this.answers.contains(answer))
            this.answers.add(answer);
    }

    /**
     * Permits to get all the details answers.
     * Permet de récupérer toutes les réponses du détail.
     * @return The list of answers. La liste des réponses.
     */
    public List<String> getAnswers() {
        return new ArrayList<>(this.answers);
    }
}
