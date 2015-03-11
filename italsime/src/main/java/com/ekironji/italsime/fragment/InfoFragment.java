package com.ekironji.italsime.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.R;

public class InfoFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle saved){
		View view = inflater.inflate(R.layout.fragment_info, group, false);

        ((MainActivity)getActivity()).onSectionAttached(MainActivity.INFO_FRAG_ID);
        ((MainActivity)getActivity()).restoreActionBar();

        SpannableString string = new SpannableString("Search on Google Maps");
        string.setSpan(new UnderlineSpan(), 0, string.length(), 0);
        ((TextView) view.findViewById(R.id.searchonmaps_textview)).setText(string);
        ((TextView) view.findViewById(R.id.searchonmaps_textview)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "geo:0,0?q=Italsime+Macchine+Elettriche+Srl";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
	
		return view;
	}

}
