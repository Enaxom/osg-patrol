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

import com.app.osgrim.data.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

	private final List<Service> list;
	private LayoutInflater inflater;
	private Context context;

	ServiceAdapter(Context context, List<Service> list) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
	}

	class ServiceViewHolder extends RecyclerView.ViewHolder {
		private TextView text;
		private CheckBox checkbox;

		ServiceViewHolder(View itemView) {
			super(itemView);
			context = itemView.getContext();
			text = itemView.findViewById(R.id.nameService);
			checkbox = itemView.findViewById(R.id.checkService);
		}

		void bindService(Service service) {
			text.setText(service.getName());
			checkbox.setChecked(service.isSelected());
			if (service.isSelected())
				text.setTypeface(text.getTypeface(), Typeface.BOLD);
		}
	}

	@Override
	public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.row, parent, false);
		ServiceViewHolder viewHolder = new ServiceViewHolder(view);
		view.setTag(viewHolder);
		view.setTag(R.id.nameService, viewHolder.text);
		view.setTag(R.id.checkService, viewHolder.checkbox);

		return viewHolder;
	}

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

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	Service getItem(int position) {
		return list.get(position);
	}

	void setSelected(int position, boolean isSelected) {
		list.get(position).setSelected(isSelected);
	}

	List<Service> getSelectedItems() {
		List<Service> services = new ArrayList<>();

		for (Service s : list)
			if (s.isSelected())
				services.add(s);

		return services;
	}
}
