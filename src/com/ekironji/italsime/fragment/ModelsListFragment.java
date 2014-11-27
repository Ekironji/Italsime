package com.ekironji.italsime.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.R;
import com.ekironji.italsime.Modello.ModelliListAdapter;
import com.ekironji.italsime.Modello.Modello;

public class ModelsListFragment extends Fragment{
	
	final String DEBUG_TAG = "ModelsListFragment";
	ListView mListViewAriaPulita;
	ArrayList<Modello> listaModelli;
	
	int ariaType = -1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle saved){
		View view = inflater.inflate(R.layout.fragment_ariapulita, group, false);
		
		setHasOptionsMenu(true);
		
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
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_menu, menu);
	    super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.search:
//			Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT)
//			.show();
	    	// custom dialog
			final Dialog dialog = new Dialog(getActivity().getBaseContext());
			dialog.setContentView(R.layout.dialog_searchfilters);
			dialog.setTitle("Ricerca filtrata");
 
			// set the custom dialog components - text, image and button
//			TextView text = (TextView) dialog.findViewById(R.id.text);
//			text.setText("Android custom dialog example!");
//			ImageView image = (ImageView) dialog.findViewById(R.id.image);
//			image.setImageResource(R.drawable.ic_launcher);
// 
//			Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//			// if button is clicked, close the custom dialog
//			dialogButton.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					dialog.dismiss();
//				}
//			});
 
			dialog.show();
	    	return true;
	    default:
	    	return super.onOptionsItemSelected(item);
	    }
	}

}
