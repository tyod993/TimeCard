package com.audiokontroller.timecard.data;

import com.audiokontroller.timecard.authentication.Result;
import com.audiokontroller.timecard.data.model.LoggedInUser;
import com.audiokontroller.timecard.authentication.firebase.FirebaseAuthHandler;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private boolean isAuthenticated;

    public Result login(String email, String password) {
        try {
            FirebaseAuthHandler authHandler = new FirebaseAuthHandler();
            authHandler.loginWithFirebase(email, password);
            if (authHandler.getLoginSuccess()) {
                LoggedInUser newUser =
                        authHandler.getLoggedInUser();
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

    public boolean getAuthentication(){return isAuthenticated;}
}
