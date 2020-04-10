package com.audiokontroller.timecard.ui.mainmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.audiokontroller.timecard.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    private static final String TAG = MainMenuActivity.class.getSimpleName();

    private MainMenuViewModel mainMenuViewModel;
    private Toolbar topToolbar;
    private BottomNavigationView bottomNav;
    private NavController navController;

    // TODO:EVERYTHING!!!!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainMenuViewModel = new ViewModelProvider(this).get(MainMenuViewModel.class);

        //Try to get the userID for the DB queries
        //There is potential here for an infinite loop.
        try {mainMenuViewModel.setUserID(getIntent().getExtras().getString(getResources().getString(R.string.user_id_key)));}
        catch (NullPointerException e){
            Log.d(TAG, ":userID=null");
            Toast.makeText(this, R.string.login_error, Toast.LENGTH_LONG).show();
            super.onBackPressed();
        }
        //Pass the data source preference to the ViewModel. If it hasn't been saved before return "firebase" as the preference
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        mainMenuViewModel.setDatabasePreference(
                preferences.getString(getResources().getString(R.string.db_pref_key),
                getResources().getString(R.string.db_pref_def_val)));

        //This needs to be moved to the first navigation fragment inflation
        mainMenuViewModel.retrieveUser();

        //TODO: Left off here. still need to finish the Navigation components

        bottomNav = findViewById(R.id.bottom_navigation_view);
        topToolbar = findViewById(R.id.home_top_toolbar);

        setSupportActionBar(topToolbar);

        //Navigation
        navController = Navigation.findNavController(this, R.id.home_nav_host_fragment);
        AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(topToolbar, navController);

        Log.d(TAG, ".onCreate.success");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO Set up work manager to update firebase with new user data
        mainMenuViewModel.clearDisposable();
    }
}
