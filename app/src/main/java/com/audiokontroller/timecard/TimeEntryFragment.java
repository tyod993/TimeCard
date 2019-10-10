package com.audiokontroller.timecard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimeEntryFragment extends Fragment {

    private static final String TAG = TimeEntryFragment.class.getSimpleName();

    private static final String USER_NAME = "username";

    private String mUsername;

    public TimeEntryFragment() {
        // Required empty public constructor
    }

    public static TimeEntryFragment newInstance(String param1) {
        TimeEntryFragment fragment = new TimeEntryFragment();
        Bundle args = new Bundle();
        args.putString(USER_NAME, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsername = getArguments().getString(USER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_entry, container, false);
        //place onClickListener here!
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
