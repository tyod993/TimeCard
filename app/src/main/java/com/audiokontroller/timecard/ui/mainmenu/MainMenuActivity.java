package com.audiokontroller.timecard.ui.mainmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.ui.login.LaunchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    private static final String TAG = MainMenuActivity.class.getSimpleName();

    private MainMenuViewModel mainMenuViewModel;
    private Toolbar topToolbar;
    private BottomNavigationView bottomNav;
    private NavController navController;

    // TODO:Things need to be cleared out of the onCreate method to optomize UI draw times
    //Move all of the UI drawing to the onStart method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainMenuViewModel = new ViewModelProvider(this).get(MainMenuViewModel.class);

        bottomNav = findViewById(R.id.bottom_navigation_view);

        //Sending the userID to the ViewModel for Data retrieval
        try {mainMenuViewModel.setUserID(getIntent().getExtras().getString(getResources().getString(R.string.user_id_key)));}
        catch (NullPointerException e){
            Log.d(TAG, ":userID=null");
            Toast.makeText(this, R.string.login_error, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LaunchActivity.class);
            startActivity(intent);
        }

        //Pass preference to the ViewModel
        mainMenuViewModel.setPreferences(getPreferences(Context.MODE_PRIVATE));

        //This needs to be moved to the first navigation fragment inflation
        mainMenuViewModel.retrieveUser();

        //Navigation
        navController = Navigation.findNavController(this, R.id.home_nav_host_fragment);
        AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(bottomNav, navController);

        Log.d(TAG, ".onCreate.success");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO Set up work manager to update firebase with new user data
        mainMenuViewModel.clearDisposable();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
