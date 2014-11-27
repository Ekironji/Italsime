package com.ekironji.italsime.fragment;

import java.util.ArrayList;

import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.R;
import com.ekironji.italsime.Modello.ModelliListAdapter;
import com.ekironji.italsime.Modello.Modello;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class ModelsListFragment extends Fragment{
	
	final String DEBUG_TAG = "ModelsListFragment";
	ListView mListViewAriaPulita;
	ArrayList<Modello> listaModelli;
	
	int ariaType = -1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle saved){
		View view = inflater.inflate(R.layout.fragment_ariapulita, group, false);
		
		mListViewAriaPulita = (ListView) view.findViewById(R.id.listViewAriaPulita);
		
		mListViewAriaPulita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(DEBUG_TAG, listaModelli.get(position).toString());
			}
		});
		
		MainActivity.database.open();
		
//		listaModelli = MainActivity.database.getModelsByFilteredSearch(Modello.ARIA_PULITA, 
//				300, 600, 0, 1000);
		ariaType = getArguments().getInt(MainActivity.KEY_PASSARIATYPE);
		
		if (ariaType == Modello.ARIA_PULITA){
			listaModelli = MainActivity.database.getAllAriaPulitaModels();
		} else if (ariaType == Modello.ARIA_SPORCA) {
			listaModelli = MainActivity.database.getAllAriaSporcaModels();
		} else if (ariaType == -1){
			
		}
		mListViewAriaPulita.setAdapter(new ModelliListAdapter(getActivity(), listaModelli));
		
		MainActivity.database.close();
		
		return view;
	}

}
