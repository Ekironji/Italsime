package com.ekironji.italsime.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.edmodo.rangebar.RangeBar.OnRangeBarChangeListener;
import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.Modello.ModelliListAdapter;
import com.ekironji.italsime.Modello.Modello;
import com.ekironji.italsime.Modello.Series;
import com.ekironji.italsime.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ModelsListFragment extends Fragment {
	
	final String DEBUG_TAG = "ModelsListFragment";
	
	ListView mListViewModels;
	ArrayList<Modello> listaModelli;
	ModelliListAdapter mListAdapter;
	
	int ariaType = -1;
	
	RelativeLayout searchFiltersLayout;
	TextView serieFilterValue;
	TextView portataFilterValues;
	TextView pressureFilterValues;
	
	final int DEFAULT_MIN_PORTATA = 0;
	final int DEFAULT_MAX_PORTATA = 60000;
	int minPortata = DEFAULT_MIN_PORTATA;
	int maxPortata = DEFAULT_MAX_PORTATA;
	
	final int DEFAULT_MIN_PRESSIONE = 0;
	final int DEFAULT_MAX_PRESSIONE = 2000;
	int minPressione = DEFAULT_MIN_PRESSIONE;
	int maxPressione = DEFAULT_MAX_PRESSIONE;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // needed to indicate that the fragment would 
	    // like to add items to the Options Menu        
	    setHasOptionsMenu(true);    
	    // update the actionbar to show the up carat/affordance 
	    //getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle saved){
		View view = inflater.inflate(R.layout.fragment_modelslist, group, false);
		
		searchFiltersLayout  = (RelativeLayout) view.findViewById(R.id.layoutSearchFilters);
		searchFiltersLayout.setVisibility(View.GONE);
		serieFilterValue	 = (TextView) view.findViewById(R.id.serieFiltersValue);
		portataFilterValues  = (TextView) view.findViewById(R.id.portataFiltersValues);
		pressureFilterValues = (TextView) view.findViewById(R.id.pressureFiltersValues);
		
		mListViewModels 	 = (ListView) view.findViewById(R.id.listViewModels);
		
		mListViewModels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Log.i(DEBUG_TAG, listaModelli.get(position).toString());
				
				((MainActivity)getActivity()).getDrawerToggle().setDrawerIndicatorEnabled(false);
				
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				Fragment mFragment = new ModelDescriptorFragment();
		        Bundle mBundle = new Bundle();
		    	mBundle.putSerializable(MainActivity.KEY_PASSMODEL, listaModelli.get(position));
		    	mFragment.setArguments(mBundle);
		    	fragmentManager.beginTransaction()
		        .replace(R.id.container, mFragment)
		        .addToBackStack("modListFragBack")
		        .commit();		    	
			}
		});
		
		int serie = getArguments().getInt(MainActivity.KEY_PASSSERIE);
		Log.i(DEBUG_TAG, "MainActivity.KEY_PASSSERIE: " + serie);
		if(serie != 0){
			ariaType = getArguments().getInt(MainActivity.KEY_PASSARIATYPE);
			MainActivity.database.open();
			listaModelli = MainActivity.database.getAllModelsBySerie(serie);
			MainActivity.database.close();
		} else {
			int[] filteredResearchArgs = getArguments().getIntArray(MainActivity.KEY_PASSFILTEREDRESEARCH);
			if (filteredResearchArgs != null) {
				ariaType = filteredResearchArgs[0];
				Log.i(DEBUG_TAG, "MainActivity.KEY_PASSFILTEREDRESEARCH: " + " - ariatype: " + filteredResearchArgs[0]
						+ " - serie: " + filteredResearchArgs[1] + " - minPo: " + filteredResearchArgs[2]
						+ " - maxPo: " + filteredResearchArgs[3] + " - minPr: " + filteredResearchArgs[4]
						+ " - maxPr: " + filteredResearchArgs[5]);
				listaModelli = new ArrayList<Modello>();
				new UpdateListFromFiltersTask().execute(filteredResearchArgs[0],filteredResearchArgs[1],filteredResearchArgs[2],
														filteredResearchArgs[3],filteredResearchArgs[4],filteredResearchArgs[5]);
			} else {
				Log.i(DEBUG_TAG, "MainActivity.KEY_PASSFILTEREDRESEARCH: null");
			}
		}	
		
		mListAdapter = new ModelliListAdapter(getActivity(), listaModelli);
		mListViewModels.setAdapter(mListAdapter);
		
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
	    	
	    	minPortata = DEFAULT_MIN_PORTATA;
	    	maxPortata = DEFAULT_MAX_PORTATA;
	    	minPressione = DEFAULT_MIN_PRESSIONE;
	    	maxPressione = DEFAULT_MAX_PRESSIONE;

	    	LayoutInflater inflater = LayoutInflater.from(getActivity());
	    	View dialog_view = inflater.inflate(R.layout.dialog_searchfilters, null);
	    	AlertDialog.Builder db = new AlertDialog.Builder(getActivity());
	    	db.setView(dialog_view);
	    	
	    	final Spinner spinner = (Spinner) dialog_view.findViewById(R.id.series_spinner);
	    	int arrayID = (ariaType == Modello.ARIA_PULITA) ? R.array.array_closed_blade_series : R.array.array_opened_blade_series;
	    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
	    			arrayID , android.R.layout.simple_spinner_item);
	    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	spinner.setAdapter(adapter);
	    	
			// set the custom dialog components - text, image and button
			final TextView textMinPortata = (TextView) dialog_view.findViewById(R.id.textView_minPortata);
			textMinPortata.setText(String.valueOf(DEFAULT_MIN_PORTATA));
			final TextView textMaxPortata = (TextView) dialog_view.findViewById(R.id.textView_maxPortata);
			textMaxPortata.setText(String.valueOf(DEFAULT_MAX_PORTATA));
			RangeBar rangeBarPortata = (RangeBar) dialog_view.findViewById(R.id.rangebarPortata);
			rangeBarPortata.setTickCount(120);
			rangeBarPortata.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {
				
				@Override
				public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex,
						int rightThumbIndex) {
					minPortata = leftThumbIndex*500;
					maxPortata = rightThumbIndex*500;
					textMinPortata.setText(String.valueOf(minPortata));
					textMaxPortata.setText(String.valueOf(maxPortata));
				}
			});
			
			final TextView textMinPressione = (TextView) dialog_view.findViewById(R.id.textView_minPressione);
			textMinPressione.setText(String.valueOf(DEFAULT_MIN_PRESSIONE));
			final TextView textMaxPressione = (TextView) dialog_view.findViewById(R.id.textView_maxPressione);
			textMaxPressione.setText(String.valueOf(DEFAULT_MAX_PRESSIONE));
			RangeBar rangeBarPressione = (RangeBar) dialog_view.findViewById(R.id.rangebarPressione);
			rangeBarPressione.setTickCount(200);
			rangeBarPressione.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {
				
				@Override
				public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex,
						int rightThumbIndex) {
					minPressione = leftThumbIndex*10;
					maxPressione = rightThumbIndex*10;
					textMinPressione.setText(String.valueOf(minPressione));
					textMaxPressione.setText(String.valueOf(maxPressione));
					
				}
			});
	    	
	    	db.setPositiveButton(getResources().getString(R.string.positive_button_search_filters_dialog), new 
	    	    DialogInterface.OnClickListener() {
	    	        public void onClick(DialogInterface dialog, int which) {
						new UpdateListFromFiltersTask().execute(ariaType,
																Series.getIntFromName(spinner.getSelectedItem().toString()),
																minPortata, 
																maxPortata, 
																minPressione, 
																maxPressione);
						dialog.dismiss();
	    	        }
	    		}
	    	);
	    	db.setNegativeButton(getResources().getString(R.string.negative_button_search_filters_dialog), new 
	    	    DialogInterface.OnClickListener() {
	    	        public void onClick(DialogInterface dialog, int which) {
	    	        	dialog.dismiss();
	    	        }
	    		}
	    	);

            TextView mTitle = (TextView) dialog_view.findViewById(R.id.alertTitle);
            View mDivider = dialog_view.findViewById(R.id.titleDivider);

            if (ariaType == Modello.ARIA_PULITA) {

                mTitle.setText(getResources().getString(R.string.title_search_filters_dialog) + " for Closed Blade");
                mTitle.setTextColor(getResources().getColor(R.color.italsimegreenahcg_color));
                mDivider.setBackgroundColor(getResources().getColor(R.color.italsimegreenahcg_color));

                rangeBarPortata.setBarColor(getResources().getColor(R.color.italsimegreenahcg_color));
                rangeBarPortata.setConnectingLineColor(getResources().getColor(R.color.italsimegreenahcg_color));
                rangeBarPortata.setThumbImageNormal(R.drawable.italsimegreenahcg_scrubber_control_normal_holo);
                rangeBarPortata.setThumbImagePressed(R.drawable.italsimegreenahcg_scrubber_control_pressed_holo);

                rangeBarPressione.setBarColor(getResources().getColor(R.color.italsimegreenahcg_color));
                rangeBarPressione.setConnectingLineColor(getResources().getColor(R.color.italsimegreenahcg_color));
                rangeBarPressione.setThumbImageNormal(R.drawable.italsimegreenahcg_scrubber_control_normal_holo);
                rangeBarPressione.setThumbImagePressed(R.drawable.italsimegreenahcg_scrubber_control_pressed_holo);
            } else if (ariaType == Modello.ARIA_SPORCA) {

                mTitle.setText(getResources().getString(R.string.title_search_filters_dialog) + " for Opened Blade");
                mTitle.setTextColor(getResources().getColor(R.color.italsimevioletahcg_color));
                mDivider.setBackgroundColor(getResources().getColor(R.color.italsimevioletahcg_color));

                rangeBarPortata.setBarColor(getResources().getColor(R.color.italsimevioletahcg_color));
                rangeBarPortata.setConnectingLineColor(getResources().getColor(R.color.italsimevioletahcg_color));
                rangeBarPortata.setThumbImageNormal(R.drawable.italsimevioletahcg_scrubber_control_normal_holo);
                rangeBarPortata.setThumbImagePressed(R.drawable.italsimevioletahcg_scrubber_control_pressed_holo);

                rangeBarPressione.setBarColor(getResources().getColor(R.color.italsimevioletahcg_color));
                rangeBarPressione.setConnectingLineColor(getResources().getColor(R.color.italsimevioletahcg_color));
                rangeBarPressione.setThumbImageNormal(R.drawable.italsimevioletahcg_scrubber_control_normal_holo);
                rangeBarPressione.setThumbImagePressed(R.drawable.italsimevioletahcg_scrubber_control_pressed_holo);
            }
	    	
	    	AlertDialog dialog = db.show();
	    	return true;
	    default:
	    	return super.onOptionsItemSelected(item);
	    }
	}
	
	public void showSearchFiltersView(int ariaType, int serie, int minPo, int maxPo, int minPr, int maxPr) {
		if (ariaType == Modello.ARIA_PULITA) {
			serieFilterValue.setTextColor(getResources().getColor(R.color.italsimegreenahcg_color));
			portataFilterValues.setTextColor(getResources().getColor(R.color.italsimegreenahcg_color));
			pressureFilterValues.setTextColor(getResources().getColor(R.color.italsimegreenahcg_color));
		} else if (ariaType == Modello.ARIA_SPORCA) {
			serieFilterValue.setTextColor(getResources().getColor(R.color.italsimevioletahcg_color));
			portataFilterValues.setTextColor(getResources().getColor(R.color.italsimevioletahcg_color));
			pressureFilterValues.setTextColor(getResources().getColor(R.color.italsimevioletahcg_color));
		}
		serieFilterValue.setText(Series.getNameFromInt(serie));
		portataFilterValues.setText("m3/h: " + minPo + " - " + maxPo);
		pressureFilterValues.setText("mmH20: " + minPr + " - " + maxPr);
		searchFiltersLayout.setVisibility(View.VISIBLE);
	}
	
	private class UpdateListFromFiltersTask extends AsyncTask<Integer, Integer, Void> {

		protected void onPreExecute(){
			MainActivity.showProgressDialog(getResources().getString(R.string.title_search_progress_dialog));
			listaModelli.clear();
			MainActivity.database.open();
		}

		protected Void doInBackground(Integer... args) {
			Log.i(DEBUG_TAG, "ariatype: " + args[0] + "serie: " + args[1] + " - minPortata: " + args[2] + 
					" - maxPortata: " + args[3] + " - minPressione: " + args[4] + 
					" - maxPressione: " + args[5]);
			publishProgress(args);
            if (args[1] == 0) {
                listaModelli = MainActivity.database.getModelsByFilteredSearch(args[0],
                        args[2], args[3], args[4], args[5]);
            } else {
                listaModelli = MainActivity.database.getModelsBySeriesFilteredSearch(args[0], args[1],
                        args[2], args[3], args[4], args[5]);
            }
            Log.i(DEBUG_TAG, "listamodelli size: " + listaModelli.size());
            MainActivity.database.close();
			return null;	    	 
		}

		protected void onProgressUpdate(Integer... args) {
			showSearchFiltersView(args[0], args[1], args[2], args[3], args[4], args[5]);
	    }
		
		protected void onPostExecute(Void result) {
//			mListAdapter.notifyDataSetChanged();
            mListAdapter=null;
            mListAdapter = new ModelliListAdapter(getActivity(), listaModelli);
            mListViewModels.setAdapter(mListAdapter);
			MainActivity.hideProgressDialog();

		}
	}

}
