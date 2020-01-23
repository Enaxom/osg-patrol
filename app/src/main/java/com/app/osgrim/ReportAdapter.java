package com.app.osgrim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.osgrim.data.Report;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * ReportAdapter class. Used to display the reports on the recyclerView on the report list tab. <br>
 * Classe ReportAdapter. Utilisée pour afficher les rapports sur la recyclerView sur l'onglet de
 * liste de rapports.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

	/**
	 * The reports list to display. <br>
	 * La liste des rapports à afficher.
	 */
	private final List<Report> list;

	/**
	 * The layout of each item in the list. Will have the value of item_report. <br>
	 * Le layout de chaque élément dans la liste. Aura la valeur de item_report (fichier xml dans
	 * layout).
	 */
	private LayoutInflater inflater;

	/**
	 * The application context. <br>
	 * Le contexte de l'application.
	 */
	private Context context;

	/**
	 * The clickListener variable allow to link a click behavior to the item displayed in the
	 * list. In our case, the report clicked will be displayed in the input report tab. <br>
	 * La variable clickListener permet de lier un comportement de click à l'élément affiché dans
	 * la liste. Dans notre cas, le rapport clické sera affiché dans l'onglet de saisie de rapport.
	 */
	private OnItemClickListener clickListener;

	/**
	 * Creates a ReportAdapter. The RecyclerView used in the report list tab needs an adapter to
	 * display a list of Report. <br>
	 * Créé un ReportAdapter. La RecyclerView utilisée dans l'onglet liste des rapports nécessite
	 * un adaptateur pour afficher une liste des objets Report.
	 * @param context The application context. <br> Le contexte de l'application.
	 * @param list The Report list to display. <br> La liste de rapports à afficher.
	 * @param listener The OnItemClickListener associated to the adapter. <br> Le OnItemClickListener
	 *                    associé à l'adaptateur.
	 */
	ReportAdapter(Context context, List<Report> list, OnItemClickListener listener) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
		this.clickListener = listener;
	}

	/**
	 * The ReportViewHolder class relates to one Report object. <br>
	 * La classe ReportViewHolder concerne un objet Rapport.
	 */
	class ReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		/**
		 * txtSaveDate: The date and time at which the report was saved. La date et l'heure à
		 * laquelle le rapport a été enregistré. <br>
		 * txtNature: The operation nature of the report. La nature de l'opération du rapport. <br>
		 * txtStartDate: The date and time at which the operation began. La date et l'heure du
		 * début de l'opération.
		 */
		private TextView txtSaveDate, txtNature, txtStartDate;

		/**
		 * The trash icon that allows the user to delete the report by clicking on it. <br>
		 * L'icône de poubelle qui permet à l'utilisateur de supprimer un rapport en cliquant
		 * dessus.
		 */
		private ImageView imgDelete;

		/**
		 * Constructor of the ReportViewHolder. Get the elements of the layout item_report. <br>
		 * Constructeur de ReportViewHolder. Récupère les éléments du layout item_report.
		 * @param itemView The itemView which is the item_report layout.
		 */
		ReportViewHolder(View itemView) {
			super(itemView);
			context = itemView.getContext();
			txtSaveDate = itemView.findViewById(R.id.tvDateSave);
			txtNature = itemView.findViewById(R.id.tvNature);
			txtStartDate = itemView.findViewById(R.id.tvStartDate);
			imgDelete = itemView.findViewById(R.id.delete);
			itemView.setOnClickListener(this);
		}

		/**
		 * Links the Report object and its data to the displayed element. <br>
		 * Lie l'objet Report et ses données aux élements affichés.
		 * @param report The report to display. <br> L'incident à afficher.
		 */
		void bindReport(Report report) {
			txtSaveDate.setText(report.getDateSave());
			txtNature.setText(report.getNature().toString());
			txtStartDate.setText(report.getDateTimeStart());
		}

		/**
		 * The OnClick method calls the onClick method of the clickListener given in parameter,
		 * so that we can also give the position of the report in the list and display it. <br>
		 * La méthode OnClick appelle la méthode onClick du clickListener donné en paramètre,
		 * ainsi on peut aussi donner la position du rapport dans la liste et l'afficher.
		 * @param view The concerned view. <br> La vue concernée.
		 */
		@Override
		public void onClick(View view) {
			if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
		}
	}

	/**
	 * Method called when the ViewHolder is created. The layout elements are linked to the
	 * ReportViewHolder class attributes. <br>
	 * Méthode appelée quand le ViewHolder est créé. Les éléments du layout sont liés aux
	 * attributs de la classe ReportViewHolder.
	 * @param parent The ViewGroup of this view, which is its parent. <br> Le ViewGroup de cette
	 *                  vue, qui est son parent.
	 * @param viewType The viewTyoe. Not used. <br> Non utilisé.
	 * @return The ReportViewHolder created to display a Report. <br> Le ReportViewHolder créé pour
	 * afficher un rapport.
	 */
	@NonNull
	public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.item_report, parent, false);
		ReportViewHolder viewHolder = new ReportViewHolder(view);
		view.setTag(viewHolder);
		view.setTag(R.id.tvDateSave, viewHolder.txtSaveDate);
		view.setTag(R.id.tvNature, viewHolder.txtNature);
		view.setTag(R.id.tvStartDate, viewHolder.txtStartDate);

		return viewHolder;
	}

	/**
	 * Links the ReportViewHolder and the report of the list located at the position in parameter
	 * thanks to bindReport(Report). Set the click listener of the trash icon when the user wants
	 * to delete a report. <br>
	 * Lie le ReportViewHolder et le rapport de la liste situé à la position en paramètre grâce à
	 * bindReport(Report). Définit l'écouteur de click de l'icône de poubelle quand l'utilisateur
	 * veut supprimer un rapport.
	 * @param holder The ReportViewHolder displaying the report. <br> Le ReportViewHolder affichant le
	 *                 rapport.
	 * @param position The index of the report in the reports list. <br> L'index du rapport dans la
	 *                    liste des rapports.
	 */
	@Override
	public void onBindViewHolder(@NonNull ReportViewHolder holder, final int position) {
		holder.bindReport(list.get(position));

		/*
		When a user click on the trash icon, a popup window will alert him asking him if he's
		sure to delete it. If no, the popup dismisses. If yes, the report is deleted.
		Quand un utilisateur clique sur l'icône de poubelle, une fenêtre popup l'alertera en lui
		demandant s'il est sûr de supprimer le rapport. Si non, la popup se ferme. Si oui, le
		rapport est supprimé.
		 */
		holder.imgDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog alertDialog = new AlertDialog.Builder(context).create();
				alertDialog.setMessage(((MainActivity) context).messages.get("confirmDelete"));

				alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, ((MainActivity) context).messages.get("yes"),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								list.remove(position);
								notifyItemRemoved(position);
								notifyItemRangeChanged(position, list.size());
								Toast.makeText(context, ((MainActivity) context).messages.get("deleted"), Toast.LENGTH_SHORT).show();
								((MainActivity) context).needClear.setBoo(!((MainActivity) context).needClear.isBoo());
							}
						});

				alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, ((MainActivity) context).messages.get("no"),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								dialogInterface.dismiss();
							}
						});

				alertDialog.show();

				TextView messageView = alertDialog.findViewById(android.R.id.message);
				messageView.setGravity(Gravity.CENTER);

				Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

				LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
				layoutParams.weight = 10;
				btnPositive.setLayoutParams(layoutParams);
				btnNegative.setLayoutParams(layoutParams);
			}
		});
	}

	/**
	 * Get the number of reports. <br>
	 * Renvoie le nombre de rapports.
	 * @return The number of reports. <br> Le nombre de rapports.
	 */
	@Override
	public int getItemCount() {
		return list.size();
	}

	/**
	 * Set the value of the click listener associated to the adapter. <br>
	 * Fixe la valeur de l'écouteur de click associé à l'adaptateur.
	 * @param itemClickListener The itemClickListener associated to the adapter. <br>
	 *                             L'itemClickListener associé à l'adaptateur.
	 */
	void setClickListener(OnItemClickListener itemClickListener) {
		this.clickListener = itemClickListener;
	}

	/**
	 * Executed when the adapter is attached to a RecyclerView. <br>
	 * Exécutée quand l'adaptateur est lié à une RecyclerView.
	 * @param recyclerView The RecyclerView to attach the adapter. <br> La RecyclerView à
	 *                        laquelle on attache l'adaptateur.
	 */
	@Override
	public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}
}
