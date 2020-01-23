package com.app.osgrim;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.osgrim.data.Intervenant;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * IntervenantAdapter class. Used to display the contributors on the recyclerView (the checkbox
 * list) on the report input. <br>
 * Classe IntervenantAdapter. Utilisée pour afficher les intervenants sur la recyclerView (la
 * liste de cases à cocher) sur la saisie du rapport.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class IntervenantAdapter extends RecyclerView.Adapter<IntervenantAdapter.IntervenantViewHolder> {

	/**
	 * The list of contributors to display. <br>
	 * La liste des intervenants à afficher.
	 */
	private final List<Intervenant> list;

	/**
	 * The layout of each item in the list. Will have the value of item_intervenant. <br>
	 * Le layout de chaque élément dans la liste. Aura la valeur de item_intervenant (fichier xml
	 * dans layout).
	 */
	private LayoutInflater inflater;

	/**
	 * Creates an IntervenantAdapter. The RecyclerView used in the report input tab needs an
	 * adapter to display a list of Intervenant. <br>
	 * Créé un IntervenantAdapter. La RecyclerView utilisée dans l'onglet saisie du rapport
	 * nécessite un adaptateur pour afficher une liste des objets Intervenant.
	 * @param context The application context. <br> Le contexte de l'application.
	 * @param list The Intervenant list to display. <br> La liste d'Intervenant à afficher.
	 */
	IntervenantAdapter(Context context, List<Intervenant> list) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}

	/**
	 * The IntervenantViewHolder class relates to one Intervenant object. <br>
	 * La classe IntervenantViewHolder concerne un objet Intervenant.
	 */
	class IntervenantViewHolder extends RecyclerView.ViewHolder {

		/**
		 * textTeam: The label of the contributor's team. Le libellé de l'équipe de l'intervenant. <br>
		 * textInter: The label of the contributor's name (fname and lname). Le libellé du nom de
		 * l'intervenant (prénom et nom).
		 */
		private TextView textTeam, textInter;

		/**
		 * The checkbox to indicate if an intervenant is selected or not. <br>
		 * La case à cocher pour indiquer si un intervenant est sélectionné ou pas.
		 */
		private CheckBox checkbox;

		/**
		 * Constructor of the IntervenantViewHolder. Get the elements of the layout
		 * item_intervenant. <br>
		 * Constructeur de IntervenantViewHolder. Récupère les éléments du layout
		 * item_intervenant.
		 * @param itemView The itemView which is the item_intervenant layout.
		 */
		IntervenantViewHolder(View itemView) {
			super(itemView);
			textTeam = itemView.findViewById(R.id.nameTeam);
			textInter = itemView.findViewById(R.id.nameInter);
			checkbox = itemView.findViewById(R.id.checkIntervenant);
		}

		/**
		 * Links the Intervenant object and its data to the displayed element. <br>
		 * Lie l'objet Intervenant et ses données aux éléments affichés.
		 * @param inter The Intervenant to display. <br> L'intervenant à afficher.
		 */
		void bindInter(Intervenant inter) {
			textTeam.setText(inter.getTeam().toString());
			textInter.setText(inter.getUser().toString());
			checkbox.setChecked(inter.isSelected());

			if (inter.isSelected()) {
				textTeam.setTypeface(textTeam.getTypeface(), Typeface.BOLD);
				textInter.setTypeface(textInter.getTypeface(), Typeface.BOLD);
			}
		}
	}

	/**
	 * Method called when the ViewHolder is created. The layout elements are linked to the
	 * ItervenantViewHolder class attributes. <br>
	 * Méthode appelée quand le ViewHolder est créé. Les éléments du layout sont liés aux
	 * attributs de la classe IntervenantViewHolder.
	 * @param parent The ViewGroup of this view, which is its parent. <br> Le ViewGroup de cette vue,
	 *                  qui est son parent.
	 * @param viewType The viewType. Not used. <br> Non utilisé.
	 * @return The IntervenantViewHolder created to display an intervenant. <br> L'IntervenantViewHolder
	 * créé pour afficher un intervenant.
	 */
	@NotNull
	public IntervenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.item_intervenant, parent, false);
		final IntervenantViewHolder viewHolder = new IntervenantViewHolder(view);
		view.setTag(viewHolder);
		view.setTag(R.id.nameTeam, viewHolder.textTeam);
		view.setTag(R.id.nameInter, viewHolder.textInter);
		view.setTag(R.id.checkService, viewHolder.checkbox);

		return viewHolder;
	}

	/**
	 * Links the IntervenantViewHolder and the Intervenant of the list located at the position in
	 * parameter thanks to bindIntervenant(Inter). Set the click listener when an Intervenant is
	 * selected. <br>
	 * Lie l'IntervenantViewHolder et l'intervenant de la liste situé à la position en paramètre
	 * grâce à bindIntervenant(inter). Définit l'écouteur de click quand un intervenant est
	 * sélectionné.
	 * @param holder The IntervenantViewHolder displaying the Intervenant. <br>
	 *                  L'IntervenantViewHolder affichant l'intervenant.
	 * @param position The index of the intervenant in the intervenants list. <br> L'index de
	 *                    l'intervenant dans la liste des intervenants.
	 */
	@Override
	public void onBindViewHolder(IntervenantViewHolder holder, final int position) {
		holder.checkbox.setTag(position);
		holder.bindInter(list.get(position));

		final CheckBox cb = holder.checkbox;
		final TextView tvTeam = holder.textTeam;
		final TextView tvInter = holder.textInter;

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				cb.setChecked(!cb.isChecked());
				list.get(position).setSelected(cb.isChecked());
			}
		});

		holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if (cb.isChecked()) {
					tvTeam.setTypeface(tvTeam.getTypeface(), Typeface.BOLD);
					tvInter.setTypeface(tvInter.getTypeface(), Typeface.BOLD);
				} else {
					tvTeam.setTypeface(null, Typeface.NORMAL);
					tvInter.setTypeface(null, Typeface.NORMAL);
				}
			}
		});
	}

	/**
	 * Get the number of contributors. <br>
	 * Renvoie le nombre d'intervenants.
	 * @return The number of contributors. <br> Le nombre d'intervenants.
	 */
	@Override
	public int getItemCount() {
		return list.size();
	}

	/**
	 * Executed when the adapter is attached to a RecyclerView. <br>
	 * Exécutée quand l'adaptateur est lié à une RecyclerView.
	 * @param recyclerView The RecyclerView to attach the adapter. <br> La RecyclerView à
	 *                        laquelle on
	 *                        attache l'adaptateur.
	 */
	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	/**
	 * Get the intervenant in the list of intervenants at the position given in parameter. <br>
	 * Renvoie l'intervenant dans la liste d'intervenants situé à la position donnée en paramètre.
	 * @param position The index of the Intervenant object in the list. <br> L'index de l'objet
	 *                    Intervenant dans la liste.
	 * @return The Intervenant at position position. <br> L'intervenant à la position position.
	 */
	Intervenant getItem(int position) {
		return list.get(position);
	}

	/**
	 * Uptades the intervenant number position in the list and selects it if isSelected is true. <br>
	 * Met à jour l'intervenant numéro position dans la liste et le sélectionne si isSelected est
	 * vrai.
	 * @param position The intervenant index in the intervenants list. <br> L'index de l'intervenant
	 *                    dans la liste des intervenants.
	 * @param isSelected True if the intervenant is selected, false if not.  <br>Vrai si l'intervenant
	 *                     est sélectionné, faux sinon.
	 */
	void setSelected(int position, boolean isSelected) {
		list.get(position).setSelected(isSelected);
	}

	/**
	 * Get all the selected intervenants. Very useful when the report is finished and we've got
	 * to get all the intervenants selected by the user. <br>
	 * Récupère tous les intervenants sélectionnés. Très utile quand le rapport est terminé et
	 * qu'on doit récupérer tous les intervenants sélectionnés par l'utilisateur.
	 * @return The list of the selected Intervenant objects. <br> La liste des objets Intervenant
	 * sélectionnés.
	 */
	List<Intervenant> getSelectedItems() {
		List<Intervenant> intervenants = new ArrayList<>();

		for (Intervenant i : list)
			if (i.isSelected())
				intervenants.add(i);

		return intervenants;
	}
}
