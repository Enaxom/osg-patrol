package com.app.osgrim;

/**
 * BooleanVariable class. Needed to know if a boolean variable change and to act accordingly. <br>
 * Classe BooleanVariable. Nécessaire pour savoir si une valeur bouléenne change et d'agir en
 * conséquences.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
class BooleanVariable {

	/**
	 * The boolean variable used fro the true/false values. <br>
	 * La variable bouléenne utilisée pour les valeurs vrai/faux.
	 */
	private boolean boo = false;

	/**
	 * The changeListener that'll be called when the boolean variable changes of value. <br>
	 * L'écouteur de changement qui sera appelé quand le bouléen change de valeur.
	 */
	private ChangeListener listener;

	/**
	 * Get the boolean value. <br> Renvoie la valeur du bouléen.
	 * @return True if the boolean is true. False if not. <br> Vrai si le bouléen est vrai, faux sinon.
	 */
	boolean isBoo() {
		return boo;
	}

	/**
	 * Set the value of the boolean variable. Call the ChangeListener for the ones who listen to the
	 * event. <br> Fixe la valeur de la variable bouléenne. Appelle ChangeListener pour ceux qui
	 * écoutent l'évènement.
	 * @param boo The boolean value. <br> La valeur du bouléen.
	 */
	void setBoo(boolean boo) {
		this.boo = boo;
		if (listener != null) listener.onChange();
	}

	/**
	 * Set the ChangeListener of the boolean variable. <br> Fixe le ChangeListener de la variable
	 * bouléenne.
	 * @param listener The change listener of the boolean variable. <br> L'écouteur de changement de
	 *                    la variable bouléenne.
	 */
	void setListener(ChangeListener listener) {
		this.listener = listener;
	}

	/**
	 * The ChangeListener interface. <br> L'interface du ChangeListener.
	 */
	public interface ChangeListener {
		void onChange();
	}
}
