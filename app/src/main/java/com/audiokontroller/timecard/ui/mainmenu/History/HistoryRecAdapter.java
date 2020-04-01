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
import com.audiokontroller.timecard.data.TimeEntry.TimeEntryDisplayFormat;
import com.audiokontroller.timecard.data.model.TimeEntry;
import com.audiokontroller.timecard.ui.mainmenu.TimeEntry.TimeEntryEditFragment;
import com.audiokontroller.timecard.ui.mainmenu.utils.TasksListAdapter;

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
        TimeEntryDisplayFormat format = new TimeEntryDisplayFormat(timeEntry);
        holder.dateTV.setText(format.getSimpleDate(timeEntry.getEntryStartTime().toString(), null));
        holder.dayTV.setText(format.getDayNameLong());
        holder.totalHrsTV.setText(format.getTotalHours(TOTAL_HRS_PREFIX));
        holder.tasksLV.setAdapter(new TasksListAdapter(timeEntry.getTasks(), false));
        holder.editBtn.setOnClickListener(view ->
                //TODO: Navigate to TimeEntryEditFragment via NavFragment passing TimeEntry as SafeArgs;
                new TimeEntryEditFragment(timeEntry)
        );
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTV;
        TextView dayTV;
        TextView totalHrsTV;
        TextView tasksTitle;
        ListView tasksLV;
        ImageButton editBtn;
        TextView breaksTitle;
        ListView breaksLV;


        public ViewHolder(View view){
            super(view);
            dateTV = view.findViewById(R.id.his_cv_date_tv);
            dayTV = view.findViewById(R.id.his_cv_day_tv);
            totalHrsTV = view.findViewById(R.id.his_cv_total_hrs);
            tasksTitle = view.findViewById(R.id.his_cv_tasks_title);
            tasksLV = view.findViewById(R.id.his_cv_tasks_lv);
            editBtn = view.findViewById(R.id.his_cv_edit_btn);
            breaksTitle = view.findViewById(R.id.his_cv_break_title);
            breaksLV = view.findViewById(R.id.his_cv_break_lv);
        }

    }


}
