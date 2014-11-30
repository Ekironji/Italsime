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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.edmodo.rangebar.RangeBar.OnRangeBarChangeListener;
import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.R;
import com.ekironji.italsime.Modello.ModelliListAdapter;
import com.ekironji.italsime.Modello.Modello;

public class ModelsListFragment extends Fragment{
	
	final String DEBUG_TAG = "ModelsListFragment";
	ListView mListViewAriaPulita;
	ArrayList<Modello> listaModelli;
	
	int ariaType = -1;
	
	final int DEFAULT_MIN_PORTATA = 0;
	final int DEFAULT_MAX_PORTATA = 900;
	int minPortata = DEFAULT_MIN_PORTATA;
	int maxPortata = DEFAULT_MAX_PORTATA;
	
	final int DEFAULT_MIN_PRESSIONE = 0;
	final int DEFAULT_MAX_PRESSIONE = 900;
	int minPressione = DEFAULT_MIN_PRESSIONE;
	int maxPressione = DEFAULT_MAX_PRESSIONE;
	
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
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of
		// actions in the action bar.
		setHasOptionsMenu(true);
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
			final Dialog dialog = new Dialog(getActivity());
			dialog.setContentView(R.layout.dialog_searchfilters);
			dialog.setTitle("Filtri ricerca");
 
			// set the custom dialog components - text, image and button
			final TextView textMinPortata = (TextView) dialog.findViewById(R.id.textView_minPortata);
			textMinPortata.setText(String.valueOf(DEFAULT_MIN_PORTATA));
			final TextView textMaxPortata = (TextView) dialog.findViewById(R.id.textView_maxPortata);
			textMaxPortata.setText(String.valueOf(DEFAULT_MAX_PORTATA));
			RangeBar rangeBarPortata = (RangeBar) dialog.findViewById(R.id.rangebarPortata);
			rangeBarPortata.setTickCount(900);
			rangeBarPortata.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {
				
				@Override
				public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex,
						int rightThumbIndex) {
					textMinPortata.setText(String.valueOf(leftThumbIndex));
					textMaxPortata.setText(String.valueOf(rightThumbIndex));
					minPortata = leftThumbIndex;
					maxPortata = rightThumbIndex;
				}
			});
			
			final TextView textMinPressione = (TextView) dialog.findViewById(R.id.textView_minPressione);
			textMinPressione.setText(String.valueOf(DEFAULT_MIN_PRESSIONE));
			final TextView textMaxPressione = (TextView) dialog.findViewById(R.id.textView_maxPressione);
			textMaxPressione.setText(String.valueOf(DEFAULT_MAX_PRESSIONE));
			RangeBar rangeBarPressione = (RangeBar) dialog.findViewById(R.id.rangebarPressione);
			rangeBarPressione.setTickCount(900);
			rangeBarPressione.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {
				
				@Override
				public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex,
						int rightThumbIndex) {
					textMinPressione.setText(String.valueOf(leftThumbIndex));
					textMaxPressione.setText(String.valueOf(rightThumbIndex));
					minPressione = leftThumbIndex;
					maxPressione = rightThumbIndex;
				}
			});
			
			 
			Button dialogButton = (Button) dialog.findViewById(R.id.buttonAvviaRicerca);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					dialog.dismiss();
				}
			});
 
			dialog.show();
	    	return true;
	    default:
	    	return super.onOptionsItemSelected(item);
	    }
	}

}
