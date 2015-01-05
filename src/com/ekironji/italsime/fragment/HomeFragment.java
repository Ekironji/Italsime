package com.ekironji.italsime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.R;
import com.ekironji.italsime.Modello.Modello;

public class HomeFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle saved){
		View view = inflater.inflate(R.layout.fragment_home, group, false);
	
		((RelativeLayout) view.findViewById(R.id.layout_AriaPulita)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				Fragment mFragment = new SeriesListFragment();
		        Bundle mBundle = new Bundle();
		    	mBundle.putInt(MainActivity.KEY_PASSARIATYPE, Modello.ARIA_PULITA);
		    	mFragment.setArguments(mBundle);
		    	fragmentManager.beginTransaction()
		        .replace(R.id.container, mFragment)
		        .commit();
			}
		});
		
		((RelativeLayout) view.findViewById(R.id.layout_AriaSporca)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				Fragment mFragment = new SeriesListFragment();
		        Bundle mBundle = new Bundle();
		    	mBundle.putInt(MainActivity.KEY_PASSARIATYPE, Modello.ARIA_SPORCA);
		    	mFragment.setArguments(mBundle);
		    	fragmentManager.beginTransaction()
		        .replace(R.id.container, mFragment)
		        .commit();
			}
		});
		
		return view;
	}	

}
