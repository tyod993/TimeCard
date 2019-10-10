package com.audiokontroller.timecard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainMenuActivity extends AppCompatActivity {

    private static final String TAG = MainMenuActivity.class.getSimpleName();

    public static String userID;

    private RecyclerView timeEntryRecView;
    private FloatingActionButton floatingButtonAddTime;
    private FrameLayout fragmentContainer;

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
