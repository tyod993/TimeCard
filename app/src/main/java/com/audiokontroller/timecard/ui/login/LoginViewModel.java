/**
TODO: New Feature: 4 Digit pin validation.

 Feature: Update this class to keep the user validated for one week from log in time.
 When the time has elapsed launch a variation on logging in that
 only requires a 4 digit pin.
*/

package com.audiokontroller.timecard.ui.login;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Patterns;

import com.audiokontroller.timecard.data.LoginDataSource;
import com.audiokontroller.timecard.data.LoginRepository;
import com.audiokontroller.timecard.authentication.Result;
import com.audiokontroller.timecard.data.UserDataSource;
import com.audiokontroller.timecard.authentication.firebase.FirebaseAuthHandler;
import com.audiokontroller.timecard.data.model.LoggedInUser;
import com.audiokontroller.timecard.R;

public class LoginViewModel extends ViewModel {


    public Context context;
    private FirebaseAuthHandler firebaseAuthHandler = new FirebaseAuthHandler();
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private UserDataSource userRepository;


    public LoginViewModel() {}

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    //TODO:App currently closes when login fails, this should"nt be the case
    public void login(String username, String password) {
        LoginRepository loginRepository = LoginRepository.getInstance(new LoginDataSource());
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(data));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password, null));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    //Reusing loginFormState to validate registration input
    public void registrationDataChanged(String username, String password, String firstName){
        LoginFormState formState = new LoginFormState(false);
        if(!isUserNameValid(username)){
            formState.setUsernameError(R.string.invalid_username);
        }else if(!isPasswordValid(password)){
            formState.setPasswordError(R.string.invalid_password);
        }else if(!isNameValid(firstName)){
            formState.setNameError(R.string.invalid_firstName);
        }else{
            formState.setDataValid(true);
        }
        loginFormState.setValue(formState);
    }
    //
    // Firebase Authentications

    public Result registerNewUser(String email, String password, @Nullable String firstName, @Nullable String lastName){
        return firebaseAuthHandler.registerNewUser(email, password, firstName, lastName);
    }

    public boolean isFirebaseUserLoggedIn(){return firebaseAuthHandler.isUserLoggedIn();}

    public LoggedInUser getLoggedInUser(){return firebaseAuthHandler.getLoggedInUser();}

    //
    //


    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }
    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isNameValid(String name){
        if(name == null){
            return true;
        } else if (name.trim().length() <= 2){
            return false;
        }else{
            return true;
        }
    }

    public String getFirebaseUserID(){
       return firebaseAuthHandler.getFirebaseUser().getUid();
    }

    public void setUserRepository(){
        if(userRepository == null) {
            userRepository = new UserDataSource(context);
        }
    }

    public void setContext(Context context){this.context = context;}


}
