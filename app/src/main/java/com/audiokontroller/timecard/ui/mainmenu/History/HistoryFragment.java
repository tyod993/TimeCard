package com.audiokontroller.timecard.ui.mainmenu.History;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.audiokontroller.timecard.R;

public class HistoryFragment extends Fragment {

    private static final String TAG = HistoryFragment.class.getSimpleName();

    //Parent Fragment is HistoryParentFrag, if changed this will cause a NullPointerException.
    private HistoryViewModel viewModel = new ViewModelProvider(getParentFragment()).get(HistoryViewModel.class);

    private RecyclerView recView;
    private RecyclerView.Adapter recViewAdapter;
    private RecyclerView.LayoutManager recViewLayoutManager;

    //Position of Fragment in TabLayout of HistoryParentFrag
    private short position;

    public HistoryFragment(int position){
        this.position = (short) position;
        viewModel.setPosition((short) position);
        Log.d(TAG,".Create=Success:pos=" + position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_page_frag, container, false);
    }

    // Im not sure if onCreate() will get called before onCreateView(). If not it will throw a nullPointerException.
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        recView = getActivity().findViewById(R.id.history_rec_view);
         recViewLayoutManager = new LinearLayoutManager(getActivity());
         recView.setHasFixedSize(true);
         recView.setLayoutManager(recViewLayoutManager);
        recViewAdapter = new HistoryRecAdapter(viewModel.getTimeEntries(position));
        recView.setAdapter(recViewAdapter);
        Log.d(TAG, "RecViewAdapter.Set=Success");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
