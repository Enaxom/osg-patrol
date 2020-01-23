package com.app.osgrim;

import android.view.View;

/**
 * Interface OnItemClickListener implemented by the InputFragment and indicated in the
 * ReportAdapter so that when a user click on a report in the list of reports, the report opens
 * itself in the report input tab. <br>
 * Interface OnItemClickListener implémentée par InputFragment et indiquée dans le ReportAdapter
 * pour que quand un utilisateur clique sur un rapport dans la liste des rapports, le rapport
 * s'ouvre dans l'onglet de saisie de rapport.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public interface OnItemClickListener {
	void onClick(View view, int position);
}
