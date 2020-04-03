package com.audiokontroller.timecard.ui.mainmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
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
        try{mainMenuViewModel.setUserID(getIntent().getExtras().getString("userID"));}
        catch(NullPointerException e){
            Log.d(TAG, ":userID=null");
            Toast.makeText(this, R.string.login_error, Toast.LENGTH_LONG).show();
            super.onBackPressed();
        }

        bottomNav = findViewById(R.id.bottom_navigation_view);
        topToolbar = findViewById(R.id.home_top_toolbar);

        setSupportActionBar(topToolbar);

        //Navigation
        navController = Navigation.findNavController(this, R.id.home_nav_host_fragment);
        AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(topToolbar, navController);

        Log.d(TAG, ".onCreate.success");
    }
}
