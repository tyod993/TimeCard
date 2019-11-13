package com.audiokontroller.timecard.ui.mainmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.model.TimeEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainMenuActivity extends AppCompatActivity {

    private static final String TAG = MainMenuActivity.class.getSimpleName();

    public MainMenuViewModel mainMenuViewModel;

    private ConstraintLayout fragmentContainer;

    //TODO:EVERYTHING!!!!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public Fragment openTimeEntryFrag(){

        // Change the newInstance() argument to the user
        // name
        TimeEntryFragment fragment = TimeEntryFragment.newInstance(null);
        return fragment;
    }

}
