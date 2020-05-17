package com.audiokontroller.timecard.ui.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.audiokontroller.timecard.ui.mainmenu.MainMenuViewModel;

import java.util.Calendar;

public class TimePickerFrag extends DialogFragment{

    private TimePickerDialog.OnTimeSetListener listener;

    public TimePickerFrag(TimePickerDialog.OnTimeSetListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //impliment and override the onTimeSelectMethod
        return new TimePickerDialog(
                getActivity(), listener, hour, minute, DateFormat.is24HourFormat(getActivity())
        );
    }
}
