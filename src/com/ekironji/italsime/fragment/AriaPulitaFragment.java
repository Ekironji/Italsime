package com.ekironji.italsime.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.R;
import com.ekironji.italsime.Modello.ModelliListAdapter;
import com.ekironji.italsime.Modello.Modello;

public class AriaPulitaFragment extends Fragment {
	
	ListView mListViewAriaPulita;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle saved){
		View view = inflater.inflate(R.layout.fragment_ariapulita, group, false);
		
		mListViewAriaPulita = (ListView) view.findViewById(R.id.listViewAriaPulita);
		
		mListViewAriaPulita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});
		
		MainActivity.database.open();
		
		ArrayList<Modello> listaModelli = MainActivity.database.getAllAriaPulitaModels();
		
		mListViewAriaPulita.setAdapter(new ModelliListAdapter(getActivity(), listaModelli));
		
		MainActivity.database.close();
		
		return view;
	}

}
