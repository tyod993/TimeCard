package com.audiokontroller.timecard.data.room.type_converters;

import androidx.room.TypeConverter;

import com.audiokontroller.timecard.data.model.TimeCard;
import com.google.gson.Gson;

public class Converters {

    @TypeConverter
    public static String fromTimeCard(TimeCard timeCard){
        Gson gson = new Gson();
        return gson.toJson(timeCard);
    }

    @TypeConverter
    public static TimeCard toTimeCard(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, TimeCard.class);
    }
}
