package com.ekironji.italsime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
		
		((TextView) view.findViewById(R.id.textViewNome)).setText(modello.getName());
		((TextView) view.findViewById(R.id.TextViewKwValue)).setText(String.valueOf(modello.getKw()));
		((TextView) view.findViewById(R.id.TextViewRpmValue)).setText(String.valueOf(modello.getRpm()));
		((TextView) view.findViewById(R.id.TextViewKgValue)).setText(String.valueOf(modello.getKg()));
		
		ImageView ariaTypeImage = (ImageView) view.findViewById(R.id.imageViewAriaType);
		if (modello.getAriaType() == Modello.ARIA_PULITA) {
			ariaTypeImage.setImageResource(R.drawable.ariapulita);
		} else if (modello.getAriaType() == Modello.ARIA_SPORCA) {
			ariaTypeImage.setImageResource(R.drawable.ariasporca);
		}
		
		((TextView) view.findViewById(R.id.TextViewm3h1)).setText((modello.getM3h1() == -1) ? "-" : String.valueOf(modello.getM3h1()));
		((TextView) view.findViewById(R.id.TextViewm3h2)).setText((modello.getM3h2() == -1) ? "-" : String.valueOf(modello.getM3h2()));
		((TextView) view.findViewById(R.id.TextViewm3h3)).setText((modello.getM3h3() == -1) ? "-" : String.valueOf(modello.getM3h3()));
		((TextView) view.findViewById(R.id.TextViewm3h4)).setText((modello.getM3h4() == -1) ? "-" : String.valueOf(modello.getM3h4()));
		((TextView) view.findViewById(R.id.TextViewm3h5)).setText((modello.getM3h5() == -1) ? "-" : String.valueOf(modello.getM3h5()));
		
		((TextView) view.findViewById(R.id.TextViewmmH2O1)).setText((modello.getMmH2O1() == -1) ? "-" : String.valueOf(modello.getMmH2O1()));
		((TextView) view.findViewById(R.id.TextViewmmH2O2)).setText((modello.getMmH2O2() == -1) ? "-" : String.valueOf(modello.getMmH2O2()));
		((TextView) view.findViewById(R.id.TextViewmmH2O3)).setText((modello.getMmH2O3() == -1) ? "-" : String.valueOf(modello.getMmH2O3()));
		((TextView) view.findViewById(R.id.TextViewmmH2O4)).setText((modello.getMmH2O4() == -1) ? "-" : String.valueOf(modello.getMmH2O4()));
		((TextView) view.findViewById(R.id.TextViewmmH2O5)).setText((modello.getMmH2O5() == -1) ? "-" : String.valueOf(modello.getMmH2O5()));

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
