package com.audiokontroller.timecard.ui.mainmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.audiokontroller.timecard.R;

public class MainMenuActivity extends AppCompatActivity {

    private static final String TAG = MainMenuActivity.class.getSimpleName();

    private MainMenuViewModel mainMenuViewModel;

    //TODO:EVERYTHING!!!!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // mainMenuViewModel = new MainMenuViewModel(getApplication());
    }

    public Fragment openTimeEntryFrag(){

        // Change the newInstance() argument to the username
        TimeEntryFragment fragment = TimeEntryFragment.newInstance(null);
        return fragment;
    }

}
