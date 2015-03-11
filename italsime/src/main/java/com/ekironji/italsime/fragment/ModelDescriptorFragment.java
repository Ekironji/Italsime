package com.ekironji.italsime.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekironji.italsime.MainActivity;
import com.ekironji.italsime.Modello.Modello;
import com.ekironji.italsime.Modello.Series;
import com.ekironji.italsime.R;

import java.util.Locale;

public class ModelDescriptorFragment extends Fragment{

	final String DEBUG_TAG = "ModelDescriptorFragment";

	Modello modello;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle saved){
//		View view = inflater.inflate(R.layout.fragment_modeldescriptor, group, false);
        FrameLayout frameLayout = new FrameLayout(getActivity());
//		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		final Toolbar actionbar = ((MainActivity)getActivity()).getToolbar();
        if (null != actionbar) {
            actionbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            actionbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((MainActivity)getActivity()).setUpNavigationDrawer();
                    ((MainActivity)getActivity()).onBackPressed();
                    //NavUtils.navigateUpFromSameTask(SettingsActivity.this);
                }
            });
            // Inflate a menu to be displayed in the toolbar
            //actionbar.inflateMenu(R.menu.settings);
        }

        modello = (Modello)getArguments().getSerializable(MainActivity.KEY_PASSMODEL);
        int currentOrientation = getResources().getConfiguration().orientation;

		Log.i(DEBUG_TAG, modello.toString());

        populateViewForOrientation(inflater, frameLayout, modello, currentOrientation);

		return frameLayout;
	}
	
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        modello = (Modello)getArguments().getSerializable(MainActivity.KEY_PASSMODEL);
        Log.i(DEBUG_TAG, "orientation: " + newConfig.orientation + " - land: " + Configuration.ORIENTATION_LANDSCAPE
                + " - port: " + Configuration.ORIENTATION_PORTRAIT);
        populateViewForOrientation(inflater, (ViewGroup) getView(), modello, newConfig.orientation);
    }
 
    private void populateViewForOrientation(LayoutInflater inflater, ViewGroup viewGroup, Modello model, int orientation) {
        viewGroup.removeAllViewsInLayout();
        View subview = inflater.inflate(R.layout.fragment_modeldescriptor, viewGroup);

        Log.i(DEBUG_TAG, model.toString());

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            Log.i(DEBUG_TAG,"current: " + orientation + " PORT");

            TextView name = (TextView) subview.findViewById(R.id.textViewNome);
            if (model.getAriaType() == Modello.ARIA_PULITA){
                name.setBackgroundColor(getResources().getColor(R.color.italsimegreenahcg_color));
            } else if (model.getAriaType() == Modello.ARIA_SPORCA) {
                name.setBackgroundColor(getResources().getColor(R.color.italsimevioletahcg_color));
            }
            name.setText(model.getName());

            ((TextView) subview.findViewById(R.id.TextViewKwValue)).setText(String.valueOf(model.getKw()));
            ((TextView) subview.findViewById(R.id.TextViewRpmValue)).setText(String.valueOf(model.getRpm()));
            ((TextView) subview.findViewById(R.id.TextViewKgValue)).setText(String.valueOf(model.getKg()));

            ((ImageView) subview.findViewById(R.id.imageViewAriaType)).setImageResource(
                    getResources().getIdentifier(Series.getNameFromInt(model.getSerie()).toLowerCase(Locale.getDefault()), "drawable",  getActivity().getPackageName()));

            ((TextView) subview.findViewById(R.id.TextViewm3h1)).setText((model.getM3h1() == -1) ? "-" : String.valueOf(model.getM3h1()));
            ((TextView) subview.findViewById(R.id.TextViewm3h2)).setText((model.getM3h2() == -1) ? "-" : String.valueOf(model.getM3h2()));
            ((TextView) subview.findViewById(R.id.TextViewm3h3)).setText((model.getM3h3() == -1) ? "-" : String.valueOf(model.getM3h3()));
            ((TextView) subview.findViewById(R.id.TextViewm3h4)).setText((model.getM3h4() == -1) ? "-" : String.valueOf(model.getM3h4()));
            ((TextView) subview.findViewById(R.id.TextViewm3h5)).setText((model.getM3h5() == -1) ? "-" : String.valueOf(model.getM3h5()));

            ((TextView) subview.findViewById(R.id.TextViewmmH2O1)).setText((model.getMmH2O1() == -1) ? "-" : String.valueOf(model.getMmH2O1()));
            ((TextView) subview.findViewById(R.id.TextViewmmH2O2)).setText((model.getMmH2O2() == -1) ? "-" : String.valueOf(model.getMmH2O2()));
            ((TextView) subview.findViewById(R.id.TextViewmmH2O3)).setText((model.getMmH2O3() == -1) ? "-" : String.valueOf(model.getMmH2O3()));
            ((TextView) subview.findViewById(R.id.TextViewmmH2O4)).setText((model.getMmH2O4() == -1) ? "-" : String.valueOf(model.getMmH2O4()));
            ((TextView) subview.findViewById(R.id.TextViewmmH2O5)).setText((model.getMmH2O5() == -1) ? "-" : String.valueOf(model.getMmH2O5()));

            ((ImageView) subview.findViewById(R.id.imageViewDimensioni)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), getResources().getText(R.string.rotate_string), Toast.LENGTH_SHORT).show();
                }
            });
            Toast.makeText(getActivity(), getResources().getText(R.string.rotate_string), Toast.LENGTH_SHORT).show();
        }
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            Log.i(DEBUG_TAG,"current: " + orientation + " LAND");

            ((ImageView) subview.findViewById(R.id.imageViewDimensioni_land)).setImageResource(R.drawable.dimensioni_image);
            ((TextView) subview.findViewById(R.id.TextViewA_value)).setText(String.valueOf(model.getMisura1()));
            ((TextView) subview.findViewById(R.id.TextViewB_value)).setText(String.valueOf(model.getMisura2()));
            ((TextView) subview.findViewById(R.id.TextViewC_value)).setText(String.valueOf(model.getMisura3()));
            ((TextView) subview.findViewById(R.id.TextViewD_value)).setText(String.valueOf(model.getMisura4()));
            ((TextView) subview.findViewById(R.id.TextViewE_value)).setText(String.valueOf(model.getMisura5()));
            ((TextView) subview.findViewById(R.id.TextViewF_value)).setText(String.valueOf(model.getMisura6()));
            ((TextView) subview.findViewById(R.id.TextViewG_value)).setText(String.valueOf(model.getMisura7()));
            ((TextView) subview.findViewById(R.id.TextViewH_value)).setText(String.valueOf(model.getMisura8()));
            ((TextView) subview.findViewById(R.id.TextViewI_value)).setText(String.valueOf(model.getMisura9()));
            ((TextView) subview.findViewById(R.id.TextViewL_value)).setText(String.valueOf(model.getMisura10()));
            ((TextView) subview.findViewById(R.id.TextViewM_value)).setText(String.valueOf(model.getMisura11()));
            ((TextView) subview.findViewById(R.id.TextViewN_value)).setText(String.valueOf(model.getMisura12()));
            ((TextView) subview.findViewById(R.id.TextViewO_value)).setText(String.valueOf(model.getMisura13()));
            ((TextView) subview.findViewById(R.id.TextViewP_value)).setText(String.valueOf(model.getMisura14()));
            ((TextView) subview.findViewById(R.id.TextViewQ_value)).setText(String.valueOf(model.getMisura15()));
            ((TextView) subview.findViewById(R.id.TextViewR_value)).setText(String.valueOf(model.getMisura16()));
            ((TextView) subview.findViewById(R.id.TextViewS_value)).setText(String.valueOf(model.getMisura17()));
            ((TextView) subview.findViewById(R.id.TextViewT_value)).setText(String.valueOf(model.getMisura18()));
        }
    }

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
////			getActivity().getSupportFragmentManager().popBackStack();
//			((MainActivity)getActivity()).onBackPressed();
//			return true;
//		}
//
//		return super.onOptionsItemSelected(item);
//	}

}
