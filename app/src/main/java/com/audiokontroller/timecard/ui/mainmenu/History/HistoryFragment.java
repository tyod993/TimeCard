package com.audiokontroller.timecard.ui.mainmenu.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.audiokontroller.timecard.R;

//TODO:

public class HistoryFragment extends Fragment {

    private RecyclerView recView;
    private RecyclerView.Adapter recViewAdapter;
    private RecyclerView.LayoutManager recViewLayoutManager;

    private int position;

    public HistoryFragment(int position){
        this.position = position;
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
        recView.findViewById(R.id.history_rec_view);
         recViewLayoutManager = new LinearLayoutManager(getActivity());
         recView.setLayoutManager(recViewLayoutManager);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
