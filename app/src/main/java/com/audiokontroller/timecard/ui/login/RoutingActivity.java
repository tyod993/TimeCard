package com.audiokontroller.timecard.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.authentication.Result;
import com.audiokontroller.timecard.ui.mainmenu.MainMenuActivity;

public class RoutingActivity extends Activity {

    private final String TAG = RoutingActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Result result = findExistingUser();
            if (result instanceof Result.Success) {
                Intent intent = new Intent(this, MainMenuActivity.class);
                intent.putExtra(getResources().getString(R.string.user_id_key), ((Result.Success<String>) result).getData());
                Log.d(TAG, "Existing user found");
                startActivity(intent);
                finish();
                Log.d(TAG, "finished");
            } else {
                Intent intent = new Intent(this,  LaunchActivity.class);
                startActivity(intent);
                finish();
                Log.d(TAG, "Finished");
            }
        }

    public Result findExistingUser(){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String uID = sharedPreferences.getString(getResources().getString(R.string.user_id_key), getResources().getString(R.string.user_id_def_val));
        if(uID.equalsIgnoreCase("none")){
            return new Result.Error(new Exception("User uID does'nt exist in SharedPreferences"));
        }else{
            return new Result.Success<>(uID);
        }
    }
}
