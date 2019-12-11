package com.audiokontroller.timecard.ui.login;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Patterns;

import com.audiokontroller.timecard.data.LoginRepository;
import com.audiokontroller.timecard.data.Result;
import com.audiokontroller.timecard.data.UserRepository;
import com.audiokontroller.timecard.data.firebase.FirebaseAuthHandler;
import com.audiokontroller.timecard.data.model.LoggedInUser;
import com.audiokontroller.timecard.R;

public class LoginViewModel extends ViewModel {


    public Context context;
    private FirebaseAuthHandler firebaseAuthHandler = new FirebaseAuthHandler();
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private UserRepository userRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    //TODO:App currently closes when login fails, this should"nt be the case
    public void login(String username, String password) {
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
        if(!isUserNameValid(username)){
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null, null));
        }else if(!isPasswordValid(password)){
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password, null));
        }else if(!isNameValid(firstName)){
            loginFormState.setValue(new LoginFormState(null, null, R.string.invalid_firstName));
        }else{
            loginFormState.setValue(new LoginFormState(true));
        }

    }
    //
    // Firebase Authentications

    public void registerNewUser(String email, String password, @Nullable String firstName, @Nullable String lastName){
        firebaseAuthHandler.registerNewUser(email, password, firstName, lastName);
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



    public void setUserRepository(){
        if(userRepository == null) {
            userRepository = new UserRepository(context);
        }
    }

    public void setContext(Context context){this.context = context;}


}
