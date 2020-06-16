package com.app.osgrim;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * ListFragment class. Belongs to MainActivity and represents the first tab of the application.
 * The displaying of all the saved reports is handled here. <br>
 * Classe ListFragment. Appartient à MainActivity et représente le premier onglet de
 * l'application. L'affichage de tous les rapports enregistrés est géré ici.
 */
public class ListBilanFragment extends Fragment {

	/**
	 * The tab view. Will be equals to the fragment_list layout. <br>
	 * La vue de l'onglet. Sera égale au layout fragment_list (fichier xml).
	 */
	private View listView;

	/**
	 * The MainActivity class. Needed because there is the reports list used in this class and
	 * it's a MainActivity variable. <br>
	 * La classe MainActivity. Nécessaire car il y a la liste des rapports utilisée dans cette
	 * classe et c'est une variable de MainActivity.
	 */
	private MainActivity mainAct;

	/**
	 * Never called but needed for the class structure. <br>
	 * Jamais appelé mais nécessaire pour la structure de la classe.
	 */
	public ListBilanFragment() {
		// Required empty public constructor
	}

	/**
	 * Method executed when the application starts or when data is imported. <br>
	 * Méthode exécutée quand l'application démarre ou quand des données sont importées.
	 * @param inflater The layout used for this class. <br> Le layout utilisé dans cette classe.
	 * @param container The ViewGroup associated to the class. <br> Le ViewGroup associé à la
	 *                     classe.
	 * @param savedInstanceState Used to save the instance of the application. <br> Utilisé pour
	 *                              enregistrer l'instance de l'application.
	 * @return The View of the class listFragment. <br> La Vue de la classe listFragment.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		listView = inflater.inflate(R.layout.fragment_list_bilan, container, false);
		mainAct = (MainActivity) getActivity();

		Button btnBilan = listView.findViewById(R.id.btnCreate);
		btnBilan.setText(mainAct.messages.get("bilanSheet"));
		btnBilan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(),BilanCirActivity.class);
				startActivity(i);
			}
		});

		return listView;
	}

	/**
	 * Executed when all the application elements are loaded. The view has just been created. The
	 * reportAdapter located in MainActivity is attached to the RecyclerView located in
	 * MainActivity. This allows to display the reports saved in the application. <br>
	 * Exécuté quand tous les éléments de l'application ont chargé. La vue vient juste d'être
	 * créé. Le reportAdapter localisé dans MainActivity est attaché à la RecyclerView localisée
	 * dans MainActivity. Cela permet d'afficher les rapports enregistrés dans l'application.
	 * @param view The View of the list of reports. <br> La vue de la liste des rapports.
	 * @param savedInstanceState The instance saved state. <br> L'instance de la sauvegarde d'état.
	 */
	@Override
	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		mainAct.listBilans = listView.findViewById(R.id.listBilans);
		mainAct.listBilans.setLayoutManager(new LinearLayoutManager(mainAct));
		mainAct.listBilans.setAdapter(mainAct.bilanAdapter);
	}

	@Override
	public void onConfigurationChanged(@NonNull Configuration config) {
		super.onConfigurationChanged(config);
	}
}
