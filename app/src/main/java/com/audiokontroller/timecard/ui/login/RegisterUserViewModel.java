package com.audiokontroller.timecard.ui.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.audiokontroller.timecard.data.LoginDataSource;
import com.audiokontroller.timecard.data.LoginRepository;
import com.audiokontroller.timecard.data.UserRepository;
import com.audiokontroller.timecard.data.model.User;

public class RegisterUserViewModel extends ViewModel {

    private UserRepository userRepository;
    private LoginRepository loginRepository;
    private Context context;

    private User newUser;

    RegisterUserViewModel(LoginDataSource loginDataSource){
        loginRepository = LoginRepository.getInstance(loginDataSource);
        this.userRepository = userRepository;
    }

    public void saveNewUser(@NonNull String username, @NonNull String password, @NonNull String email,
                            String firstName, String lastName, String birthday,
                            String companyName){
        newUser = new User(username, password, email, firstName, lastName, birthday, companyName);
        userRepository.update(newUser);
    }

    public void loginNewUser(Context context){
        loginRepository.login(context, newUser.getUsername(), newUser.getPassword());
    }

    public void setContext(Context context){this.context=context;}

}
