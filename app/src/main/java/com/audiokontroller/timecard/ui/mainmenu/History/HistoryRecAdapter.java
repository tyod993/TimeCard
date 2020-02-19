package com.audiokontroller.timecard.ui.mainmenu.History;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.TimeEntryFormat;
import com.audiokontroller.timecard.data.model.TimeEntry;

import java.util.ArrayList;

public class HistoryRecAdapter extends RecyclerView.Adapter<HistoryRecAdapter.ViewHolder> {

    private ArrayList<TimeEntry> mDataSet;

    final String TOTAL_HRS_PREFIX = "Hours:";

    public HistoryRecAdapter(ArrayList<TimeEntry> dataSet){
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeEntry timeEntry = mDataSet.get(position);
        TimeEntryFormat format = new TimeEntryFormat(timeEntry);
        holder.dateTV.setText(format.getSimpleDate(timeEntry.getEntryStartTime(), null));
        holder.dayTV.setText(format.getDayNameFull());
        holder.totalHrsTV.setText(format.getTotalHours(TOTAL_HRS_PREFIX));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTV;
        TextView dayTV;
        TextView totalHrsTV;
        ListView tasksLV;
        ImageButton editBtn;

        public ViewHolder(View view){
            super(view);
            dateTV = view.findViewById(R.id.his_cv_date_tv);
            dayTV = view.findViewById(R.id.his_cv_day_tv);
            totalHrsTV = view.findViewById(R.id.his_cv_total_hrs);
            tasksLV = view.findViewById(R.id.his_cv_tasks_lv);
            editBtn = view.findViewById(R.id.his_cv_edit_btn);
        }

    }


}
