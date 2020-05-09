package com.audiokontroller.timecard.data.room.type_converters;

import androidx.room.TypeConverter;

import com.audiokontroller.timecard.data.model.TimeCard;
import com.audiokontroller.timecard.data.model.UserPref;
import com.audiokontroller.timecard.data.model.UserTimeCardsHolder;
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

    @TypeConverter
    public static String fromUserTimeCardsHolder(UserTimeCardsHolder holder){
        Gson gson = new Gson();
        return gson.toJson(holder);
    }

    @TypeConverter
    public  static UserTimeCardsHolder toUserTimeCardsHolder(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, UserTimeCardsHolder.class);
    }

    @TypeConverter
    public static String fromUserPref(UserPref userPref){
        Gson gson = new Gson();
        return gson.toJson(userPref);
    }

    @TypeConverter
    public static UserPref toUserPref(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, UserPref.class);
    }
}
