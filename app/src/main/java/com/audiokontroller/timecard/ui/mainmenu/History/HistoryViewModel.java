package com.audiokontroller.timecard.ui.mainmenu.History;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audiokontroller.timecard.data.model.TimeCard;
import com.audiokontroller.timecard.data.model.TimeEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryViewModel extends ViewModel {

    private static final String TAG = HistoryViewModel.class.getSimpleName();

    //Positions in the TabLayout [ Today : Week : Period ]
    public short position;

    private TimeCard activeCard;

    /**
     * uiDataSetFull Is for access and editing of the data.
     * uiDataSetFull should be sorted from newest to oldest
     * liveTimeCard is READONLY!!! Once the parent fragment is destroyed WorkManger will save all
     * edited data to Room and Firestore.
      */

    private MutableLiveData<ArrayList<TimeEntry>> uiDataSetFull = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TimeCard>> liveTimeCard = new MutableLiveData<>();

    public HistoryViewModel(){}

    public MutableLiveData<ArrayList<TimeEntry>> getUiDataSetFull() {
        return uiDataSetFull;
    }

    public void setPosition(short position) {
        this.position = position;
    }

    /**
     * @param listTimeCard
     * Must be called before anything else otherwise methods will throw NullPointerException
     */

    public void setLiveTimeCard(ArrayList<TimeCard> listTimeCard) {
        liveTimeCard.setValue(listTimeCard);
        for (TimeCard card : listTimeCard) {
            if(card.isActive()){
                activeCard = card; }
        }
        Log.d(TAG, "LiveTimeCard:Set=Success");
    }

    public ArrayList<TimeEntry> getTimeEntries(int position){
        try {
            ArrayList<TimeEntry> listTimeEntries = new ArrayList<>();
            Calendar now = Calendar.getInstance();
            //position 0 is == Today
            if (position == 0) {
                //TODO change to for each statement
                for (TimeEntry entry : uiDataSetFull.getValue()) {

                }
            } else if (position == 1) { //Position 1 == This Week
                for (TimeEntry entry : uiDataSetFull.getValue()) {
                    if (entry.getEntryStartTime().get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR)) {
                        listTimeEntries.add(entry);
                    }
                }
            } else {
                for (TimeEntry entry: uiDataSetFull.getValue()) {
                    if(entry.getTimeCardID() == activeCard.CARD_ID){
                        listTimeEntries.add(entry);
                    }
                }
            }
            return listTimeEntries;
        } catch (NullPointerException e){
            Log.d(TAG, e.toString() + "!Data set is empty!");
            return null;
        }
    }
}
