package com.audiokontroller.timecard.ui.mainmenu.History;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.ui.mainmenu.MainMenuViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HistoryParentFrag extends Fragment {

    private final static String TAG = HistoryParentFrag.class.getSimpleName();

    private String[] tabTitles = {"R.string.today", "R.string.week", "R.string.period"};

    private HistoryViewModel viewModel;
    private HistoryPagerAdapter adapter;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.history_layout, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        final MainMenuViewModel mainMenuViewModel = new ViewModelProvider(getActivity()).get(MainMenuViewModel.class);
        viewModel.setLiveTimeCard(mainMenuViewModel.getLoadedTimeCards());
        adapter = new HistoryPagerAdapter(this);
        viewPager2 = view.findViewById(R.id.history_view_pager);
        viewPager2.setAdapter(adapter);
        Log.d(TAG, "PagerAdapter.set=Success");

        tabLayout  = getActivity().findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                tab.setText(tabTitles[position])
        ).attach();
    }
}
