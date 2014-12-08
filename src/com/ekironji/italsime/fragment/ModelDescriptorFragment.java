package com.ekironji.italsime.fragment;

import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.R;
import com.ekironji.italsime.Modello.Modello;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ModelDescriptorFragment extends Fragment{
	
	final String DEBUG_TAG = "ModelDescriptorFragment";
	
	Modello modello;
	
//	public ModelDescriptorFragment(Modello modello){
//		super();
//		this.modello = modello;
//	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle saved){
		View view = inflater.inflate(R.layout.fragment_modeldescriptor, group, false);
		
		modello = (Modello)getArguments().getSerializable(MainActivity.KEY_PASSMODEL);
		Log.i(DEBUG_TAG, modello.toString());
		
		return view;
	}

}
