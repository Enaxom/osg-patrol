package com.app.osgrim;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.osgrim.data.Intervenant;

import java.util.ArrayList;
import java.util.List;

public class IntervenantAdapter extends RecyclerView.Adapter<IntervenantAdapter.IntervenantViewHolder> {

	private final List<Intervenant> list;
	private LayoutInflater inflater;
	private Context context;

	public IntervenantAdapter(Context context, List<Intervenant> list) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
	}

	class IntervenantViewHolder extends RecyclerView.ViewHolder {
		private TextView textTeam, textInter;
		private CheckBox checkbox;

		public IntervenantViewHolder(View itemView) {
			super(itemView);
			context = itemView.getContext();
			textTeam = itemView.findViewById(R.id.nameTeam);
			textInter = itemView.findViewById(R.id.nameInter);
			checkbox = itemView.findViewById(R.id.checkIntervenant);
		}

		public void bindInter(Intervenant inter) {
			textTeam.setText(inter.getTeam().toString());
			textInter.setText(inter.getUser().toString());
			checkbox.setChecked(inter.isSelected());

			if (inter.isSelected()) {
				textTeam.setTypeface(textTeam.getTypeface(), Typeface.BOLD);
				textInter.setTypeface(textInter.getTypeface(), Typeface.BOLD);
			}
		}
	}

	@Override
	public IntervenantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.item_intervenant, parent, false);
		final IntervenantViewHolder viewHolder = new IntervenantViewHolder(view);
		view.setTag(viewHolder);
		view.setTag(R.id.nameTeam, viewHolder.textTeam);
		view.setTag(R.id.nameInter, viewHolder.textInter);
		view.setTag(R.id.checkService, viewHolder.checkbox);

		return viewHolder;
	}

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

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	Intervenant getItem(int position) {
		return list.get(position);
	}

	void setSelected(int position, boolean isSelected) {
		list.get(position).setSelected(isSelected);
	}

	List<Intervenant> getSelectedItems() {
		List<Intervenant> intervenants = new ArrayList<>();

		for (Intervenant i : list)
			if (i.isSelected())
				intervenants.add(i);

		return intervenants;
	}
}
