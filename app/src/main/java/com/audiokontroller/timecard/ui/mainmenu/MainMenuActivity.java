package com.audiokontroller.timecard.ui.mainmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.util.Log;
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
        //TODO call Intent.getIntent to retireve the UserId from the log in screen.

        mainMenuViewModel = new MainMenuViewModel(getApplication());

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
