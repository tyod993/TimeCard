package com.audiokontroller.timecard.data;

import android.content.Context;

import com.audiokontroller.timecard.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private boolean isAuthenticated;

    public Result login(Context context, String username, String password) {
        try {
            // TODO: create Firebase authentication
            UserAuthentication authentication =
                    new UserAuthentication(context, username, password);
            if (authentication.getAuthentication()) {
                LoggedInUser newUser =
                        new LoggedInUser(
                                username,
                                authentication.getDisplayName());
                isAuthenticated = true;
                return new Result.Success<>(newUser);
            }else{
                return new Result.Error(new IOException("Username or Password did'nt match"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
        isAuthenticated = false;
    }
}
