package com.audiokontroller.timecard.ui.mainmenu.History;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.model.Task;

import java.util.List;
import java.util.Random;

public class HistoryListAdapter extends BaseAdapter {

    private List<Task> mDataSet;
    //Todo: choose the colors to cycle through when creating list
    private int[] backgroundColorId = {R.color.list_item_1, R.color.list_item_2, R.color.list_time_3};

    public HistoryListAdapter(List<Task> dataSet){
        mDataSet = dataSet;
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_item, parent, false);
        Task currentTask = mDataSet.get(position - 1);
        TextView taskName = convertView.findViewById(R.id.list_task_name);
        TextView time = convertView.findViewById(R.id.list_task_hrs);
        taskName.setText(currentTask.getmName());
        time.setText(currentTask.getmHours());
        int backgroundRes;
        Random random = new Random(1);
        double randomDouble = random.nextDouble();
        if (randomDouble <= 0.33){
            backgroundRes = backgroundColorId[0];
        } else if (randomDouble <= 0.66) {
            backgroundRes = backgroundColorId[1];
        } else {
            backgroundRes = backgroundColorId[2];
        }
        convertView.setBackgroundResource(backgroundRes);
        return convertView;
    }
}
