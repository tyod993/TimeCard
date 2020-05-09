package com.audiokontroller.timecard.data.model;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;


/**
 * As the app grows add more preferences to this class. Only add primitive data types so GSON
 * can parse it properly when updating the user in the room DB.
 */
public class UserPref {

    private final String userID;

    public static final int PROJECT = 0;
    public static final int TASK = 1;

    private ArrayList<String> projectSuggestions;
    private ArrayList<String> taskSuggestions;

    public UserPref(String userID){
        this.userID = userID;
    }

    public void addProjectSuggestion(String newSuggestion){
        if(projectSuggestions == null){
            projectSuggestions = new ArrayList<>();
            projectSuggestions.add(newSuggestion);
        } else {
            projectSuggestions.add(newSuggestion);
        }
    }

    public void addTaskSuggestion(String newSuggestion) {
        if(taskSuggestions == null){
            taskSuggestions = new ArrayList<>();
            taskSuggestions.add(newSuggestion);
        } else {
            taskSuggestions.add(newSuggestion);
        }
    }

    public ArrayList<String> getProjectSuggestions() {
        return projectSuggestions;
    }

    public ArrayList<String> getTaskSuggestions() {
        return taskSuggestions;
    }

    public String getUserID() {
        return userID;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
