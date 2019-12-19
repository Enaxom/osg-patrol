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

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

	private final List<Report> list;
	private LayoutInflater inflater;
	private Context context;
	private OnItemClickListener clickListener;

	ReportAdapter(Context context, List<Report> list, OnItemClickListener listener) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
		this.clickListener = listener;
	}

	class ReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private TextView txtSaveDate, txtNature, txtStartDate;
		private ImageView imgDelete;

		ReportViewHolder(View itemView) {
			super(itemView);
			context = itemView.getContext();
			txtSaveDate = itemView.findViewById(R.id.tvDateSave);
			txtNature = itemView.findViewById(R.id.tvNature);
			txtStartDate = itemView.findViewById(R.id.tvStartDate);
			imgDelete = itemView.findViewById(R.id.delete);
			itemView.setOnClickListener(this);
		}

		void bindReport(Report report) {
			txtSaveDate.setText(report.getDateSave());
			txtNature.setText(report.getNature().toString());
			txtStartDate.setText(report.getDateTimeStart());
		}

		@Override
		public void onClick(View view) {
			if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
		}
	}

	@NonNull
	@Override
	public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.item_report, parent, false);
		ReportViewHolder viewHolder = new ReportViewHolder(view);
		view.setTag(viewHolder);
		view.setTag(R.id.tvDateSave, viewHolder.txtSaveDate);
		view.setTag(R.id.tvNature, viewHolder.txtNature);
		view.setTag(R.id.tvStartDate, viewHolder.txtStartDate);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull ReportViewHolder holder, final int position) {
		holder.bindReport(list.get(position));

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

	@Override
	public int getItemCount() {
		return list.size();
	}

	void setClickListener(OnItemClickListener itemClickListener) {
		this.clickListener = itemClickListener;
	}

	@Override
	public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}
}
