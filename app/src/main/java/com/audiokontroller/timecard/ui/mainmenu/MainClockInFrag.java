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
import androidx.lifecycle.ViewModelProvider;

import com.audiokontroller.timecard.R;

public class MainClockInFrag extends Fragment {

    private final String TAG = MainClockInFrag.class.getSimpleName();

    //Notes: Left off setting the button image URLs to make the button state change class?
    private static final String[] buttonImageUrls = {};

    private int clockState = 0;
    private TextView totalHoursTV;
    private ImageView clockinButton;
    private Button breakButton;

    private MainClockInViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainClockInViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                                @Nullable Bundle savedInstanceState){
        return layoutInflater.inflate(R.layout.main_clockin_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // totalHoursTV = view.findViewById(R.id.)
    }
}
