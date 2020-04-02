package com.audiokontroller.timecard.ui.History;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class HistoryPagerAdapter extends FragmentStateAdapter {

    private final int NUM_PAGES = 3;

    public HistoryPagerAdapter(Fragment fragment){
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        return new HistoryFragment(position);
    }

    @Override
    public int getItemCount(){
        return NUM_PAGES;
    }
}
