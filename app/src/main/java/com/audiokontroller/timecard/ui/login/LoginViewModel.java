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
import android.util.Log;
import android.util.Patterns;

import com.audiokontroller.timecard.data.LoginDataSource;
import com.audiokontroller.timecard.data.LoginRepository;
import com.audiokontroller.timecard.authentication.Result;
import com.audiokontroller.timecard.data.UserDataSource;
import com.audiokontroller.timecard.authentication.firebase.FirebaseAuthHandler;
import com.audiokontroller.timecard.data.model.LoggedInUser;
import com.audiokontroller.timecard.R;

public class LoginViewModel extends ViewModel {

    private static final String TAG = LoginViewModel.class.getSimpleName();

    private FirebaseAuthHandler firebaseAuthHandler = new FirebaseAuthHandler();
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> authResult = new MutableLiveData<>();
    private MutableLiveData<Error> currentError = new MutableLiveData<>();

    public LoginViewModel() {}

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {

        firebaseAuthHandler.loginWithFirebase(email, password);
        authResult.setValue(firebaseAuthHandler.getLoginSuccess());

        /*
        LoginRepository loginRepository = LoginRepository.getInstance(new LoginDataSource());
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(data));
            Log.d(TAG, "Login was successful");
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }

         */
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
    public void registrationDataChanged(String username, String password){
        LoginFormState formState = new LoginFormState(false);
        if(!isUserNameValid(username)){
            formState.setUsernameError(R.string.invalid_username);
        }else if(!isPasswordValid(password)){
            formState.setPasswordError(R.string.invalid_password);
        }else{
            formState.setDataValid(true);
        }
        loginFormState.setValue(formState);
    }
    //
    // Firebase Authentications

    public LiveData<Result> registerNewUser(String email, String password){
        firebaseAuthHandler.registerNewUser(email, password);
        return firebaseAuthHandler.getObservableAuthResult();
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
        if (username.contains("@") && username.contains(".com")) {
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

    public LiveData<Boolean> getAuthResult(){return authResult;}

}
