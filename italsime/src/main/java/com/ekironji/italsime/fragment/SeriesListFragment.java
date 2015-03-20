package com.ekironji.italsime.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edmodo.rangebar.RangeBar;
import com.edmodo.rangebar.RangeBar.OnRangeBarChangeListener;
import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.Modello.Modello;
import com.ekironji.italsime.Modello.Series;
import com.ekironji.italsime.R;

import java.util.List;
import java.util.Locale;

public class SeriesListFragment extends Fragment{
	
	final String DEBUG_TAG = "SeriesListFragment";
	
	/**
	 * Remember the position of the selected item.
	 */
	private static final String STATE_SELECTED_ARIATYPE = "selected_ariatype";
	
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
//	    getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
	    
		if (savedInstanceState != null) {
			ariaType = savedInstanceState
					.getInt(STATE_SELECTED_ARIATYPE);
//			mFromSavedInstanceState = true;
		}

	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(DEBUG_TAG, "onSaveInstanceState()");
		
		outState.putInt(STATE_SELECTED_ARIATYPE, ariaType);
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

	    	LayoutInflater inflater = LayoutInflater.from(getActivity());
	    	final View dialog_view = inflater.inflate(R.layout.dialog_searchfilters, null);
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
			final RangeBar rangeBarPortata = (RangeBar) dialog_view.findViewById(R.id.rangebarPortata);
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

            final CheckBox mCheckBox_portata_man = (CheckBox) dialog_view.findViewById(R.id.checkbox_portata_manually);
            mCheckBox_portata_man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        ((RelativeLayout) dialog_view.findViewById(R.id.layout_editstext_portata)).setVisibility(View.VISIBLE);
                        rangeBarPortata.setEnabled(false);
                        rangeBarPortata.setBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        rangeBarPortata.setConnectingLineColor(getResources().getColor(R.color.colorPrimaryDark));
                        rangeBarPortata.setThumbRadius(0);
                        rangeBarPortata.setConnectingLineWeight(2);
                    } else {
                        ((RelativeLayout) dialog_view.findViewById(R.id.layout_editstext_portata)).setVisibility(View.GONE);
                        rangeBarPortata.setEnabled(true);
                        rangeBarPortata.setThumbRadius(-1);
                        rangeBarPortata.setThumbColorNormal(-1);
                        rangeBarPortata.setThumbColorPressed(-1);
                        rangeBarPortata.setConnectingLineWeight(4);
                        if (ariaType == Modello.ARIA_PULITA) {
                            rangeBarPortata.setBarColor(getResources().getColor(R.color.italsimegreenahcg_color));
                            rangeBarPortata.setConnectingLineColor(getResources().getColor(R.color.italsimegreenahcg_color));
                        } else if (ariaType == Modello.ARIA_SPORCA) {
                            rangeBarPortata.setBarColor(getResources().getColor(R.color.italsimevioletahcg_color));
                            rangeBarPortata.setConnectingLineColor(getResources().getColor(R.color.italsimevioletahcg_color));
                        }
                    }
                }
            });

            final EditText editMinPort = (EditText) dialog_view.findViewById(R.id.editTextMinPortata);
            editMinPort.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        handled = true;
                        String editValue = editMinPort.getText().toString();
                        if (!editValue.equals("") && !editValue.contains(".") && !(Integer.valueOf(editValue) < DEFAULT_MIN_PORTATA)) {
                            minPortata = Integer.valueOf(editValue);
                            textMinPortata.setText(String.valueOf(minPortata));
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        } else {
                            Toast.makeText(getActivity(), getResources().getText(R.string.invalide_value), Toast.LENGTH_SHORT).show();
                        }
                    }
                    return handled;
                }
            });
            editMinPort.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String editValue = editMinPort.getText().toString();
                        if (!editValue.equals("") && !editValue.contains(".") && !(Integer.valueOf(editValue) < DEFAULT_MIN_PORTATA)) {
                            minPortata = Integer.valueOf(editValue);
                            textMinPortata.setText(String.valueOf(minPortata));
                        } else {
                            Toast.makeText(getActivity(), getResources().getText(R.string.invalide_value), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            final EditText editMaxPort = (EditText) dialog_view.findViewById(R.id.editTextMaxPortata);
            editMaxPort.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        handled = true;
                        String editValue = editMaxPort.getText().toString();
                        if (!editValue.equals("") && !editValue.contains(".") && !(Integer.valueOf(editValue) > DEFAULT_MAX_PORTATA)) {
                            maxPortata = Integer.valueOf(editValue);
                            textMaxPortata.setText(String.valueOf(maxPortata));
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        } else {
                            Toast.makeText(getActivity(), getResources().getText(R.string.invalide_value), Toast.LENGTH_SHORT).show();
                        }
                    }
                    return handled;
                }
            });
            editMaxPort.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String editValue = editMaxPort.getText().toString();
                        if (!editValue.equals("") && !editValue.contains(".") && !(Integer.valueOf(editValue) > DEFAULT_MAX_PORTATA)) {
                            maxPortata = Integer.valueOf(editValue);
                            textMaxPortata.setText(String.valueOf(maxPortata));
                        } else {
                            Toast.makeText(getActivity(), getResources().getText(R.string.invalide_value), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

			final TextView textMinPressione = (TextView) dialog_view.findViewById(R.id.textView_minPressione);
			textMinPressione.setText(String.valueOf(DEFAULT_MIN_PRESSIONE));
			final TextView textMaxPressione = (TextView) dialog_view.findViewById(R.id.textView_maxPressione);
			textMaxPressione.setText(String.valueOf(DEFAULT_MAX_PRESSIONE));
			final RangeBar rangeBarPressione = (RangeBar) dialog_view.findViewById(R.id.rangebarPressione);
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

            final CheckBox mCheckBox_pressione_man = (CheckBox) dialog_view.findViewById(R.id.checkbox_pressione_manually);
            mCheckBox_pressione_man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        ((RelativeLayout) dialog_view.findViewById(R.id.layout_editstext_pressione)).setVisibility(View.VISIBLE);
                        rangeBarPressione.setEnabled(false);
                        rangeBarPressione.setBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        rangeBarPressione.setConnectingLineColor(getResources().getColor(R.color.colorPrimaryDark));
                        rangeBarPressione.setThumbRadius(0);
                        rangeBarPressione.setConnectingLineWeight(2);
                    } else {
                        ((RelativeLayout) dialog_view.findViewById(R.id.layout_editstext_pressione)).setVisibility(View.GONE);
                        rangeBarPressione.setEnabled(true);
                        rangeBarPressione.setThumbRadius(-1);
                        rangeBarPressione.setThumbColorNormal(-1);
                        rangeBarPressione.setThumbColorPressed(-1);
                        rangeBarPressione.setConnectingLineWeight(4);
                        if (ariaType == Modello.ARIA_PULITA) {
                            rangeBarPressione.setBarColor(getResources().getColor(R.color.italsimegreenahcg_color));
                            rangeBarPressione.setConnectingLineColor(getResources().getColor(R.color.italsimegreenahcg_color));
                        } else if (ariaType == Modello.ARIA_SPORCA) {
                            rangeBarPressione.setBarColor(getResources().getColor(R.color.italsimevioletahcg_color));
                            rangeBarPressione.setConnectingLineColor(getResources().getColor(R.color.italsimevioletahcg_color));
                        }
                    }
                }
            });

            final EditText editMinPress = (EditText) dialog_view.findViewById(R.id.editTextMinPressione);
            editMinPress.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        handled = true;
                        String editValue = editMinPress.getText().toString();
                        if (!editValue.equals("") && !editValue.contains(".") && !(Integer.valueOf(editValue) < DEFAULT_MIN_PRESSIONE)) {
                            minPressione = Integer.valueOf(editValue);
                            textMinPressione.setText(String.valueOf(minPressione));
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        } else {
                            Toast.makeText(getActivity(), getResources().getText(R.string.invalide_value), Toast.LENGTH_SHORT).show();
                        }
                    }
                    return handled;
                }
            });
            editMinPress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String editValue = editMinPress.getText().toString();
                        if (!editValue.equals("") && !editValue.contains(".") && !(Integer.valueOf(editValue) < DEFAULT_MIN_PRESSIONE)) {
                            minPressione = Integer.valueOf(editValue);
                            textMinPressione.setText(String.valueOf(minPressione));
                        } else {
                            Toast.makeText(getActivity(), getResources().getText(R.string.invalide_value), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            final EditText editMaxPress = (EditText) dialog_view.findViewById(R.id.editTextMaxPressione);
            editMaxPress.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        handled = true;
                        String editValue = editMaxPress.getText().toString();
                        if (!editValue.equals("") && !editValue.contains(".") && !(Integer.valueOf(editValue) > DEFAULT_MAX_PRESSIONE)) {
                            maxPressione = Integer.valueOf(editValue);
                            textMaxPressione.setText(String.valueOf(maxPressione));
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        } else {
                            Toast.makeText(getActivity(), getResources().getText(R.string.invalide_value), Toast.LENGTH_SHORT).show();
                        }
                    }
                    return handled;
                }
            });
            editMaxPress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String editValue = editMaxPress.getText().toString();
                        if (!editValue.equals("") && !editValue.contains(".") && !(Integer.valueOf(editValue) > DEFAULT_MAX_PRESSIONE)) {
                            maxPressione = Integer.valueOf(editValue);
                            textMaxPressione.setText(String.valueOf(maxPressione));
                        } else {
                            Toast.makeText(getActivity(), getResources().getText(R.string.invalide_value), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            db.setPositiveButton(getResources().getString(R.string.positive_button_search_filters_dialog), new
	    	    DialogInterface.OnClickListener() {
	    	        public void onClick(DialogInterface dialog, int which) {
                        if (minPortata > maxPortata || minPortata < DEFAULT_MIN_PORTATA || maxPortata > DEFAULT_MAX_PORTATA) {
                            Toast.makeText(getActivity(), new String(getResources().getText(R.string.portata_string) + "" +
                                    getResources().getText(R.string.invalide_range)), Toast.LENGTH_LONG).show();
                        } else {
                            if (minPressione > maxPressione || minPressione < DEFAULT_MIN_PRESSIONE || maxPressione > DEFAULT_MAX_PRESSIONE) {
                                Toast.makeText(getActivity(), new String(getResources().getText(R.string.pressure_string) + "" +
                                        getResources().getText(R.string.invalide_range)), Toast.LENGTH_LONG).show();
                            } else {
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                Fragment mFragment = new ModelsListFragment();
                                Bundle mBundle = new Bundle();
                                int[] filteredResearchArgs = new int[]{ariaType,
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
                                dialog.dismiss();
                            }
                        }
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
                textMinPortata.setTextColor(getResources().getColor(R.color.italsimegreenahcg_color));
                textMaxPortata.setTextColor(getResources().getColor(R.color.italsimegreenahcg_color));
                textMinPressione.setTextColor(getResources().getColor(R.color.italsimegreenahcg_color));
                textMaxPressione.setTextColor(getResources().getColor(R.color.italsimegreenahcg_color));

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
                textMinPortata.setTextColor(getResources().getColor(R.color.italsimevioletahcg_color));
                textMaxPortata.setTextColor(getResources().getColor(R.color.italsimevioletahcg_color));
                textMinPressione.setTextColor(getResources().getColor(R.color.italsimevioletahcg_color));
                textMaxPressione.setTextColor(getResources().getColor(R.color.italsimevioletahcg_color));

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
				nameSerie.setTextColor(getResources().getColor(R.color.italsimegreenahcg_color));
				vg.setBackgroundResource(R.drawable.italsimegreenahcg_list_selector_holo_light);
			} else if (ariaType == Modello.ARIA_SPORCA) {
				nameSerie.setTextColor(getResources().getColor(R.color.italsimevioletahcg_color));
				vg.setBackgroundResource(R.drawable.italsimevioletahcg_list_selector_holo_light);
			}
			
			((ImageView) vg.findViewById(R.id.imageView_serie)).setImageResource(
					getResources().getIdentifier(mSeries.get(position).toLowerCase(Locale.getDefault()), "drawable",  getActivity().getPackageName()));
			
			return vg;
		}
	}
	
}
