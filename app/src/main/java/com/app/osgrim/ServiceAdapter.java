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

import com.app.osgrim.data.Service;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * ServiceAdapter class. Used to display the services on the two recyclerView on the report input
 * tab. <br>
 * Classe ServiceAdapter. Utilisée pour afficher les services sur les recyclerView (les deux
 * listes de cases à cocher de service) sur la saisie du rapport.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

	/**
	 * The list of services to display. <br>
	 * La liste des services à afficher.
	 */
	private final List<Service> list;

	/**
	 * The layout of each item in the list. Will have the value of row. <br>
	 * Le layout de chaque élément dans la liste. Aura la valeur de row (fichier xml dans layout).
	 */
	private LayoutInflater inflater;

	/**
	 * Creates a ServiceAdapter. The RecyclerView used in the report input tab needs an adapter
	 * to display a list of Service. <br>
	 * Créé un ServiceAdapter. La RecyclerView utilisée dans l'onglet saisie de rapport nécessite
	 * un adaptateur pour afficher une liste des objets Service.
	 * @param context The applicaiton context. <br> Le contexte de l'application.
	 * @param list The Service list to display. <br> La liste de rapports à afficher.
	 */
	ServiceAdapter(Context context, List<Service> list) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}

	/**
	 * The ServiceViewHolder class relates to one Service object. <br>
	 * La classe ServiceViewHolder concerne un objet Service.
	 */
	class ServiceViewHolder extends RecyclerView.ViewHolder {

		/**
		 * text: The service's name. <br> Le nom du service.
		 */
		private TextView text;

		/**
		 * The checkbox to indicate if a service is selected or not. <br>
		 * La case à cocher pour insiquer si un service est sélectionné ou pas.
		 */
		private CheckBox checkbox;

		/**
		 * Constructor of the ServiceViewHolder. Get the elements of the layout row. <br>
		 * Constructeur de ServiceViewHolder. Récupère les éléments du layout row (fichier xml).
		 * @param itemView The itemView which is the row layout.
		 */
		ServiceViewHolder(View itemView) {
			super(itemView);
			text = itemView.findViewById(R.id.nameService);
			checkbox = itemView.findViewById(R.id.checkService);
		}

		/**
		 * Links the Service object and its data to the displayed element. <br>
		 * Lie l'objet Service et ses données aux éléments affichés.
		 * @param service The Service to display. <br> Le service à afficher.
		 */
		void bindService(Service service) {
			text.setText(service.getName());
			checkbox.setChecked(service.isSelected());
			if (service.isSelected())
				text.setTypeface(text.getTypeface(), Typeface.BOLD);
		}
	}

	/**
	 * Method called when the ViewHolder is created. The layout elements are linked to the
	 * ServiceViewHolder class attributes. <br>
	 * Méthode appelée quand le ViewHolder est créé. Les éléments du layout sont liés aux
	 * attributs de la classe ServiceViewHolder.
	 * @param parent The ViewGroup of this view, which is its parent. <br> Le ViewGroup de cette
	 *                  vue, qui est son parent.
	 * @param viewType The viewType. Not used. <br> Non utilisé.
	 * @return The ServiceViewHolder created to display a service. <br> Le ServiceViewHolder créé
	 * pour afficher un service.
	 */
	@NotNull
	public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.row, parent, false);
		ServiceViewHolder viewHolder = new ServiceViewHolder(view);
		view.setTag(viewHolder);
		view.setTag(R.id.nameService, viewHolder.text);
		view.setTag(R.id.checkService, viewHolder.checkbox);

		return viewHolder;
	}

	/**
	 * Links the ServiceViewHolder and the Service of the list located at the position in
	 * parameter thanks to bindService(Service). Set the click listener when a Service is selected. <br>
	 * Lie le ServiceViewHolder et le service de la liste situé à la position en paramètre grâce
	 * à bindService(Service). Définit l'écouteur de click quand un service est sélectionné.
	 * @param holder The ServiceViewHolder displaying the Service. <br> Le ServiceViewHolder affichant
	 *                 le service.
	 * @param position The index of the service in the services list. <br> L'index du service dans la
	 *                    liste des services.
	 */
	@Override
	public void onBindViewHolder(ServiceViewHolder holder, final int position) {
		holder.checkbox.setTag(position);
		holder.bindService(list.get(position));

		final CheckBox cb = holder.checkbox;
		final TextView tv = holder.text;

		holder.text.setOnClickListener(new View.OnClickListener() {
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
					tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
				} else {
					tv.setTypeface(null, Typeface.NORMAL);
				}
			}
		});
	}

	/**
	 * Get the number of services. <br>
	 * Récupère le nombre de services.
	 * @return The number of services. <br> Le nombre de services.
	 */
	@Override
	public int getItemCount() {
		return list.size();
	}

	/**
	 * Executed when the adapter is attached to a RecyclerView. <br>
	 * Exécutée quand l'adaptateur est lié à une RecyclerView.
	 * @param recyclerView The RecyclerView to attach the adapter. <br> La RecyclerView à
	 *                        laquelle on attache l'adaptateur.
	 */
	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	/**
	 * Get the service in the list of services at the position given in parameter. <br>
	 * Renvoie le service dans la liste de services situé à la position donnée en paramètre.
	 * @param position The index of the Service object in the list. <br> L'index de l'objet Service
	 *                    dans la liste.
	 * @return The Service at position position. <br> Le service à la position poition.
	 */
	Service getItem(int position) {
		return list.get(position);
	}

	/**
	 * Set the service at the position given in parameter in the services list to selected or
	 * deselected. <br>
	 * Met à jour le service à la position donnée en paramètre dans la liste des services et le
	 * sélectionne ou le désélectionne.
	 * @param position The service index in the services list. <br> L'index du service dans la liste
	 *                    des services.
	 * @param isSelected True if the service is selected, false if not. <br> Vrai si le service est
	 *                      sélectionné, faux sinon.
	 */
	void setSelected(int position, boolean isSelected) {
		list.get(position).setSelected(isSelected);
	}

	/**
	 * Get all the selected services. Very useful when the report is finished and we've got to
	 * get all the services selected by the user. <br>
	 * Récupère tous les services sélectionnés. Très utile quand le rapport est terminé et qu'on
	 * doit récupérer tous les services sélectionnés par l'utilisateur.
	 * @return The list of the selected Service objects. <br> La liste des objets Service
	 * sélectionnés.
	 */
	List<Service> getSelectedItems() {
		List<Service> services = new ArrayList<>();

		for (Service s : list)
			if (s.isSelected())
				services.add(s);

		return services;
	}
}
