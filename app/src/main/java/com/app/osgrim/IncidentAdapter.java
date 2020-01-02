package com.app.osgrim;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.osgrim.data.Incident;

import java.util.ArrayList;
import java.util.List;

public class IncidentAdapter extends RecyclerView.Adapter<IncidentAdapter.IncidentViewHolder> {

	private final List<Incident> list;
	private LayoutInflater inflater;
	private Context context;
	private ViewGroup vg;

	IncidentAdapter(Context context, List<Incident> list) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
	}

	class IncidentViewHolder extends RecyclerView.ViewHolder {
		private TextView textIncident, textNbr;
		private CheckBox checkbox;

		IncidentViewHolder(View itemView) {
			super(itemView);
			context = itemView.getContext();
			textIncident = itemView.findViewById(R.id.nameIncident);
			textNbr = itemView.findViewById(R.id.nbrIncident);
			checkbox = itemView.findViewById(R.id.checkIncident);
		}

		void bindIncident(Incident incident) {
			textIncident.setText(incident.getName());
			checkbox.setChecked(incident.isSelected());

			if (incident.isSelected()) {
				textNbr.setText(String.valueOf(incident.getNbrIncident()));
				textNbr.setTypeface(textNbr.getTypeface(), Typeface.BOLD);
				textIncident.setTypeface(textNbr.getTypeface(), Typeface.BOLD);
			} else {
				textNbr.setText("");
			}
		}
	}

	public IncidentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.item_incident, parent, false);
		IncidentViewHolder viewHolder = new IncidentViewHolder(view);
		view.setTag(viewHolder);
		view.setTag(R.id.nameIncident, viewHolder.textIncident);
		view.setTag(R.id.nbrIncident, viewHolder.textNbr);
		view.setTag(R.id.checkIncident, viewHolder.checkbox);
		vg = parent;

		return viewHolder;
	}

	private void openPopup(ViewGroup v, IncidentViewHolder vh, final int index) {
		View view = LayoutInflater.from(context).inflate(R.layout.pop_up_incident, null, true);
		int width = context.getResources().getDisplayMetrics().widthPixels;
		int height = context.getResources().getDisplayMetrics().heightPixels;
		int newWidth = (width > height) ? height/2 + height/3 : width/2 + width/3;
		double ratio = (double) height/width;
		int newHeight = (ratio < 1.5 || height < 1000) ? newWidth - newWidth/4 : newWidth/2;
		if(((MainActivity) context).inputFragment.getTagStr().equals("tablet"))
			newHeight -= newWidth/5;

		final PopupWindow pw = new PopupWindow(view, newWidth, newHeight);
		Button btnMinus = view.findViewById(R.id.btnMinus);
		Button btnPlus = view.findViewById(R.id.btnPlus);
		Button btnClose = view.findViewById(R.id.btnClose);
		TextView titleIncident = view.findViewById(R.id.tvTitleIncident);
		TextView nbrIncident = view.findViewById(R.id.tvNbrIncident);
		final TextView tvNbrIncident = view.findViewById(R.id.digitIncident);
		tvNbrIncident.setText("1");
		pw.setFocusable(true);

		titleIncident.setText(((MainActivity) context).messages.get("titleNbIncident"));
		nbrIncident.setText(((MainActivity) context).messages.get("nbIncident"));
		btnClose.setText(((MainActivity) context).messages.get("ok"));

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

		pw.showAtLocation(v, Gravity.CENTER,0,0);

		final TextView tvNbr = vh.textNbr;
		final CheckBox cb = vh.checkbox;
		pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				if (cb.isChecked()) {
					tvNbr.setText(tvNbrIncident.getText());
					try {
						int nb = Integer.valueOf(tvNbr.getText().toString());
						list.get(index).setNbrIncident(nb);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				} else {
					list.get(index).setNbrIncident(0);
				}
			}
		});
	}

	@Override
	public void onBindViewHolder(final IncidentViewHolder holder, final int position) {
		holder.checkbox.setTag(position);
		holder.bindIncident(list.get(position));

		final CheckBox cb = holder.checkbox;
		final TextView tvIncident = holder.textIncident;
		final TextView tvNbr = holder.textNbr;

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

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	Incident getItem(int position) {
		return list.get(position);
	}

	void setSelected(int position, boolean isSelected, int numberIncident) {
		Incident incident = list.get(position);
		incident.setSelected(isSelected);
		incident.setNbrIncident(numberIncident);
	}

	List<Incident> getSelectedItems() {
		List<Incident> incidents = new ArrayList<>();

		for (Incident i : list)
			if (i.isSelected())
				incidents.add(i);

		return incidents;
	}
}
