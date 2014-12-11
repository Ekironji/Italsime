package com.ekironji.italsime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.R;
import com.ekironji.italsime.Modello.Modello;

public class ModelDescriptorFragment extends Fragment{

	final String DEBUG_TAG = "ModelDescriptorFragment";

	Modello modello;
	
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
		View view = inflater.inflate(R.layout.fragment_modeldescriptor, group, false);

		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

		modello = (Modello)getArguments().getSerializable(MainActivity.KEY_PASSMODEL);
		Log.i(DEBUG_TAG, modello.toString());

		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
//			getActivity().getSupportFragmentManager().popBackStack();
			((MainActivity)getActivity()).onBackPressed();
			return true;    
		}

		return super.onOptionsItemSelected(item);
	}

}
