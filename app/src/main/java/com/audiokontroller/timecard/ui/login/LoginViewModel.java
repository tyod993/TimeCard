package com.audiokontroller.timecard.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Patterns;

import com.audiokontroller.timecard.data.LoginRepository;
import com.audiokontroller.timecard.data.Result;
import com.audiokontroller.timecard.data.UserRepository;
import com.audiokontroller.timecard.data.model.LoggedInUser;
import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.model.User;

public class LoginViewModel extends ViewModel {


    public Context context;
    // This new user is only used within the RegisterFragment.
    private User newUser;
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

    public void login(Context context, String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(context, username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
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

    public void registerNewUser(){
        // TODO ; Connect to Firebase and register user information.
        if(newUser != null){
            userRepository.update(newUser);
        }
    }

    public void createNewUser(@NonNull String email, @NonNull String password, String firstName, String lastName){
        newUser = new User(password, email, firstName, lastName);
    }

    public void setUserRepository(){
        if(userRepository == null) {
            userRepository = new UserRepository(context);
        }
    }

    public void setContext(Context context){this.context = context;}


}
