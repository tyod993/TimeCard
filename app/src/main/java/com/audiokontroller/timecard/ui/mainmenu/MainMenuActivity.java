package com.audiokontroller.timecard.ui.mainmenu;

import androidx.appcompat.app.AppCompatActivity;
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

    private RecyclerView timeEntryRecView;
    private FloatingActionButton floatingButtonAddTime;
    private FrameLayout fragmentContainer;
    private LiveData<List<TimeEntry>> timeCardList;

    //TODO:EVERYTHING!!!!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeEntryRecView = findViewById(R.id.time_list_rec_view);
        floatingButtonAddTime = findViewById(R.id.add_time_entry_float_button);
        fragmentContainer = findViewById(R.id.fragment_container);

    }

    @Override
    protected void onStart(){
        super.onStart();

        floatingButtonAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeEntryFrag();

            }
        });

    }

    protected void onStop(){
        super.onStop();


    }

    protected void onPause(){
        super.onPause();


    }

    protected void onRestart(){
        super.onRestart();
    }

    protected void onResume(){
        super.onResume();
    }

    public Fragment openTimeEntryFrag(){

        // Change the newInstance() argument to the user
        // name
        TimeEntryFragment fragment = TimeEntryFragment.newInstance(null);
        return fragment;
    }

}
