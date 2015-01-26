package com.ekironji.italsime.Modello;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ekironji.italsime.R;

public class ModelliListAdapter extends BaseAdapter {
	private ArrayList<Modello> mModelli;
	private LayoutInflater mInflater;

	public ModelliListAdapter(Context context, ArrayList<Modello> listaModelli) {
		mInflater = LayoutInflater.from(context);
		mModelli = listaModelli;
	}

	public int getCount() {
		return mModelli.size();
	}

	public Modello getItem(int position) {
		return mModelli.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup vg;

		if (convertView != null) {
			vg = (ViewGroup) convertView;
		} else {
			vg = (ViewGroup) mInflater.inflate(R.layout.listview_model_item, null);

		}
		
		if (mModelli.get(position).getAriaType() == Modello.ARIA_PULITA) {
			vg.setBackgroundResource(R.drawable.italsimegreenahcg_list_selector_holo_light);
		} else if (mModelli.get(position).getAriaType() == Modello.ARIA_SPORCA) {
			vg.setBackgroundResource(R.drawable.italsimevioletahcg_list_selector_holo_light);
		}
		
		((TextView) vg.findViewById(R.id.textView_model_name)).setText(mModelli.get(position).getName());
		((TextView) vg.findViewById(R.id.textView_kw_value)).setText(String.valueOf(mModelli.get(position).getKw()));
		((TextView) vg.findViewById(R.id.textView_rpm_value)).setText(String.valueOf(mModelli.get(position).getRpm()));
				
		return vg;
	}
}