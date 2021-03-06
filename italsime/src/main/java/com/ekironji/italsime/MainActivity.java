package com.ekironji.italsime;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import com.ekironji.italsime.Modello.Modello;
import com.ekironji.italsime.database.Database;
import com.ekironji.italsime.fragment.HomeFragment;
import com.ekironji.italsime.fragment.InfoFragment;
import com.ekironji.italsime.fragment.NavigationDrawerFragment;
import com.ekironji.italsime.fragment.SeriesListFragment;


public class MainActivity extends ActionBarActivity implements 
								NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	private final String DEBUG_TAG = "MainActivity";
	
	public static final String KEY_PASSARIATYPE 		= "ARIATYPE";
	public static final String KEY_PASSMODEL			= "MODELCLICKED";
	public static final String KEY_PASSSERIE			= "SERIECLICKED";
	public static final String KEY_PASSFILTEREDRESEARCH	= "FILTEREDRESEARCH";

    public static final int HOME_FRAG_ID                = 0;
    public static final int INFO_FRAG_ID                = 3;
	public static Database database;
	
	static ProgressDialog dialog;
	
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

	    supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    dialog = new ProgressDialog(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

		
		if (savedInstanceState != null) {
			Log.i(DEBUG_TAG, "savedInstanceState!=null");
		}

		database = new Database(this);
        database.open();
        
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        setUpNavigationDrawer();
		
		database.close();
	}

    public Toolbar getToolbar(){
        return this.toolbar;
    }

    public void setUpNavigationDrawer() {
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(DEBUG_TAG, "onSaveInstanceState()");
//		outState.putInt(STATE_SELECTED_ARIATYPE, ariaType);
//		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
//	        return;
//	    }
//	    String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		hideProgressDialog();
	}
	
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    	FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Fragment mFragment = null;
        switch(position) {
        case 0:
        	mFragment = new HomeFragment();
        	break;
        case 1:
        	mFragment = new SeriesListFragment();
            Bundle mBundleAriaPulita = new Bundle();
        	mBundleAriaPulita.putInt(KEY_PASSARIATYPE, Modello.ARIA_PULITA);
        	mFragment.setArguments(mBundleAriaPulita);
        	break;
        case 2:
        	mFragment = new SeriesListFragment();
        	Bundle mBundleAriaSporca = new Bundle();
        	mBundleAriaSporca.putInt(KEY_PASSARIATYPE, Modello.ARIA_SPORCA);
        	mFragment.setArguments(mBundleAriaSporca);
        	break;
        case 3:
        	mFragment = new InfoFragment();
        	break;
    	default: 
    		break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, mFragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.app_name);
                break;
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
//        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }
    
    public ActionBarDrawerToggle getDrawerToggle(){
    	return mNavigationDrawerFragment.getDrawerToggle();
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // turn on the Navigation Drawer image; 
        // this is called in the LowerLevelFragments
//        getDrawerToggle().setDrawerIndicatorEnabled(true);
        setUpNavigationDrawer();
    }
    
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        if (!mNavigationDrawerFragment.isDrawerOpen()) {
//            // Only show items in the action bar relevant to this screen
//            // if the drawer is not showing. Otherwise, let the drawer
//            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.main, menu);
//            restoreActionBar();
//            return true;
//        }
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	public static void showProgressDialog(String message){
		dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();		
	}
	
	public static void hideProgressDialog(){
		dialog.dismiss();		
	}

}
