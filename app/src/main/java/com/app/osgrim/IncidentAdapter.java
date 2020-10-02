package com.app.osgrim;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.osgrim.data.Incident;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * IncidentAdapter class. Used to display the incidents on the recyclerView (the checkbox list)
 * on the report input. <br>
 * Classe IncidentAdapter. Utilisée pour afficher les incidents sur la recyclerView (la liste de
 * cases à cocher) sur la saisie du rapport.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class IncidentAdapter extends RecyclerView.Adapter<IncidentAdapter.IncidentViewHolder> {

	/**
	 * The list of incidents to display. <br>
	 * La liste des incidents à afficher.
	 */
	private final List<Incident> list;

	/**
	 * The layout of each item in the list. Will have the value of item_incident. <br>
	 * Le layout de chaque élément dans la liste. Aura la valeur de item_incident (fichier xml
	 * dans layout).
	 */
	private LayoutInflater inflater;

	/**
	 * The application context. <br>
	 * Le contexte de l'application.
	 */
	private Context context;

	/**
	 * The application ViewGroup. Needed to display a popup when an incident is checked. <br>
	 * Le ViewGroup de l'application. Nécessaire pour afficher une popup quand un incident est
	 * coché.
	 */
	private ViewGroup vg;

	/**
	 * Creates an IncidentAdapter. The RecyclerView used in the report input tab needs an adapter
	 * to display a list of Incident. <br>
	 * Créé un IncidentAdapter. La RecyclerView utilisée dans l'onglet saisie du rapport
	 * nécessite un adaptateur pour afficher une liste des objets Incident.
	 * @param context The application context. <br> Le contexte de l'application.
	 * @param list The Incident list to display. <br> La liste d'Incident à afficher.
	 */
	IncidentAdapter(Context context, List<Incident> list) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
	}

	/**
	 * The IncidentViewHolder class relates to one Incident object. <br>
	 * La classe IncidentViewHolder concerne un objet Incident.
	 */
	class IncidentViewHolder extends RecyclerView.ViewHolder {

		/**
		 * textIncident: The label of the incident's name. Le libellé du nom de l'incident. <br>
		 * textNbr: The label of the number of incidents. Le libellé du nombre d'anomalies.
		 */
		private TextView textIncident, textNbr;

		/**
		 * The checkbox to indicate if an incident is selected or not. <br>
		 * La case à cocher pour indiquer si un incident est sélectionné ou pas.
		 */
		private CheckBox checkbox;

		/**
		 * Constructor of the IncidentViewHolder. Get the elements of the layout item_incident. <br>
		 * Constructeur de IncidentViewHolder. Récupère les éléments du layout item_incident.
		 * @param itemView The itemView which is the item_incident layout.
		 */
		IncidentViewHolder(View itemView) {
			super(itemView);
			context = itemView.getContext();
			textIncident = itemView.findViewById(R.id.nameIncident);
			textNbr = itemView.findViewById(R.id.nbrIncident);
			checkbox = itemView.findViewById(R.id.checkIncident);
		}

		/**
		 * Links the Incident object and its data to the displayed element. <br>
		 * Lie l'objet Incident et ses données aux éléments affichés.
		 * @param incident The incident to display. <br> L'incident à afficher.
		 */
		void bindIncident(Incident incident) {
			textIncident.setText(incident.getName());
			checkbox.setChecked(incident.isSelected());

			// If the incident is selected, there is at least 1 displayed on the number of
			// incidents (TextView textNbr).
			// Si l'incident est sélectionné, il y a au moins 1 d'affiché sur le nombre
			// d'incidents (TextView textNbr).
			if (incident.isSelected()) {
				textNbr.setText(String.valueOf(incident.getNbrIncident()));
				textNbr.setTypeface(textNbr.getTypeface(), Typeface.BOLD);
				textIncident.setTypeface(textNbr.getTypeface(), Typeface.BOLD);
			} else {
				textNbr.setText("");
			}
		}
	}

	/**
	 * Method called when the ViewHolder is created. The layout elements are linked to the
	 * IncidentViewHolder class attributes. <br>
	 * Méthode appelée quand le ViewHolder est créé. Les éléments du layout sont liés aux
	 * attributs de la classe IncidentViewHolder.
	 * @param parent The ViewGroup of this view, which is its parent. <br> Le ViewGroup de cette
	 *                 vue, qui est son parent.
	 * @param viewType The viewType. Not used. <br> Non utilisé.
	 * @return The IncidentViewHolder created to display an Incident. <br> L'IncidentViewHolder créé
	 * pour afficher un incident.
	 */
	@NotNull
	public IncidentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.item_incident, parent, false);
		IncidentViewHolder viewHolder = new IncidentViewHolder(view);
		view.setTag(viewHolder);
		view.setTag(R.id.nameIncident, viewHolder.textIncident);
		view.setTag(R.id.nbrIncident, viewHolder.textNbr);
		view.setTag(R.id.checkIncident, viewHolder.checkbox);
		vg = parent;

		return viewHolder;
	}

	/**
	 * Method executed when an incident is selected. The user is asked to choose the number of
	 * incidents. <br>
	 * Méthode exécutée quand un incident est sélectionné. L'utilisateur est invité à choisir le
	 * nombre d'anomalies.
	 * @param v The ViewGroup, the view's parent. <br> Le ViewGroup, le parent de la vue.
	 * @param vh The IncidentViewHolder corresponding to the incident the user checked. <br>
	 *              L'IncidentViewHolder correspondant à l'incident que l'utilisateur a coché.
	 * @param index The index of the incident in the incidents list. <br> L'index de l'incident dans
	 *                 la liste des incidents.
	 */
	private void openPopup(ViewGroup v, IncidentViewHolder vh, final int index) {
		// Get the popup to display, which the layout is pop_up_incident.xml
		// Récupération de la popup à afficher, qui a comme layout pop_up_incident.xml
		View view = LayoutInflater.from(context).inflate(R.layout.pop_up_incident,
				new LinearLayout(context), true);

		// Calculating the dimensions of the popup according to the screen size, can be improved
		// Calcul des dimentions de la popup selon la taille d'écran, peut être amélioré
		int width = context.getResources().getDisplayMetrics().widthPixels;
		int height = context.getResources().getDisplayMetrics().heightPixels;
		int newWidth = (width > height) ? height/2 + height/3 : width/2 + width/3;
		double ratio = (double) height/width;
		int newHeight = (ratio < 1.5 || height < 1000) ? newWidth - newWidth/4 : newWidth/2;
		if(((MainActivity) context).inputFragment.getTagStr().equals("tablet"))
			newHeight -= newWidth/5;

		// Creation of the PopupWindow, get the popup element
		// Création de la PopupWindow, récupération des éléments de la popup
		final PopupWindow pw = new PopupWindow(view, newWidth, newHeight);
		Button btnMinus = view.findViewById(R.id.btnMinus);
		Button btnPlus = view.findViewById(R.id.btnPlus);
		Button btnClose = view.findViewById(R.id.btnClose);
		TextView titleIncident = view.findViewById(R.id.tvTitleIncident);
		TextView nbrIncident = view.findViewById(R.id.tvNbrIncident);
		final TextView tvNbrIncident = view.findViewById(R.id.digitIncident);

		// If the user submit, the number of incidents is 1 by default
		// Si l'utilisateur valide, le nombre d'incidents est 1 par défaut
		tvNbrIncident.setText("1");
		pw.setFocusable(true);

		titleIncident.setText(((MainActivity) context).messages.get("titleNbIncident"));
		nbrIncident.setText(((MainActivity) context).messages.get("nbIncident"));
		btnClose.setText(((MainActivity) context).messages.get("ok"));

		// Action of the minus (to decrement) and plus (to increment) buttons
		btnMinus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int nb = Integer.parseInt(tvNbrIncident.getText().toString());
				if (nb > 1) {
					nb--;
					tvNbrIncident.setText(String.valueOf(nb));
				}
			}
		});

		btnPlus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int nb = Integer.parseInt(tvNbrIncident.getText().toString());
				nb++;
				tvNbrIncident.setText(String.valueOf(nb));
			}
		});

		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				pw.dismiss();
			}
		});

		// Displaying of the popup
		// Affichage de la popup
		pw.showAtLocation(v, Gravity.CENTER,0,0);

		final TextView tvNbr = vh.textNbr;
		final CheckBox cb = vh.checkbox;

		/*
		When the popup is closed, the number of incidents displayed on the list is updated, the
		incident is selected and the line is bold.
		Quand la popup est fermée, le nombre d'incidents affiché dans la liste est mis à jour,
		l'incident est sélectionné et la ligne est en gras.
		 */
		pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				if (cb.isChecked()) {
					tvNbr.setText(tvNbrIncident.getText());
					try {
						int nb = Integer.valueOf(tvNbr.getText().toString());
						list.get(index).setNbrIncident(nb); // The Incident object is updated
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				} else {
					list.get(index).setNbrIncident(0);
				}
			}
		});
	}

	/**
	 * Links the IncidentViewHolder and the incident of the list located at the position
	 * in parameter thanks to bindIncident(Incident). Set the click listener when an incident is
	 * selected. <br>
	 * Lie l'IncidentViewHolder et l'incident de la liste situé à la position en paramètre grâce
	 * à bindIncident(Incident). Définit l'écouteur de click quand un incident est sélectionné.
	 * @param holder The IncidentViewHolder displaying the incident. <br> L'IncidentViewHolder
	 *                  affichant l'incident.
	 * @param position The index of the incident in the incidents list. <br> L'index de l'incident
	 *                    dans la liste des incidents.
	 */
	@Override
	public void onBindViewHolder(final IncidentViewHolder holder, final int position) {
		holder.checkbox.setTag(position);
		holder.bindIncident(list.get(position));

		// Get the layout elements to use it in the click listener
		final CheckBox cb = holder.checkbox;
		final TextView tvIncident = holder.textIncident;
		final TextView tvNbr = holder.textNbr;

		/*
		When an item is touched, the checkbox will change its state and if the item is selected,
		the line will be bold and a popup will open to ask the number of incidents.
		If the item is deselected, the line is back to normal and the number of incidents
		disappears.

		Quand un item est touché, la case à cocher va changer d'état et si l'élément est
		sélectionné, la ligne sera en gras et une popup s'ouvrira pour demander le nombre
		d'incidents.
		Si l'élément est décoché, la ligne redevient normale et le nombre d'incidents disparait.
		 */
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				cb.setChecked(!cb.isChecked());
				//list.get(position).setSelected(cb.isChecked());
			}
		});

		holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				list.get(position).setSelected(cb.isChecked());
				if (cb.isChecked()) {
					tvIncident.setTypeface(tvIncident.getTypeface(), Typeface.BOLD);
					tvNbr.setTypeface(tvNbr.getTypeface(), Typeface.BOLD);
					openPopup(vg, holder, position);
				} else {
					tvIncident.setTypeface(null, Typeface.NORMAL);
					tvNbr.setText("");
				}
			}
		});
	}

	/**
	 * Get the number of incidents. <br>
	 * Renvoie le nombre d'incidents.
	 * @return The number of incidents. <br> Le nombre d'incidents.
	 */
	@Override
	public int getItemCount() {
		return list.size();
	}

	/**
	 * Executed when the adapter is attached to a RecyclerView. <br>
	 * Exécutée quand l'adaptateur est lié à une RecyclerView.
	 * @param recyclerView The RecyclerView to attach the adapter. <br> La RecyclerView à laquelle on
	 *                        attache l'adaptateur.
	 */
	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	/**
	 * Update the incident number position in the list, edit its number of incidents to 0 and
	 * deselect it. <br>
	 * Met à jour l'incident numéro position dans la liste, modifie son nombre d'anomalies à 0 et
	 * le désélectionne.
	 * @param position The incident index in the incidents list. <br> L'index de l'incident dans la
	 *                    liste des incidents.
	 */
	void setDeselected(int position) {
		Incident incident = list.get(position);
		incident.setSelected(false);
		incident.setNbrIncident(0);
	}

	/**
	 * Get all the selected incidents. Very useful when the report is finished and we've to get
	 * all the incidents selected by the user. <br>
	 * Récupère tous les incidents sélectionnés. Très utile quand le rapport est terminé et qu'on
	 * doit récupérer tous les incidents sélectionnés par l'utilisateur.
	 * @return The List of the selected Incident objects. <br> La liste des objets Incident
	 * sélectionnés.
	 */
	List<Incident> getSelectedItems() {
		List<Incident> incidents = new ArrayList<>();

		for (Incident i : list)
			if (i.isSelected())
				incidents.add(i);

		return incidents;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public int getItemViewType(int position) {
		return position;
	}
}
