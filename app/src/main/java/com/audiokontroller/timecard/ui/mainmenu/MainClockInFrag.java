package com.audiokontroller.timecard.ui.mainmenu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.audiokontroller.timecard.R;

public class MainClockInFrag extends Fragment {

    private final String TAG = MainClockInFrag.class.getSimpleName();

    //Notes: Left off setting the button image URLs to make the button state change class?
    private static final String[] buttonImageUrls = {};

    private TextView totalHoursTV;
    private ImageView clockinButton;
    private Button breakButton;

    public MainClockInFrag(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                                @Nullable Bundle savedInstanceState){
        return layoutInflater.inflate(R.layout.main_clockin_frag, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //UI Components
        totalHoursTV = getActivity().findViewById(R.id.total_hours_clk_in);
        clockinButton = getActivity().findViewById(R.id.clockin_button);
        breakButton = getActivity().findViewById(R.id.break_button);
        //

        clockinButton.setOnClickListener(view ->{


        });


    }
}
