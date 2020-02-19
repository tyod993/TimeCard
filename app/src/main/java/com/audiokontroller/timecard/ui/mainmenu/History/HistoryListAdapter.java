package com.audiokontroller.timecard.ui.mainmenu.History;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.audiokontroller.timecard.R;

import java.util.List;

public class HistoryListAdapter extends BaseAdapter {

    private List<String[]> mDataSet;
    //Todo: choose the colors to cycle through when creating list
    private String[] backgroundColorCodes = {};

    public HistoryListAdapter(List<String[]> dataSet){
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
        String[] currentTask = mDataSet.get(position);
        TextView taskName = convertView.findViewById(R.id.list_task_name);
        TextView time = convertView.findViewById(R.id.list_task_hrs);
        taskName.setText(currentTask[0]);
        time.setText(currentTask[1]);
        return convertView;
    }
}
