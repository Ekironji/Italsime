package com.ekironji.italsime.fragment;

import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.edmodo.rangebar.RangeBar.OnRangeBarChangeListener;
import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.R;
import com.ekironji.italsime.Modello.Modello;
import com.ekironji.italsime.Modello.Series;

@SuppressLint("DefaultLocale")
public class SeriesListFragment extends Fragment{
	
	final String DEBUG_TAG = "ModelsListFragment";
	
	int ariaType = -1;
	
	List<String> listaSeries;
	
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
	    getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle saved){
		View view = inflater.inflate(R.layout.fragment_serieslist, group, false);
		
		ListView mSeriesListView = (ListView) view.findViewById(R.id.listViewSeries);
		mSeriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Log.i(DEBUG_TAG, listaModelli.get(position).toString());
				
//				((MainActivity)getActivity()).getDrawerToggle().setDrawerIndicatorEnabled(false);
				
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				Fragment mFragment = new ModelsListFragment();
		        Bundle mBundle = new Bundle();
		    	mBundle.putInt(MainActivity.KEY_PASSSERIE, Series.getIntFromName(listaSeries.get(position)));
		    	mBundle.putInt(MainActivity.KEY_PASSARIATYPE, ariaType);
		    	mFragment.setArguments(mBundle);
		    	fragmentManager.beginTransaction()
		        .replace(R.id.container, mFragment)
		        .addToBackStack("serListFragBack")
		        .commit();		    	
			}
		});
		
		ariaType = getArguments().getInt(MainActivity.KEY_PASSARIATYPE);
		listaSeries = Series.getSeriesListByAriaType(ariaType);
		
		mSeriesListView.setAdapter(new SeriesListAdapter(getActivity(), listaSeries));
		
		((MainActivity)getActivity()).onSectionAttached(ariaType);
		((MainActivity)getActivity()).restoreActionBar();
		
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
	    	
	    	// custom dialog
////	    	final Dialog dialog = new Dialog(getActivity());
//			final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialogItalsimeGreenAHCG);
//			dialog.setContentView(R.layout.dialog_searchfilters);
//			dialog.setTitle("Filtri ricerca");
// 
//			// set the custom dialog components - text, image and button
//			final TextView textMinPortata = (TextView) dialog.findViewById(R.id.textView_minPortata);
//			textMinPortata.setText(String.valueOf(DEFAULT_MIN_PORTATA));
//			final TextView textMaxPortata = (TextView) dialog.findViewById(R.id.textView_maxPortata);
//			textMaxPortata.setText(String.valueOf(DEFAULT_MAX_PORTATA));
//			RangeBar rangeBarPortata = (RangeBar) dialog.findViewById(R.id.rangebarPortata);
//			rangeBarPortata.setTickCount(900);
//			rangeBarPortata.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {
//				
//				@Override
//				public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex,
//						int rightThumbIndex) {
//					textMinPortata.setText(String.valueOf(leftThumbIndex));
//					textMaxPortata.setText(String.valueOf(rightThumbIndex));
//					minPortata = leftThumbIndex;
//					maxPortata = rightThumbIndex;
//				}
//			});
//			
//			final TextView textMinPressione = (TextView) dialog.findViewById(R.id.textView_minPressione);
//			textMinPressione.setText(String.valueOf(DEFAULT_MIN_PRESSIONE));
//			final TextView textMaxPressione = (TextView) dialog.findViewById(R.id.textView_maxPressione);
//			textMaxPressione.setText(String.valueOf(DEFAULT_MAX_PRESSIONE));
//			RangeBar rangeBarPressione = (RangeBar) dialog.findViewById(R.id.rangebarPressione);
//			rangeBarPressione.setTickCount(900);
//			rangeBarPressione.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {
//				
//				@Override
//				public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex,
//						int rightThumbIndex) {
//					textMinPressione.setText(String.valueOf(leftThumbIndex));
//					textMaxPressione.setText(String.valueOf(rightThumbIndex));
//					minPressione = leftThumbIndex;
//					maxPressione = rightThumbIndex;
//				}
//			});
//			
//			 
//			Button dialogButton = (Button) dialog.findViewById(R.id.buttonAvviaRicerca);
//			// if button is clicked, close the custom dialog
//			dialogButton.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					new UpdateListFromFiltersTask().execute(minPortata, maxPortata, minPressione, maxPressione);
//					dialog.dismiss();
//				}
//			});
//			
//			AlertDialog.Builder db = dialog
// 
//			dialog.show();
	    	
	    	LayoutInflater inflater = LayoutInflater.from(getActivity());
	    	View dialog_view = inflater.inflate(R.layout.dialog_searchfilters, null);
	    	AlertDialog.Builder db = new AlertDialog.Builder(getActivity());
	    	db.setView(dialog_view);
	    	db.setTitle(getResources().getString(R.string.title_search_filters_dialog));
	    	
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
			if (ariaType == Modello.ARIA_PULITA) {
				rangeBarPortata.setBarColor(getResources().getColor(R.color.italsimegreenahcg_color));
				rangeBarPortata.setConnectingLineColor(getResources().getColor(R.color.italsimegreenahcg_color));
				rangeBarPortata.setThumbImageNormal(R.drawable.italsimegreenahcg_scrubber_control_normal_holo);
				rangeBarPortata.setThumbImagePressed(R.drawable.italsimegreenahcg_scrubber_control_pressed_holo);
			} else if (ariaType == Modello.ARIA_SPORCA) {
				rangeBarPortata.setBarColor(getResources().getColor(R.color.italsimevioletahcg_color));
				rangeBarPortata.setConnectingLineColor(getResources().getColor(R.color.italsimevioletahcg_color));
				rangeBarPortata.setThumbColorNormal(R.color.italsimevioletahcg_color);
				rangeBarPortata.setThumbColorPressed(R.color.italsimevioletahcg_color);
			}
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
			if (ariaType == Modello.ARIA_PULITA) {
				rangeBarPressione.setBarColor(getResources().getColor(R.color.italsimegreenahcg_color));
				rangeBarPressione.setConnectingLineColor(getResources().getColor(R.color.italsimegreenahcg_color));
				rangeBarPressione.setThumbImageNormal(R.drawable.italsimegreenahcg_scrubber_control_normal_holo);
				rangeBarPressione.setThumbImagePressed(R.drawable.italsimegreenahcg_scrubber_control_pressed_holo);
			} else if (ariaType == Modello.ARIA_SPORCA) {
				rangeBarPressione.setBarColor(getResources().getColor(R.color.italsimevioletahcg_color));
				rangeBarPressione.setConnectingLineColor(getResources().getColor(R.color.italsimevioletahcg_color));
				rangeBarPressione.setThumbColorNormal(R.color.italsimevioletahcg_color);
				rangeBarPressione.setThumbColorPressed(R.color.italsimevioletahcg_color);
			}
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
	    	        	FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
	    				Fragment mFragment = new ModelsListFragment();
	    		        Bundle mBundle = new Bundle();
	    		        int[] filteredResearchArgs = new int[] {ariaType,
	    		        										Series.getIntFromName(spinner.getSelectedItem().toString()),
	    		        										minPortata,
	    		        										maxPortata,
	    		        										minPressione,
	    		        										maxPressione};
	    		    	mBundle.putIntArray(MainActivity.KEY_PASSFILTEREDRESEARCH, filteredResearchArgs);
	    		    	mFragment.setArguments(mBundle);
	    		    	fragmentManager.beginTransaction()
	    		        .replace(R.id.container, mFragment)
	    		        .addToBackStack("serListFragBack")
	    		        .commit();	
	    	        	spinner.getSelectedItem().toString();
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
	    	
	    	AlertDialog dialog = db.show();
	    	return true;
	    default:
	    	return super.onOptionsItemSelected(item);
	    }
	}
	
	public class SeriesListAdapter extends BaseAdapter {
		private List<String> mSeries;
		private LayoutInflater mInflater;

		public SeriesListAdapter(Context context, List<String> listaSerie) {
			mInflater = LayoutInflater.from(context);
			mSeries = listaSerie;
		}

		public int getCount() {
			return mSeries.size();
		}

		public String getItem(int position) {
			return mSeries.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewGroup vg;

			if (convertView != null) {
				vg = (ViewGroup) convertView;
			} else {
				vg = (ViewGroup) mInflater.inflate(R.layout.listview_serie_item, null);

			}
			
			TextView nameSerie = (TextView) vg.findViewById(R.id.textView_serie_name);
			nameSerie.setText("Serie " + mSeries.get(position));
			if (ariaType == Modello.ARIA_PULITA) {
				nameSerie.setBackgroundColor(getResources().getColor(R.color.italsimegreenahcg_color));
			} else if (ariaType == Modello.ARIA_SPORCA) {
				nameSerie.setBackgroundColor(getResources().getColor(R.color.italsimevioletahcg_color));
			}
			
			((ImageView) vg.findViewById(R.id.imageView_serie)).setImageResource(
					getResources().getIdentifier(mSeries.get(position).toLowerCase(Locale.getDefault()), "drawable",  getActivity().getPackageName()));
			
			return vg;
		}
	}
	
}
