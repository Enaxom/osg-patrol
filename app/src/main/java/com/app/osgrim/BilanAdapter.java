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

import com.app.osgrim.data.Bilan;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BilanAdapter extends RecyclerView.Adapter<BilanAdapter.BilanViewHolder> {

    private final List<Bilan> list;

    private LayoutInflater inflater;

    private Context context;

    private OnItemClickListener clickListener;

    BilanAdapter(Context context, List<Bilan> list, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.clickListener = listener;
    }

    class BilanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvDateSave;

        private ImageView imgDelete;

        BilanViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            tvDateSave = itemView.findViewById(R.id.tvDateSave);
            imgDelete = itemView.findViewById(R.id.delete);
            itemView.setOnClickListener(this);
        }

        void bindBilan(Bilan bilan) {
            tvDateSave.setText(bilan.getDateTime());
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    @NotNull
    public BilanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_bilan, parent, false);
        BilanViewHolder viewHolder = new BilanViewHolder(view);
        view.setTag(viewHolder);
        view.setTag(R.id.tvDateSave, viewHolder.tvDateSave);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BilanAdapter.BilanViewHolder holder, final int position) {
        holder.bindBilan(list.get(position));

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
                alertDialog.setMessage(((MainActivity) context).messages.get("confirmDeleteBil"));

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, ((MainActivity) context).messages.get("yes"),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                                Toast.makeText(context, ((MainActivity) context).messages.get("bilDeleted"), Toast.LENGTH_SHORT).show();
                                ((MainActivity) context).needClearBilan.setBoo(!((MainActivity) context).needClearBilan.isBoo());
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
